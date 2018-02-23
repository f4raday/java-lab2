package Chat.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionHoster extends Thread {
    ServerManager server;
    ServerSocket serverSocket;

    public ConnectionHoster(ServerManager server, ServerSocket serverSocket) {
        this.server = server;
        this.serverSocket = serverSocket;
    }

    public void run() {
        while (true){
            try {
                server.AddConnection(new UserConnection(server, serverSocket.accept()));
            }
            catch (IOException e) {
                System.out.println("Cannot accept socket");
            }
        }
    }
}
