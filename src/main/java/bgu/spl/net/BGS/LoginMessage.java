package bgu.spl.net.BGS;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LoginMessage extends MessageFromClient {

    private String username, password;
    private int zeroCounter;

    public LoginMessage()
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

    @Override
    public byte[] encode() {
        return new byte[0];
    }

    public String popString()
    {
        String output = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
        currentByte = 0;
        return output;
    }

    public String getUserName(){
        return username;
    }
    public String getPassWord(){
        return password;
    }
}
