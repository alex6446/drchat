package drchat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import drchat.database.DatabaseConnection;
import drchat.model.Message;
import drchat.model.SocketMessage;
import drchat.model.User;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private boolean activated = false;
    private int userId = -1;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public boolean isActive() {
        return activated;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public void run() throws NullPointerException {
        
        try {

            while (socket.isConnected()) {
                SocketMessage message = null;
                message = (SocketMessage) ois.readObject();

                if (message != null) {
                    switch (message.getType()) {
                        case LOGIN: {
                            userId = -1;
                            User user = (User) message.getObject();
                            for (User e : Server.getUsers()) {
                                if (e.getUsername().equals(user.getUsername()) &&
                                    e.getPassword().equals(user.getPassword())) {
                                        userId = e.getId();
                                        break;
                                    }
                            }
                            send(new SocketMessage(SocketMessage.Type.LOGIN, userId));
                            break; }
                        case MESSAGE: {
                            Message msg = (Message) message.getObject();
                            addMessage(msg);
                            notifyNewMessage(msg);

                            System.out.println(msg.getText());

                            break; }
                        case REGISTER: {
                            User user = (User) message.getObject();
                            int id = addUser(user);
                            send(new SocketMessage(SocketMessage.Type.REGISTER, id));
                            System.out.println(id);
                            if (id != -1) notifyNewUser(user);
                            break; }
                        case ACTIVATION:
                            activated = !activated;
                            send(message);
                            break;
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {

        }
    }

    public void send(SocketMessage message) throws NullPointerException, IOException {
        oos.writeObject(message);
        oos.flush();
    }

    public void sendAll(SocketMessage message) throws NullPointerException, IOException {
        for (ClientHandler e : Server.getClients())
            if (e != this && e.isActive())
                e.send(message);
    }

    public void addMessage(Message message) {
        try (Session session = DatabaseConnection.getSession()) {
            session.beginTransaction();
            session.save(message);
            session.getTransaction().commit();
        }
    }

    public synchronized int addUser(User user) {
        for (User e : Server.getUsers())
            if (e.getUsername().equals(user.getUsername()))
                return -1;
        try (Session session = DatabaseConnection.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        Server.getUsers().add(user);
        return user.getId();
    }

    public void notifyNewUser(User user) throws IOException {
        // send USER to all active users
        sendAll(new SocketMessage(SocketMessage.Type.USER, user));
    }
    
    public void notifyNewMessage(Message message) throws IOException {
        // send message to whome it belongs
        SocketMessage msg = new SocketMessage(SocketMessage.Type.MESSAGE, message);
        if (message.getReceiverId() == -1)
            sendAll(msg);
        else {
            for (ClientHandler e : Server.getClients())
                if (e.getUserId() == message.getReceiverId() ||
                    e.getUserId() == message.getSenderId() && e != this)
                    e.send(msg);
        }
    }
    
}

