package bgu.spl.net.BGS;

public class PrivateMessage extends MessageFromClient {

    private String username, content;

    public PrivateMessage(String content)
    {
        username = content.substring(3, 3 + content.substring(3).indexOf(' '));
        this.content = content.substring(4 + content.substring(3).indexOf(' '));
    }
}
