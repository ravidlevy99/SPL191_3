package bgu.spl.net.BGS;

public interface Message {

    public Message decodeNextByte(byte b);

    public byte[] encode();
}
