package bgu.spl.net.BGS;

public class NotificationMessage extends MessageFromServer {

    private String username, content;
    private short PMorPublic;

    public NotificationMessage(short PMorPublic, String username, String content)
    {
        super();
        this.PMorPublic = PMorPublic;
        this.username = username;
        this.content = content;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
