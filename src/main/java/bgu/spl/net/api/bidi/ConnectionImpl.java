package bgu.spl.net.api.bidi;

import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionImpl <T> implements Connections<T> {

    private ConcurrentHashMap<Integer , ConnectionHandler<T>> connectionHandlerHashMap;

    public ConnectionImpl()
    {
        connectionHandlerHashMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean send(int connectionId, T msg) {
        ConnectionHandler<T> connectionHandler = connectionHandlerHashMap.get(connectionId);
        connectionHandler.send(msg);
    }

    @Override
    public void broadcast(T msg) {
        for(ConnectionHandler<T> C : connectionHandlerHashMap.values())
            C.send(msg);
    }

    @Override
    public void disconnect(int connectionId) {
        if(connectionHandlerHashMap.containsKey(connectionId))
            connectionHandlerHashMap.remove(connectionId);
    }

    public void connect (int connectionId , ConnectionHandler<T> connectionHandler)
    {
        connectionHandlerHashMap.putIfAbsent(connectionId , connectionHandler);
    }
}
