package bgu.spl.net.BGS;

public class Stats {

    private short numOfPosts, numOfFollowers, numOfFollowing;

    public Stats()
    {
        numOfPosts = 0;
        numOfFollowers = 0;
        numOfFollowing = 0;
    }

    public void follow()
    {
        numOfFollowing++;
    }

    public void followed()
    {
        numOfFollowers++;
    }

    public void post()
    {
        numOfPosts++;
    }

    public void unfollow()
    {
        numOfFollowing--;
    }

    public void unfollowed()
    {
        numOfFollowers--;
    }

    public short getNumOfFollowing()
    {
        return numOfFollowing;
    }

    public short getNumOfFollowers()
    {
        return numOfFollowers;
    }

    public short getNumOfPosts()
    {
        return numOfPosts;
    }
}
