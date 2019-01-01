package bgu.spl.net.BGS;

public class NotificationMessage extends MessageFromServer {

    private String username, content;
    private short PMorPublic;// 0-PM 1-public message

    public NotificationMessage()
    {
        super();
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    public void setData(short PMorPublic , String username , String content)
    {
        this.PMorPublic = PMorPublic;
        this.username = username;
        this.content = content;
    }
}
