package server;

import IOmanager.RWManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class work as a ordinary server, accept and manage
 * different connections with threads
 * @author Steven Ramirez
 * @author Keylor Barboza
 * @author Juan Ignacio Orozco
 */
public class Server {
    
    private ServerSocket server;
    private Socket client;
    private final int PORT = 12345;
    private static final UserList userList = UserList.getInstance();

    /**
     * Manage the arrival connections, and also start listen to user
     * request with the GameThread
     */
    private void runServer() {
        try {
            server = new ServerSocket(PORT);
            RWManager.read(userList);
            while (true) {
                try {
                    waitForConnection();
                    GameThread gameThread = new GameThread(client, userList);
                    gameThread.start();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeServer();
        }
    }
    
    /**
     * Accept the comming connections
     * Print the inet adresss from the user connected
     * @throws IOException
     */
    private void waitForConnection() throws IOException {
        System.out.println("Waiting for connection");
        this.client = server.accept();
        System.out.println("Connection received from: "
                + client.getInetAddress().getHostName());
    }
    
    /**
     * Close the server socket
     */
    private void closeServer() {
        System.out.println("\nTerminating server");
        try {
            server.close();
        } catch (IOException ex) {
            System.out.println("Error during server closing");
        }
    }

    /**
     * Initialize the server
     * @param args
     */
    public static void main(String[] args) {
        new Server().runServer();
    }

}
