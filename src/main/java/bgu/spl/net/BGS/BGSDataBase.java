package bgu.spl.net.BGS;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import sun.rmi.runtime.Log;

import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class BGSDataBase {

    private ConcurrentHashMap<String, String> UserInfo;
    private ConcurrentHashMap<Integer, String> LoggedInUsers;
    private ConcurrentHashMap<String, BlockingDeque<String>> FollowList;
    private ConcurrentHashMap<String, BlockingDeque<String>> UnFollowList;


    public BGSDataBase() {
        UserInfo = new ConcurrentHashMap<>();
        LoggedInUsers = new ConcurrentHashMap<>();
        FollowList = new ConcurrentHashMap<>();
        UnFollowList = new ConcurrentHashMap<>();
    }

    public boolean checkPassword(String userName, String password) {
        if(!checkIfAlreadyRegistered(userName))
            return false;
        String currentPassword = UserInfo.get(userName);
        return password.equals(currentPassword);
    }

    public void registerUser(String userName, String password) {
        for (BlockingDeque BQ : UnFollowList.values())
            BQ.add(userName);
        FollowList.put(userName, new LinkedBlockingDeque<>());
        UnFollowList.put(userName, new LinkedBlockingDeque<>());

        for (String user : UserInfo.keySet())
            UnFollowList.get(userName).add(user);
        UserInfo.put(userName, password);
    }

    public boolean checkIfAlreadyRegistered(String userName)
    {
        return UserInfo.containsKey(userName);
    }

    public boolean checkIfLoggedIn(int connectionId)
    {
        return LoggedInUsers.containsKey(connectionId);
    }

    public boolean checkIfLoggedIn(String userName)
    {
        return LoggedInUsers.contains(userName);
    }

    public void logInUser(String userName, int connectionId)
    {
        LoggedInUsers.put(connectionId, userName);
    }

    public LinkedList<String> follow(String userName, LinkedList<String> followList) {
        LinkedList<String> output = new LinkedList<>();
        BlockingDeque<String> currentFollowList = FollowList.get(userName);
        BlockingDeque<String> currentUnFollowList = UnFollowList.get(userName);

        for (String name : followList) {
            if (UserInfo.containsKey(name)) {
                if (currentUnFollowList.contains(name)) {
                    currentUnFollowList.remove(name);
                    currentFollowList.add(name);
                    output.add(name);
                }
            }
        }
        return output;
    }

    public LinkedList<String> unFollow(String userName , LinkedList<String> unFollowList)
    {
        LinkedList<String> output = new LinkedList<>();
        BlockingDeque<String> currentFollowList = FollowList.get(userName);
        BlockingDeque<String> currentUnFollowList = UnFollowList.get(userName);

        for(String name : unFollowList){
            if(UserInfo.containsKey(name)){
                if(currentFollowList.contains(name)){
                    currentFollowList.remove(name);
                    currentUnFollowList.add(name);
                    output.add(name);
                }
            }
        }
        return output;
    }

    public void logout(int connectionId)
    {
        LoggedInUsers.remove(connectionId);
    }

    public String getUsernameByConnectionId(int connectionId)
    {
        return LoggedInUsers.get(connectionId);
    }

    public BlockingDeque<String> returnFollowList(String username)
    {
        return FollowList.get(username);
    }

    public int getCID(String username)
    {
        for (int connectionId: LoggedInUsers.keySet()) {
            if(LoggedInUsers.get(connectionId).equals(username))
                return connectionId;
        }
        return -1;
    }

    public void addToNotify(String username, NotificationMessage message)
    {
        //TODO: Implement an addition of a notification to a data structure of a certain user a new hashmap
        throw new NotImplementedException();
    }
}
