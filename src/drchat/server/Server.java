package drchat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import drchat.database.DatabaseConnection;
import drchat.model.Message;
import drchat.model.SocketMessage;
import drchat.model.User;

public class Server implements Runnable {

    private static final int PORT = 6446;

    private static List<User> users;
    private static ArrayList<ClientHandler> clinets = new ArrayList<ClientHandler>();
    
    private ServerSocket listener;
    private boolean isRunning = true;

    @Override
    public void run() throws NullPointerException {
        
        try {
            loadUsers();
            listener = new ServerSocket(PORT);
            while (isRunning) {
                System.out.println("[SERVER] Waiting for client connection...");
                Socket socket = listener.accept();
                ClientHandler client = new ClientHandler(socket);
                clinets.add(client);
                new Thread(client).start();
                System.out.println("[SERVER] New connection from: " + socket.toString());
            }

        } catch (IOException e) {

        }
    }

    private void loadUsers() {
        try (Session session = DatabaseConnection.getSession()) {
            session.beginTransaction();
            users = (List<User>) session.createQuery("from " + User.class.getName()).list();
            session.getTransaction().commit();
        }
    }

    public static List<User> getUsers() {
        return Server.users;
    }
    
    public static List<ClientHandler> getClients() {
        return Server.clinets;
    }
    
    public static void main(String[] args) {
        Server server = new Server();
        Thread listener = new Thread(server);
        listener.start();
    }

}


