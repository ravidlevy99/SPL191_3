package bgu.spl.net.BGS;

import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class BGSDataBase {

    private ConcurrentHashMap<String, String> UserInfo;
    private ConcurrentHashMap<String, Integer> LoggedInUsers;
    private ConcurrentHashMap<String, BlockingDeque<String>> FollowList;
    private ConcurrentHashMap<String, BlockingDeque<String>> UnFollowList;

    public BGSDataBase() {
        UserInfo = new ConcurrentHashMap<>();
        LoggedInUsers = new ConcurrentHashMap<>();
        FollowList = new ConcurrentHashMap<>();
        UnFollowList = new ConcurrentHashMap<>();
    }
    
    public boolean checkPassword(String userName, String password) {
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

    public boolean checkIfAlreadyRegistered(String userName) {
        return UserInfo.containsKey(userName);
    }

    public boolean checkIfLoggedIn(String userName) {
        return LoggedInUsers.containsKey(userName);
    }

    public void logInUser(String userName, int connectionId) {
        LoggedInUsers.put(userName, connectionId);
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

    public void logout(String userName)
    {
        LoggedInUsers.remove(userName);
    }
}
