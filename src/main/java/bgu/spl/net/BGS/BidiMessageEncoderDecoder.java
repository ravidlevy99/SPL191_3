package bgu.spl.net.BGS;

import bgu.spl.net.api.MessageEncoderDecoder;


public class BidiMessageEncoderDecoder implements MessageEncoderDecoder<Message> {

    private Message message;
    private short opcode = 0;
    private int len = 0;

    public Message get() {
        if (opcode < 9)
            return getFromClient();
        else if (opcode < 12)
            return getFromServer();
        else
            return null;
    }

    public MessageFromClient getFromClient() {
        if (opcode == 1)
            return new RegisterMessage();
        else if (opcode == 2)
            return new LoginMessage();
        else if (opcode == 3)
            return new LogoutMessage();
        else if (opcode == 4)
            return new FollowMessage();
        else if (opcode == 5)
            return new PostMessage();
        else if (opcode == 6) //Sliding into her DMs
            return new PrivateMessage();
        else if (opcode == 7)
            return new UserListMessage();
        else if (opcode == 8)
            return new StatsMessage();
        else
            return null;
    }

    public MessageFromServer getFromServer() {
        if (opcode == 9)
            return new NotificationMessage();
        else if (opcode == 10)
            return new ACKMessage();
        else if (opcode == 11)
            return new ErrorMessage();
        else
            return null;
    }

    @Override
    public Message decodeNextByte(byte nextByte) {

        if(len < 2)
        {
            if(len == 0)
                opcode += (short) (nextByte & 0xff << 8);
            else
                opcode += (short) (nextByte & 0xff);
            len++;
            return null;
        }
        if (len == 2)
        {
            message = get();
            len = 0;
            opcode = 0;
        }
        return ((MessageFromClient)message).decodeNextByte(nextByte);
    }

    @Override
    public byte[] encode(Message message)
    {
        return ((MessageFromServer)message).encode();
    }
}