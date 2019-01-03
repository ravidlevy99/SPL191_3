package bgu.spl.net.BGS;

import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PostMessage extends MessageFromClient {

    private String content, username;
    private List<String> usernames;
    private boolean isDone;

    public PostMessage()
    {
        super();
        content = "";
        username = null;
        usernames = new LinkedList<>();
        isDone = false;
    }


    @Override
    public Message decodeNextByte(byte b) {

        if (currentByte >= bytes.length)
            bytes = Arrays.copyOf(bytes, currentByte * 2);

        if(b == '\0')
        {
            if(username != null)
            {
                usernames.add(username);
                username = null;
            }
            content = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
            isDone = true;
            return this;
        }

        if(b == ' ' & username != null)
        {
            usernames.add(username);
            username = null;
        }

        if(username != null)
            username += b;

        if(b == '@' & username == null)
            username = "";

        bytes[currentByte] = b;
        currentByte++;
        return null;
    }

    public List<String> getUsernames()
    {
        return usernames;
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
    public void process(BidiMessagingProtocolImpl messagingProtocol) {
        messagingProtocol.processMessage(this);
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
