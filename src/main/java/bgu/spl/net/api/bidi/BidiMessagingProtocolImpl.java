package bgu.spl.net.api.bidi;

import bgu.spl.net.BGS.*;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;

public class BidiMessagingProtocolImpl implements BidiMessagingProtocol<Message> {

    boolean shouldTerminate = false;
    private BGSDataBase dataBase;
    private Connections<Message> connections;
    private int connectionId;

    public BidiMessagingProtocolImpl(BGSDataBase dataBase)
    {
        this.dataBase = dataBase;
    }

    @Override
    public void start(int connectionId, Connections<Message> connections) {
        this.connections = connections;
        this.connectionId = connectionId;
    }

    @Override
    public void process(Message msg) {
        ((MessageFromClient)msg).process(this);
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }


    public void processMessage(RegisterMessage msg)
    {
        ConcurrentHashMap<String, String> UserInfo  = dataBase.getUserInfo();
        synchronized (UserInfo){
            if (dataBase.checkIfAlreadyRegistered(msg.getUserName()) || dataBase.checkIfLoggedIn(connectionId, msg.getUserName())) {
                ErrorMessage response = new ErrorMessage();
                response.setOpcode((short) (1));
                connections.send(connectionId, response);
            } else {
                dataBase.registerUser(msg.getUserName(), msg.getPassWord());
                ACKMessage response = new ACKMessage();
                response.setOpcode((short)(1));
                connections.send(connectionId, response);
            }
        }
    }

    public void processMessage(LoginMessage msg)
    {
        ConcurrentHashMap<Integer, String> loginusers = dataBase.getLoggedInUsers();
        synchronized (loginusers) {
            if (!dataBase.checkIfLoggedIn(connectionId , msg.getUserName()) && dataBase.checkPassword(msg.getUserName(), msg.getPassWord())) {
                dataBase.logInUser(msg.getUserName(), connectionId);
                ACKMessage response = new ACKMessage();
                response.setOpcode((short) 2);
                connections.send(connectionId, response);
                BlockingDeque<NotificationMessage> notifications = dataBase.getUpdated(msg.getUserName());
                for (NotificationMessage message : notifications) {
                    notifications.remove(message);
                    connections.send(connectionId, message);
                }
            } else {
                ErrorMessage response = new ErrorMessage();
                response.setOpcode((short)(2));
                connections.send(connectionId, response);
            }
        }
    }

    public void processMessage(LogoutMessage msg)
    {
        if(dataBase.checkIfLoggedIn(connectionId)) {
            dataBase.logout(connectionId);
            ACKMessage response = new ACKMessage();
            response.setOpcode((short)3);
            connections.send(connectionId , response);
        }
        else {
            ErrorMessage response = new ErrorMessage();
            response.setOpcode((short) (3));
            connections.send(connectionId, response);
        }
    }

    public void processMessage(FollowMessage msg)
    {

        String username = dataBase.getUsernameByConnectionId(connectionId);

        if(!dataBase.checkIfLoggedIn(connectionId))
        {
            ErrorMessage response = new ErrorMessage();
            response.setOpcode((short) (4));
            connections.send(connectionId, response);
        }

        else {
            Vector<String> list;
            if (msg.followOrUnfollow() == '0')
                list = dataBase.follow(username, msg.getUsernames());
            else
                list = dataBase.unFollow(username, msg.getUsernames());

            if (list.isEmpty()) {
                ErrorMessage response = new ErrorMessage();
                response.setOpcode((short) (4));
                connections.send(connectionId, response);
            }
            else
            {
                short numOfUsers = (short)(list.size());
                String userList = "";
                for(String user : list)
                    userList += user + '\0';
                ACKMessage response = new ACKMessage();
                response.setOpcode((short)(4));
                response.setFollowUserList(numOfUsers, userList);
                connections.send(connectionId , response);
            }
        }
    }

    public void processMessage(PostMessage msg)
    {
        if(!dataBase.checkIfLoggedIn(connectionId))
        {
            ErrorMessage response = new ErrorMessage();
            response.setOpcode((short) (5));
            connections.send(connectionId, response);
        }
        else
        {

            dataBase.post(connectionId);
            ACKMessage response = new ACKMessage();
            response.setOpcode((short)5);
            connections.send(connectionId , response);
            List<String> usernames = msg.getUsernames();
            BlockingDeque<String> followList = dataBase.returnFollowList(dataBase.getUsernameByConnectionId(connectionId));
            if(followList != null)
                for (String username: followList)
                    usernames.add(username);

            for(String username: usernames)
            {
                if(dataBase.checkIfAlreadyRegistered(username))
                {
                    NotificationMessage notification = new NotificationMessage();
                    notification.setData("0", dataBase.getUsernameByConnectionId(connectionId), msg.getContent());
                    if(dataBase.checkIfLoggedIn(username))
                    {
                        int newconnectionId = dataBase.getCID(username);
                        if(newconnectionId != -1)
                            connections.send(newconnectionId, notification);
                        else
                            dataBase.addToNotify(username, notification);
                    }
                    else
                        dataBase.addToNotify(username, notification);
                }
            }
        }
    }

    public void processMessage(PrivateMessage msg)
    {
        if (!dataBase.checkIfLoggedIn(connectionId) | !dataBase.checkIfAlreadyRegistered(msg.getUsername()))
        {
            ErrorMessage response = new ErrorMessage();
            response.setOpcode((short) (6));
            connections.send(connectionId, response);
        }
        else
        {
            ACKMessage response = new ACKMessage();
            response.setOpcode((short)6);
            connections.send(connectionId , response);
            NotificationMessage notification = new NotificationMessage();
            notification.setData("1", dataBase.getUsernameByConnectionId(connectionId), msg.getContent());
            if(!dataBase.checkIfLoggedIn(dataBase.getCID(msg.getUsername())))
                dataBase.addToNotify(msg.getUsername(), notification);
            else
            {
                int connectionId = dataBase.getCID(msg.getUsername());
                if(connectionId != -1)
                    connections.send(connectionId, notification);
                else
                    dataBase.addToNotify(msg.getUsername(), notification);
            }
        }
    }

    public void processMessage(UserListMessage msg)
    {

        if (!dataBase.checkIfLoggedIn(connectionId))
        {
            ErrorMessage response = new ErrorMessage();
            response.setOpcode((short) (7));
            connections.send(connectionId, response);
        }
        else
        {
            ACKMessage response = new ACKMessage();
            response.setOpcode((short)(7));
            String userList ="";
            Queue<String> usernames = dataBase.getUsernames();
            for(String user : usernames)
                userList += user + '\0';
            response.setUserList((short)usernames.size(), userList);
            connections.send(connectionId , response);
        }
    }

    public void processMessage(StatsMessage msg)
    {
        if (!dataBase.checkIfLoggedIn(connectionId) | !dataBase.checkIfAlreadyRegistered(msg.getUsername()))
        {
            ErrorMessage response = new ErrorMessage();
            response.setOpcode((short) (8));
            connections.send(connectionId, response);
        }
        else
        {
            Stats stats = dataBase.getStats(msg.getUsername());
            ACKMessage response = new ACKMessage();
            response.setOpcode((short)(8));
            response.setStats(stats.getNumOfFollowing(), stats.getNumOfFollowers(), stats.getNumOfPosts());
            connections.send(connectionId, response);
        }
    }
}
