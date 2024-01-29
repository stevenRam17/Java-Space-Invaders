/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * This class is responsible for connecting to the server to write and send 
 * values, as required, it is used most of all in the controller class, 
 * for constant interaction with the server
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public final class Client extends Thread {

    private Socket socket;
    private Connection connection;
    private final String LOCALHOST = "127.0.0.1";
    private final int PORT = 12345;

    public Client() throws IOException {
        System.out.println("Attempting connection\n");
        socket = new Socket(LOCALHOST, PORT);
        System.out.println("Connected to: " + socket.getInetAddress().getHostName());
        runClient();
    }

    /**
     * The method runClient initiates the connection with the server.
     */
    public void runClient() {
        try {
            this.connection = new Connection(socket);
            connection.getStreams();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method readInt receives an integer from the one sent by the server.
     *
     * @return integer received.
     * @throws IOException if the current connection is interrupted
     */
    public int readInt() throws IOException {
        return connection.readInt();
    }

    /**
     * The method writeInt takes care of sending integer to the server.
     *
     * @param integer integer that sends to the server.
     * @throws IOException if the current connection is interrupted
     */
    public void writeInt(int integer) throws IOException {
        connection.writeInt(integer);
    }

    /**
     * The method readUTF receives an text from the one sent by the server.
     *
     * @return text received.
     * @throws IOException
     */
    public String readUTF() throws IOException {
        return connection.readUTF();
    }

    /**
     * The method readBoolean receives an integer from the one sent by the
     * server.
     *
     * @return integer received.
     * @throws IOException if the current connection is interrupted
     */
    public boolean readBoolean() throws IOException {
        return connection.readBoolean();
    }

    /**
     * The method writeUTF takes care of sending text to the server.
     *
     * @param string text that sends to the server.
     * @throws IOException if the current connection is interrupted
     */
    public void writeUTF(String string) throws IOException {
        connection.writeUTF(string);
    }

    /**
     * The method processConnection reads and displays the text it receives from
     * the server.
     *
     * @throws IOException if the current connection is interrupted
     */
    public void processConnection() throws IOException {
        JOptionPane.showMessageDialog(null, connection.readInt());
    }

    /**
     * The method closeClient is responsible for closing connection.
     */
    public void closeClient() {
        try {
            connection.closeConnection();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
