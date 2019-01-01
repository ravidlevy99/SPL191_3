package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.BGS.BGSDataBase;
import bgu.spl.net.BGS.BidiMessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.srv.Server;

public class ReactorMain {
    public static void main(String[] args)
    {
        BGSDataBase dataBase = new BGSDataBase();
        Server.reactor(Integer.parseInt(args[1]) , Integer.parseInt(args[0]) , () -> new BidiMessagingProtocolImpl(dataBase) , () -> new BidiMessageEncoderDecoder()).serve();
    }
}
