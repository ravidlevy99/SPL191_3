package bgu.spl.net.api.bidi;

import bgu.spl.net.BGS.BGSDataBase;
import bgu.spl.net.BGS.Message;
import bgu.spl.net.api.MessagingProtocol;

import java.sql.Connection;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Message> {

    boolean shouldTerminate = false;
    private BGSDataBase dataBase;
    private Connections<Message>

    @Override
    public void start(int connectionId, Connections<Message> connections) {

    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public void setDataBase(BGSDataBase dataBase)
    {
        this.dataBase = dataBase;
    }
}