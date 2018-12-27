package bgu.spl.net.BGS;

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

    public Message decodeNextByte(byte b, int len)
    {
        if(b == '\0')
        {
            zeroCounter++;
            if(zeroCounter == 1)
                for (int i = 0; i < bytes.length; i++)
                    username += (char) bytes[i];
            else
                for (int i = username.length(); i < bytes.length; i++)
                    password += (char) bytes[i];
        }

        else
            bytes[len] = b;

        if(zeroCounter == 2)
            return this;

        return null;
    }
}
