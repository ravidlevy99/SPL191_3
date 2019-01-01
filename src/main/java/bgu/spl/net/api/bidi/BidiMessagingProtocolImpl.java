package bgu.spl.net.api.bidi;

import bgu.spl.net.BGS.*;
import bgu.spl.net.api.MessagingProtocol;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Message> {

    boolean shouldTerminate = false;
    private BGSDataBase dataBase;
    private Connections<Message> connections;
    private int connectionId;

    @Override
    public void start(int connectionId, Connections<Message> connections) {
        this.connections = connections;
        this.connectionId = connectionId;
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

    public void process(RegsiterMessage msg) {
        if (dataBase.checkIfAlreadyRegistered(msg.getUserName())) {
            ErrorMessage response = new ErrorMessage((short) (1));
            connections.send(connectionId, response);
        } else {
            dataBase.registerUser(msg.getUserName(), msg.getPassWord());
            ACKMessage response = new ACKMessage((short) (1));
            connections.send(connectionId, response);
        }
    }

    public void processMessage(LoginMessage msg)
    {
        if(!dataBase.checkIfLoggedIn(connectionId) & dataBase.checkPassword(msg.getUserName() , msg.getPassWord()))
        {
            dataBase.logInUser(msg.getUserName() , connectionId);
        }
        else
        {
            ErrorMessage response = new ErrorMessage((short)(2));
            connections.send(connectionId , response);
        }
    }

    public void processMessage(LogoutMessage msg)
    {
        if(dataBase.checkIfLoggedIn(connectionId))
            dataBase.logout(connectionId);
        else {
            ErrorMessage response = new ErrorMessage((short) (3));
            connections.send(connectionId, response);
        }
    }

    public void processMessage(FollowMessage msg) {

        String username = dataBase.getUsernameByConnectionId(connectionId);

        if(!dataBase.checkIfLoggedIn(connectionId))
        {
            ErrorMessage response = new ErrorMessage((short) (4));
            connections.send(connectionId, response);
        }

        else {
            LinkedList<String> list;
            if (msg.followOrUnfollow() == 0)
                list = dataBase.follow(username, msg.getUsernames());
            else
                list = dataBase.unFollow(username, msg.getUsernames());

            if (list.isEmpty()) {
                ErrorMessage response = new ErrorMessage((short) (4));
                connections.send(connectionId, response);
            }
            else
            {
                short numOfUsers = (short)(list.size());
                String userList ="";
                for(String user : list)
                    userList += list + "\0";
                ACKMessage response = new ACKMessage((short)(4));
                response.setUserList(msg.followOrUnfollow() ,numOfUsers , userList);
                connections.send(connectionId , response);
            }
        }
    }

    public void processMessage(PostMessage msg)
    {
        if(!dataBase.checkIfLoggedIn(connectionId))
        {
            ErrorMessage response = new ErrorMessage((short) (5));
            connections.send(connectionId, response);
        }
        else
        {
            List<String> usernames = msg.getUsernames();
            BlockingDeque<String> followList = dataBase.returnFollowList(msg.getUsername());
            if(followList != null)
                for (String username: followList)
                    usernames.add(username);

            for(String username: usernames)
            {
                if(dataBase.checkIfAlreadyRegistered(username))
                {
                    NotificationMessage notification = new NotificationMessage((short)(1), username, msg.getContent());
                    if(dataBase.checkIfLoggedIn(username))
                    {
                        int connectionId = dataBase.getCID(username);
                        if(connectionId != -1)
                            connections.send(connectionId, notification);
                        else
                            dataBase.addToNotify(username, notification);
                    }
                    else
                        dataBase.addToNotify(username, notification);
                }
            }
        }
    }
}
