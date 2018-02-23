package Chat.Server;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerManager server = new ServerManager(9090);
        server.Start();
    }
}
