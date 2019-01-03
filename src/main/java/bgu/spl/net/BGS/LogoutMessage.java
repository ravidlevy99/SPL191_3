package bgu.spl.net.BGS;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

public class LogoutMessage extends MessageFromClient {
    private boolean isDone;

    public LogoutMessage() {
        super();
        isDone = true;

    }

    @Override
    public Message decodeNextByte(byte b)
    {
        isDone = true;
        return this;
    }

    @Override
    public void process(BidiMessagingProtocolImpl messagingProtocol) {
        messagingProtocol.processMessage(this);
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
