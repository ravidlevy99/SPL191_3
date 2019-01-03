package bgu.spl.net.BGS;

public class ACKMessage extends MessageFromServer {

    private short opcode, numOfUsers;
    private String optional;
    private short numOfFollowing, numOfFollowers, numOfPosts;


    public ACKMessage()
    {
        super();
    }

    public void setFollowUserList( short numOfUsers, String userList)
    {
        this.optional = userList;
        this.numOfUsers = numOfUsers;
        numOfFollowing = -1;
        numOfFollowers = -1;
        numOfPosts = -1;
    }

    public void setUserList(short numOfUsers, String userList)
    {
        this.numOfUsers = numOfUsers;
        this.optional = userList;
        numOfFollowing = -1;
        numOfFollowers = -1;
        numOfPosts = -1;
    }

    public void setStats(short numOfFollowing, short numOfFollowers, short numOfPosts)
    {
        optional = "";
        numOfUsers = -1;
        this.numOfFollowing = numOfFollowing;
        this.numOfFollowers = numOfFollowers;
        this.numOfPosts = numOfPosts;
    }

    public void setOpcode(short opcode)
    {
        this.opcode = opcode;
    }

    @Override
    public byte[] encode() {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((10 >> 8) & 0xFF);
        bytesArr[1] = (byte)(10 & 0xFF);

        byte [] byteOpCode = new byte[2];
        byteOpCode[0] = (byte)((opcode >> 8) & 0xFF);
        byteOpCode[1] = (byte)(opcode & 0xFF);

        switch (opcode){
            case 4:
            {
                byte [] userList = optional.getBytes();
                byte[] output = new byte[6+userList.length];
                output[0] = bytesArr[0];
                output[1] = bytesArr[1];
                output[2] = byteOpCode[0];
                output[3] = byteOpCode[1];
                byte[] numOfUsersBytes = new byte[2];
                numOfUsersBytes[0] = (byte)((numOfUsers >> 8) & 0xFF);
                numOfUsersBytes[1] = (byte)(numOfUsers & 0xFF);
                output[4] = numOfUsersBytes[0];
                output[5] = numOfUsersBytes[1];

                for(int i = 0 ; i < userList.length ; i++)
                    output[6+i] = userList[i];
                return output;
            }
            case 7:
            {
                byte [] userList = optional.getBytes();
                byte[] output = new byte[6+userList.length];
                byte[] numOfUsersBytes = new byte[2];
                numOfUsersBytes[0] = (byte)((numOfUsers >> 8) & 0xFF);
                numOfUsersBytes[1] = (byte)(numOfUsers & 0xFF);
                output[0] = bytesArr[0];
                output[1] = bytesArr[1];
                output[2] = byteOpCode[0];
                output[3] = byteOpCode[1];
                output[4] = numOfUsersBytes[0];
                output[5] = numOfUsersBytes[1];
                for(int i = 0 ; i < userList.length ; i++)
                    output[6+i] = userList[i];
                return output;
            }
            case 8:
            {
                byte[] output = new byte [10];

                byte[] numPostByte = new byte[2];
                numPostByte[0] = (byte)((numOfPosts >> 8) & 0xFF);
                numPostByte[1] = (byte)(numOfPosts & 0xFF);

                byte[] numFollowersByte = new byte[2];
                numFollowersByte[0] = (byte)((numOfFollowers >> 8) & 0xFF);
                numFollowersByte[1] = (byte)(numOfFollowers & 0xFF);

                byte[] numFollowingByte = new byte[2];
                numFollowingByte[0] = (byte)((numOfFollowing >> 8) & 0xFF);
                numFollowingByte[1] = (byte)(numOfFollowing & 0xFF);

                output[0] = bytesArr[0];
                output[1] = bytesArr[1];
                output[2] = byteOpCode[0];
                output[3] = byteOpCode[1];
                output[4] = numPostByte[0];
                output[5] = numPostByte[1];
                output[6] = numFollowersByte[0];
                output[7] = numFollowersByte[1];
                output[8] = numFollowingByte[0];
                output[9] = numFollowingByte[1];
                return output;
            }
        }

        byte[] output ={bytesArr[0] , bytesArr[1] , byteOpCode[0] , byteOpCode[1]};
        return output;
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
