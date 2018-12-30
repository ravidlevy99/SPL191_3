package bgu.spl.net.BGS;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RegsiterMessage extends MessageFromClient {

    private int zeroCounter;
    private String username, password;
    private static final short opcode = 1;

    public RegsiterMessage()
    {
        super();
        zeroCounter = 0;
        username = "";
        password = "";
    }

    public Message decodeNextByte(byte b)
    {
        if (currentByte >= bytes.length) {
            bytes = Arrays.copyOf(bytes, currentByte * 2);
        }
        if(b == '\0')
        {
            zeroCounter++;
            if(zeroCounter == 1)
                username = popString();
            else
                password = popString();
        }

        else {
            bytes[currentByte] = b;
            currentByte++;
        }

        if(zeroCounter == 2)
            return this;

        return null;
    }

    public String popString()
    {
        String output = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
        currentByte = 0;
        return output;
    }
}
