package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    /**
     *
     * @param socket
     * @throws IOException
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.output = new DataOutputStream(socket.getOutputStream());
        this.output.flush();
        this.input = new DataInputStream(socket.getInputStream());
    }

    /**
     *
     * @param str
     * @throws IOException
     */
    public void writeUTF(String str) throws IOException {
        output.writeUTF(str);
    }

    /**
     *
     * @param bool
     * @throws IOException
     */
    public void writeBoolean(Boolean bool) throws IOException {
        output.writeBoolean(bool);
    }

    /**
     *
     * @param givenInt
     * @throws IOException
     */
    public void writeInt(int givenInt) throws IOException {
        output.writeInt(givenInt);
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public String readUTF() throws IOException {
        return input.readUTF();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public boolean readBoolean() throws IOException {
        return input.readBoolean();
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public int readInt() throws IOException {
        return input.readInt();
    }
    
    /**
     *
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        output.close();
        input.close();
        socket.close();
    }

}
