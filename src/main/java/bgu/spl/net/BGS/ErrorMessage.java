package bgu.spl.net.BGS;

public class ErrorMessage extends MessageFromServer {

    private short opcode;


    public ErrorMessage()
    {
        super();
    }

    public void setOpcode(short opcode)
    {
        this.opcode = opcode;
    }
    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
