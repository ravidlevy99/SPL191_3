package bgu.spl.net.BGS;

public class PostMessage extends MessageFromClient {

    private String content;

    public PostMessage(String content)
    {
        super();
        this.content = content.substring(1 + content.indexOf(' '));
    }
}
