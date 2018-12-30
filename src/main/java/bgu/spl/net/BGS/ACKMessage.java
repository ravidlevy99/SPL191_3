package bgu.spl.net.BGS;

public class ACKMessage extends MessageFromServer {

    private short opcode;
    private String optional;

    public ACKMessage(short opcode)
    {
        super();
        this.opcode = opcode;
    }

    public ACKMessage(short opcode , String optional)
    {
        super();
        this.opcode = opcode;
        this.optional = optional;
    }
}
