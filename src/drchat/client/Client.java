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
import javafx.application.Platform;

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
            activate();
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

    public int register(User user) throws IOException, ClassNotFoundException {
        SocketMessage message = new SocketMessage(SocketMessage.Type.REGISTER, user);
        send(message);
        SocketMessage reply = (SocketMessage) input.readObject();
        return (int) reply.getObject();
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

    public void activate() throws NullPointerException, IOException {
        SocketMessage greeting = new SocketMessage();
        greeting.setType(SocketMessage.Type.ACTIVATION);
        send(greeting);
    }

    public void send(SocketMessage message) throws NullPointerException, IOException {
        output.writeObject(message);
        output.flush();
    }

}

