package Chat.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            ClientManager clientManager = new ClientManager("127.0.0.1", 9099);
            clientManager.Start();


        } catch (IOException e) {
            System.out.println("Couldn't create client manager");
        }
    }
}
