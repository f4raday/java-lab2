package Chat.Client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;

public class ConnectionHoster extends Thread{
    ClientManager client;
    Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ConnectionHoster(ClientManager client, Socket socket) throws IOException{
        this.client = client;
        this.socket = socket;

        dataInputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = dataInputStream.readUTF();
                client.PrintMessage(message);
            } catch (IOException e) {
                System.out.println("Failed to read message");
                client.Stop();
                break;
            }
        }
    }
}
