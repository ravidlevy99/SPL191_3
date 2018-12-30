package bgu.spl.net.BGS;

public class ErrorMessage extends MessageFromServer {

    private short opcode;


    public ErrorMessage(short opcode)
    {
        super();
        this.opcode = opcode;
    }

}
