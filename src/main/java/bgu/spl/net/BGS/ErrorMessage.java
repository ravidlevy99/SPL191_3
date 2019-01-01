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
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((11 >> 8) & 0xFF);
        bytesArr[1] = (byte)(11 & 0xFF);

        byte[] bytes = new byte[2];
        bytes[0] = (byte)((opcode >> 8)& 0xFF);
        bytes[1] = (byte)(opcode & 0xFF);

        byte[] output ={bytesArr[0] , bytesArr[1] , bytes[0] , bytes[1]};
        return output;
    }
}
