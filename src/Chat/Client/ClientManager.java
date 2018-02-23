package Chat.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager {

    private Socket socket;
    private ConnectionHoster hoster;
    private DataOutputStream dataOutputStream;

    private boolean isWorking;

    public ClientManager(String IP, int port) throws IOException{
        socket = new Socket(IP, port);

        hoster = new ConnectionHoster(this, socket);

        isWorking = true;

        dataOutputStream = new DataOutputStream(socket.getOutputStream());

    }

    public void Start() {
        hoster.start();

        Scanner scanner = new Scanner(System.in);
        String mes = "";

        while(!"/quit".equals(mes) && isWorking) {
            try {
                mes = scanner.nextLine();
                dataOutputStream.writeUTF(mes);
                dataOutputStream.flush();
            } catch (IOException e) {
                System.out.println("Failed to send message");
                isWorking = false;
            }
        }

        try {
            dataOutputStream.close();
        } catch (IOException e) {
            System.out.println("Failed to close output stream");
        }
    }

    public void Stop() {
        isWorking = false;
    }

    public void PrintMessage(String message) {
        System.out.println(message);
    }
}
