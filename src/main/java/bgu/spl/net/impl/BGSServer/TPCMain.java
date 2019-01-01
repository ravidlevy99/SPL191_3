package bgu.spl.net.impl.BGSServer;

import bgu.spl.net.BGS.BGSDataBase;
import bgu.spl.net.BGS.BidiMessageEncoderDecoder;
import bgu.spl.net.api.bidi.BidiMessagingProtocolImpl;
import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main (String[] args)
    {
        BGSDataBase dataBase = new BGSDataBase();

        Server.threadPerClient(Integer.parseInt(args[0]) , () -> new BidiMessagingProtocolImpl(dataBase) , ()-> new BidiMessageEncoderDecoder()).serve();

    }
}
