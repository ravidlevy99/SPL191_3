package bgu.spl.net.BGS;

public class ACKMessage extends MessageFromServer {

    private int opcode;
    private String optional;

    public ACKMessage(String content)
    {
        super();
        opcode = Integer.parseInt(content.substring(4, 6));
        optional = content.substring(6);
    }
}
