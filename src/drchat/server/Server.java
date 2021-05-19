package drchat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import drchat.model.Message;
import drchat.model.SocketMessage;
import drchat.model.User;

public class Server implements Runnable {

    private ServerSocket listener;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    @Override
    public void run() throws NullPointerException {
        
        try {
            ServerSocket ss = new ServerSocket(6446);

            socket = ss.accept();
            System.out.println("got connection from: " + socket.toString());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                SocketMessage message = null;
                message = (SocketMessage) ois.readObject();

                if (message != null) {
                    switch (message.getMessageType()) {
                        case LOGIN:
                            User user = (User) message.getMessageObject();
                            send(new SocketMessage(SocketMessage.Type.LOGIN, 0));
                            break;
                        case MESSAGE:
                            Message msg = (Message) message.getMessageObject();
                            msg.setText("SERVER:" + msg.getText());
                            System.out.println(msg.getText());
                            send(new SocketMessage(SocketMessage.Type.MESSAGE, msg));
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

    public static void main(String[] args) {
        Server server = new Server();
        Thread listener = new Thread(server);
        listener.start();
    }

}


