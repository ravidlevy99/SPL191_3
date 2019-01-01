package bgu.spl.net.api.bidi;

import bgu.spl.net.BGS.*;

import javax.management.Notification;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

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
        ((MessageFromClient)msg).process(this);
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public void setDataBase(BGSDataBase dataBase)
    {
        this.dataBase = dataBase;
    }

    public void processMessage(RegisterMessage msg)
    {
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
        if(!dataBase.checkIfLoggedIn(connectionId) & dataBase.checkPassword(msg.getUserName(), msg.getPassWord()))
        {
            dataBase.logInUser(msg.getUserName(), connectionId);
            BlockingDeque<NotificationMessage> notifications = dataBase.getUpdated(msg.getUserName());
            for (NotificationMessage message: notifications)
            {
                notifications.remove(message);
                connections.send(connectionId, message);
            }
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

    public void processMessage(FollowMessage msg)
    {

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
                String userList = "";
                for(String user : list)
                    userList += user + "\0";
                ACKMessage response = new ACKMessage((short)(4));
                response.setUserList(msg.followOrUnfollow(), numOfUsers, userList);
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
            dataBase.post(connectionId);
            List<String> usernames = msg.getUsernames();
            BlockingDeque<String> followList = dataBase.returnFollowList(msg.getUsername());
            if(followList != null)
                for (String username: followList)
                    usernames.add(username);

            for(String username: usernames)
            {
                if(dataBase.checkIfAlreadyRegistered(username))
                {
                    NotificationMessage notification = new NotificationMessage((short)(1), dataBase.getUsernameByConnectionId(connectionId), msg.getContent());
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

    public void processMessage(PrivateMessage msg)
    {
        if (!dataBase.checkIfLoggedIn(connectionId) | !dataBase.checkIfAlreadyRegistered(msg.getUsername()))
        {
            ErrorMessage response = new ErrorMessage((short) (6));
            connections.send(connectionId, response);
        }
        else
        {
            NotificationMessage notification = new NotificationMessage((short)(0), dataBase.getUsernameByConnectionId(connectionId), msg.getContent());
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
            ErrorMessage response = new ErrorMessage((short) (7));
            connections.send(connectionId, response);
        }
        else
        {
            ACKMessage response = new ACKMessage((short)(7));
            String userList ="";
            Collection<String> usernames = dataBase.getUsernames();
            for(String user : usernames)
                userList += user + "\0";
            response.setUserList((short)usernames.size(), userList);
            connections.send(connectionId , response);
        }
    }

    public void processMessage(StatsMessage msg)
    {
        if (!dataBase.checkIfLoggedIn(connectionId) | !dataBase.checkIfAlreadyRegistered(msg.getUsername()))
        {
            ErrorMessage response = new ErrorMessage((short) (8));
            connections.send(connectionId, response);
        }
        else
        {
            Stats stats = dataBase.getStats(msg.getUsername());
            ACKMessage response = new ACKMessage((short)(8));
            response.setStats(stats.getNumOfFollowing(), stats.getNumOfFollowers(), stats.getNumOfPosts());
            connections.send(connectionId, response);
        }
    }
}
