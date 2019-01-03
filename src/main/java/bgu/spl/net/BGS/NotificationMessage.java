package bgu.spl.net.BGS;

public class NotificationMessage extends MessageFromServer {

    private String username, content;
    private String PMorPublic;// 0-PM 1-public message

    public NotificationMessage()
    {
        super();
    }

    public void setData(String PMorPublic , String username , String content)
    {
        this.PMorPublic = PMorPublic;
        this.username = username;
        this.content = content;
    }

    @Override
    public byte[] encode() {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((9 >> 8) & 0xFF);
        bytesArr[1] = (byte)(9 & 0xFF);

        byte[] NotificationType = PMorPublic.getBytes();
        byte[] PostingUser = username.getBytes();
        byte[] Content = content.getBytes();

        byte[] output = new byte[5+PostingUser.length+Content.length];
        output[0] = bytesArr[0];
        output[1] = bytesArr[1];
        output[2] = NotificationType[0];

        for(int i = 0 ; i < PostingUser.length ; i++)
            output[3+i] = PostingUser[i];
        output[3+PostingUser.length] = '\0';

        for(int i = 0 ; i < Content.length ; i++)
            output[4+PostingUser.length+i] = Content[i];
        output[4+PostingUser.length +Content.length] = '\0';

        return output;

    }

    @Override
    public boolean isDone() {
        return true;
    }
}
