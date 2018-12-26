package bgu.spl.net.BGS;

public class ErrorMessage extends MessageFromServer {

    private int opcode;

    public ErrorMessage(String content)
    {
        super();
        opcode = Integer.parseInt(content.substring(1 + content.indexOf(' ')));
    }

}
