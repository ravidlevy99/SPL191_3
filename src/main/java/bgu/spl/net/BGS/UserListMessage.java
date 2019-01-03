package bgu.spl.net.BGS;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

public class UserListMessage extends MessageFromClient {
        private boolean isDone;

    public UserListMessage()
    {
        super();
        isDone = true;
    }


    @Override
    public Message decodeNextByte(byte b) {
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
