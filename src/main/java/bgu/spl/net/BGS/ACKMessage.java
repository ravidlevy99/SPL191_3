package bgu.spl.net.BGS;

public class ACKMessage extends MessageFromServer {

    private short opcode, numOfUsers, followOPCODE;
    private String optional;
    private short numOfFollowing, numOfFollowers, numOfPosts;


    public ACKMessage(short opcode)
    {
        super();
        this.opcode = opcode;
    }

    public void setUserList(short followOPCODE, short numOfUsers, String userList)
    {
        this.followOPCODE = followOPCODE;
        this.optional = userList;
        this.numOfUsers = numOfUsers;
        numOfFollowing = -1;
        numOfFollowers = -1;
        numOfPosts = -1;
    }

    public void setUserList(short numOfUsers, String userList)
    {
        followOPCODE = -1;
        this.numOfUsers = numOfUsers;
        this.optional = userList;
        numOfFollowing = -1;
        numOfFollowers = -1;
        numOfPosts = -1;
    }

    public void setStats(short numOfFollowing, short numOfFollowers, short numOfPosts)
    {
        followOPCODE = -1;
        optional = "";
        numOfUsers = -1;
        this.numOfFollowing = numOfFollowing;
        this.numOfFollowers = numOfFollowers;
        this.numOfPosts = numOfPosts;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
