package bgu.spl.net.BGS;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PrivateMessage extends MessageFromClient {

    private String username, content;
    private int zeroCounter;
    private boolean isDone;

    public PrivateMessage()
    {
        super();
        username = "";
        content = "";
        zeroCounter = 0;
        isDone = false;
    }

    @Override
    public Message decodeNextByte(byte b) {
        if (currentByte >= bytes.length) {
            bytes = Arrays.copyOf(bytes, currentByte * 2);
        }
        if(b == '\0')
        {
            zeroCounter++;
            if(zeroCounter == 1) {
                username = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
                currentByte = 0;
            }
            else {
                content = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
                currentByte = 0;
            }
        }

        else {
            bytes[currentByte] = b;
            currentByte++;
        }

        if(zeroCounter == 2) {
            isDone = true;
            return this;
        }

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

    public String getContent()
    {
        return content;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
