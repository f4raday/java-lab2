package Chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerManager {
    private int port;
    private CommandHandler commandHandler;

    private volatile ArrayList<UserConnection> connections;

    public ServerManager(int port) {
        this.port = port;
        commandHandler = new CommandHandler(this);
        connections = new ArrayList<>();
    }

    public void Start() throws IOException{
        ServerSocket serverSocket =
                new ServerSocket(9099);

        ConnectionHoster hoster = new ConnectionHoster(this, serverSocket);
        hoster.start();


        while (true){
            ReadMessages();
        }
    }

    public synchronized void AddConnection(UserConnection connection) {
        connections.add(connection);
    }

    public void SentMessage(String message, Socket socket) {
        if (commandHandler.ProcessCommand(message) != 0)
            return;
        else {
            String messageToSent = socket.getInetAddress().toString() + ":" + socket.getPort() + " - " + message;


            for (UserConnection connection : connections) {
                if (!connection.IsBanned())
                    connection.Send(messageToSent);
            }

            System.out.println(messageToSent);

        }
    }

    private synchronized void ReadMessages() {
        for (int i=0; i<connections.size(); i++) {
            connections.get(i).ReadMessage();
        }
    }

    public void BanPerson(String address, int duration) {

        String[] addressParts = address.split(":");

        if(addressParts.length == 2) {
            String IP = addressParts[0];
            int port = Integer.parseInt(addressParts[1]);

            for (UserConnection connection : connections) {
                if (connection.getSocket().getInetAddress().toString() == IP && connection.getSocket().getPort() == port)
                    connection.Ban(duration);
            }
        }
    }



    public void Stop() {

    }
}
