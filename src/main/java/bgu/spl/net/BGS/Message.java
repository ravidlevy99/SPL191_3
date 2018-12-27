package bgu.spl.net.BGS;

public interface Message {

    public Message decodeNextByte(byte b, int len);
}
