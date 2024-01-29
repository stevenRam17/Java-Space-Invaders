/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * The class Connection
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class Connection {

    private DataInputStream input;
    private DataOutputStream output;
   // private ObjectInputStream objectInput;
    private final Socket socket;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
    }

    /**
     * The method getStreams initialize the input and output methods
     *
     * @throws IOException
     */
    public void getStreams() throws IOException {
        this.output = new DataOutputStream(socket.getOutputStream());
        this.output.flush();
        this.input = new DataInputStream(socket.getInputStream());
    }

    /**
     * The method writeUTF takes care of sending text to the server.
     *
     * @param str text that sends to the server.
     * @throws IOException
     */
    public void writeUTF(String str) throws IOException {
        output.writeUTF(str);
    }

    /**
     * The method writeBoolean takes care of sending boolean to the server.
     *
     * @param bool boolean that sends to the server.
     * @throws IOException
     */
    public void writeBoolean(boolean bool) throws IOException {
        output.writeBoolean(bool);
    }

    /**
     * The method writeInt takes care of sending integer to the server.
     *
     * @param givenInt
     * @throws IOException
     */
    public void writeInt(int givenInt) throws IOException {
        output.writeInt(givenInt);
    }

    /**
     * The method readUTF receives an text from the one sent by the server.
     *
     * @return text received.
     * @throws IOException
     */
    public String readUTF() throws IOException {
        return input.readUTF();
    }

    /**
     * The method readBoolean receives an boolean from the one sent by the
     * server.
     *
     * @return boolean received.
     * @throws IOException
     */
    public boolean readBoolean() throws IOException {
        return input.readBoolean();
    }

    /**
     * The method readInt receives an integer from the one sent by the server.
     *
     * @return integer received.
     * @throws IOException
     */
    public int readInt() throws IOException {
        return input.readInt();
    }

    /**
     * The method closeConnection is responsible for closing the client
     * connection.
     *
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        input.close();
        output.close();
        socket.close();
    }
}
