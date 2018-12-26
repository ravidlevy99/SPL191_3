package bgu.spl.net.BGS;

public class FollowMessage extends MessageFromClient {

    private boolean followOrUnfollow;
    private int numberOfUsers;
    private String[] usernames;

    public FollowMessage(String content)
    {
        super();
        if(content.charAt(7) == '0')
            followOrUnfollow = true;
        else
            followOrUnfollow = false;
        numberOfUsers = Integer.parseInt(content.substring(9, 9 + content.substring(9).indexOf(' ')));
        if(numberOfUsers > 0)
        {
            String users = content.substring(10 + content.substring(9).indexOf(' '));
            usernames = users.split( " ");
        }
    }
}
