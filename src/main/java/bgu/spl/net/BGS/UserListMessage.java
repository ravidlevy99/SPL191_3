package bgu.spl.net.BGS;

public class UserListMessage extends MessageFromClient {

    public UserListMessage()
    {
        super();
    }


    @Override
    public Message decodeNextByte(byte b) {
        return this;
    }
}
