package Chat.Server;

public class CommandHandler {
    private ServerManager server;

    public CommandHandler(ServerManager server) {
        this.server = server;
    }

    public int ProcessCommand(String message) {
        if ("/quit".equals(message))
            return 1;

        String[] parsedMessage = message.split(" ");
        if(parsedMessage.length == 3) {
            if(parsedMessage[0] == "ban")
                server.BanPerson(parsedMessage[1], Integer.parseInt(parsedMessage[2]));
        }


        return 0;
    }
}
