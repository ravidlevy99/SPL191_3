package bgu.spl.net.BGS;

public class StatsMessage extends MessageFromClient {

    private String username;

    public StatsMessage(String content)
    {
        super();
        username = content.substring(1 + content.indexOf(' '));
    }
}
