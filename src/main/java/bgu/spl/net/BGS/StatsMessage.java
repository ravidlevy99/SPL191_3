package bgu.spl.net.BGS;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StatsMessage extends MessageFromClient {

    private String username;

    public StatsMessage()
    {
        super();
        username = "";
    }

    @Override
    public Message decodeNextByte(byte b) {
        if (currentByte >= bytes.length)
            bytes = Arrays.copyOf(bytes, currentByte * 2);

        if(b == '\0')
        {
            username = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
            return this;
        }
        bytes[currentByte] = b;
        currentByte++;
        return null;
    }
}
