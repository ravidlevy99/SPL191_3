package bgu.spl.net.BGS;

public class LogoutMessage extends MessageFromClient {

    public LogoutMessage() {
        super();
    }

    @Override
    public Message decodeNextByte(byte b) {
        return this;
    }
}
