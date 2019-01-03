package bgu.spl.net.BGS;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StatsMessage extends MessageFromClient {

    private String username;
    private boolean isDone;

    public StatsMessage()
    {
        super();
        username = "";
        isDone = false;
    }

    @Override
    public Message decodeNextByte(byte b) {
        if (currentByte >= bytes.length)
            bytes = Arrays.copyOf(bytes, currentByte * 2);

        if(b == '\0')
        {
            username = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
            isDone = true;
            return this;
        }
        bytes[currentByte] = b;
        currentByte++;
        return null;
    }

    @Override
    public void process(BidiMessagingProtocolImpl messagingProtocol) {
        messagingProtocol.processMessage(this);
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
