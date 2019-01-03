package bgu.spl.net.BGS;

import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

public class FollowMessage extends MessageFromClient {

    private short numberOfUsers, usersleft;
    private char followOrUnfollow = '\0';
    private Vector<String> usernames;
    private boolean passedTwo = false;
    private boolean isDone;

    public FollowMessage()
    {
        super();
        numberOfUsers = 0;
        usersleft = 0;
        isDone = false;
        usernames = new Vector<>();
    }

    @Override
    public Message decodeNextByte(byte b)
    {
        if (currentByte >= bytes.length) {
            bytes = Arrays.copyOf(bytes, currentByte * 2);
        }

        if(followOrUnfollow == '\0') {
            followOrUnfollow = (char)b;
        }
        else
        {
            if(!passedTwo)
            {
                if(currentByte == 0) {
                    numberOfUsers += (short) (b & 0xff << 8);
                    currentByte++;
                }
                else
                    if(currentByte == 1) {
                        numberOfUsers += (short) (b & 0xff);
                        usersleft = numberOfUsers;
                        passedTwo = true;
                        currentByte = 0;
                      }
            }
            else
            {
                if(b == '\0')
                {
                    String userName = new String(bytes , 0 , currentByte , StandardCharsets.UTF_8);
                    usernames.add(userName);
                    currentByte = 0;
                    usersleft--;
                    if(usersleft == 0) {
                        isDone = true;
                        return this;
                    }

                }
                else
                {
                    bytes[currentByte] = b;
                    currentByte++;
                }
            }
        }
        return null;
    }

    @Override
    public void process(BidiMessagingProtocolImpl messagingProtocol) {
        messagingProtocol.processMessage(this);
    }

    public Vector<String> getUsernames()
    {
        return usernames;
    }

    public char followOrUnfollow()
    {
        return followOrUnfollow;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}

//        if(b == '\0')
//        {
//            String userName = new String(bytes, 0, currentByte, StandardCharsets.UTF_8);
//            currentByte = 0;
//            usersleft--;
//            currentByte = 0;
//            if(usersleft == 0) {
//                isDone = true;
//                return this;
//            }
//        }
//
//        else {
//            if (!passedTwo)
//            {
//                if (currentByte == 0)
//                    followOrUnfollow = (short) b;
//                else if (currentByte == 1 | currentByte == 2) {
//                    if (currentByte == 1)
//                        numberOfUsers += (short) (b & 0xff << 8);
//                    else {
//                        numberOfUsers += (short) (b & 0xff);
//                        usersleft = numberOfUsers;
//                        usernames = new String[numberOfUsers];
//                        passedTwo = true;
//                    }
//                }
//            }
//            bytes[currentByte] = b;
//            currentByte++;
//        }
//        return null;

