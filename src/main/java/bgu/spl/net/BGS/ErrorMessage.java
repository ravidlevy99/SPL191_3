package bgu.spl.net.BGS;

public class ErrorMessage extends MessageFromServer {

    private short opcode;


    public ErrorMessage(short opcode)
    {
        super();
        this.opcode = opcode;
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
