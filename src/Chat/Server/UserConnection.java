package Chat.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UserConnection {
    private ServerManager server;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private boolean isBanned;
    private long banEndTime;



    public UserConnection(ServerManager server, Socket socket) throws IOException {
        System.out.println("New user connected: " + socket.getInetAddress().toString() + ":" + socket.getPort());

        this.server = server;
        this.socket = socket;

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void Send(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            System.out.printf("Couldn't sent message to " + socket.getInetAddress());
        }
    }

    public boolean IsBanned() {
        if (System.currentTimeMillis() > banEndTime)
            isBanned = false;

        return isBanned;
    }

    public Socket getSocket() {
        return socket;
    }

    public void Ban() {
        Ban(0);
    }

    public void Ban(int minutes) {
        banEndTime = System.currentTimeMillis() + minutes * 60 * 1000;
        isBanned = true;
    }

    public void ReadMessage() {
        try {
            String mes = dataInputStream.readUTF();
            server.SentMessage(mes, socket);
        }
        catch (IOException e) {
            System.out.println("Failed to read message from client");
        }
    }


}
