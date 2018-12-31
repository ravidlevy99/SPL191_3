package bgu.spl.net.BGS;

public class ACKMessage extends MessageFromServer {

    private short opcode;
    private String optional;
    private short numOfFollows;
    private short followOPCODE;


    public ACKMessage(short opcode)
    {
        super();
        this.opcode = opcode;
    }

    public void setUserList(short followOPCODE ,short numOfFollows , String userList)
    {
        this.followOPCODE = followOPCODE;
        this.optional = userList;
        this.numOfFollows = numOfFollows;
    }


    @Override
    public Message decodeNextByte(byte b) {
        return null;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
