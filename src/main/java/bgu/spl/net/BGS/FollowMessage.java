package bgu.spl.net.BGS;

public class FollowMessage extends MessageFromClient {

    private short followOrUnfollow, numberOfUsers, usersleft;
    private String[] usernames;
    private int index;

    public FollowMessage()
    {
        super();
        numberOfUsers = 0;
        usersleft = 0;
        index = 3;
    }

    public Message decodeNextByte(byte b, int len)
    {
        if(b == '\0')
        {
            usernames[numberOfUsers - usersleft] = "";
            for(int i = index; i<bytes.length; i++)
                usernames[numberOfUsers - usersleft] += bytes[i];
            index = bytes.length;
            usersleft--;
            if(usersleft == 0)
                return this;
        }
        else {
            bytes[len] = b;
            if (len == 0)
                followOrUnfollow = (short) b;
            else if (len == 1 | len == 2) {
                if (len == 1)
                    numberOfUsers += (short) (b & 0xff << 8);
                else {
                    numberOfUsers += (short) (b & 0xff);
                    usersleft = numberOfUsers;
                    usernames = new String[numberOfUsers];
                }
            }
        }
        return null;
    }
}
