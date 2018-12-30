package bgu.spl.net.BGS;

public class ACKMessage extends MessageFromServer {

    private int opcode;
    private String optional;

    public ACKMessage()
    {
        super();
    }
}
