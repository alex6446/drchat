package drchat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import drchat.client.controller.Login;
import drchat.model.Message;
import drchat.model.SocketMessage;
import drchat.model.User;

public class Client implements Runnable {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public void connect(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() throws NullPointerException {
        
        try {
            while (socket.isConnected()) {
                SocketMessage message = null;
                message = (SocketMessage) input.readObject();

                if (message != null) {
                    switch (message.getType()) {
                        case MESSAGE:
                            Login.getChat().updateMessages((Message) message.getObject());
                            break;
                        case USER:
                            Login.getChat().updateUsers((User) message.getObject());
                            break;
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {

        }
    }

    public void activate() throws NullPointerException, IOException {
        SocketMessage greeting = new SocketMessage();
        greeting.setType(SocketMessage.Type.ACTIVATION);
        send(greeting);
    }

    public int login(String username, String password) throws IOException, ClassNotFoundException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        SocketMessage message = new SocketMessage(SocketMessage.Type.LOGIN, user);
        send(message);
        SocketMessage reply = (SocketMessage) input.readObject();
        return (int) reply.getObject();
    }

    public void send(SocketMessage message) throws NullPointerException, IOException {
        output.writeObject(message);
        output.flush();
    }

    public ArrayList<User> getUsers() throws IOException, ClassNotFoundException {
        //SocketMessage message = new SocketMessage(SocketMessage.Type.GET_USERS, null);
        //send(message);
        //SocketMessage reply = (SocketMessage) ois.readObject();
        //return (ArrayList<User>) reply.getMessageObject();

        ArrayList<User> us = new ArrayList<>(Arrays.asList(
            new User(0, "alex6446", null, "a64", 1),
            new User(1, "linus", null, "li", 0)
        ));
        //SocketMessage reply = new SocketMessage(SocketMessage.Type.GET_USERS, us);
        //return (ArrayList<User>) reply.getMessageObject();
        return us;

    }

}

