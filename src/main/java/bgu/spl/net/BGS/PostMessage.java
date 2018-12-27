package bgu.spl.net.BGS;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PostMessage extends MessageFromClient {

    private String content;

    public PostMessage()
    {
        super();
        content = "";
    }


    @Override
    public Message decodeNextByte(byte b) {

        if (currentByte >= bytes.length)
            bytes = Arrays.copyOf(bytes, currentByte * 2);

        if(b == '\0')
        {
            content = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
            return this;
        }
        bytes[currentByte] = b;
        currentByte++;
        return null;
    }
}
