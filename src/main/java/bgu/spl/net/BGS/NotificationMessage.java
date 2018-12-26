package bgu.spl.net.BGS;

public class NotificationMessage extends MessageFromServer {

    private boolean PMorPublic;
    private String postingUser, content;

    public NotificationMessage(String content)
    {
        super();
        int firstSpace = content.indexOf(' ');
        if(content.substring(firstSpace).startsWith("PM"))
            PMorPublic = true;
        else
            PMorPublic = false;
        int secondSpace = firstSpace + 1 + content.substring(firstSpace + 1).indexOf(' ');
        postingUser = content.substring(firstSpace, secondSpace);
        int thirdSpace = secondSpace + 1 + content.substring(secondSpace + 1).indexOf(' ');
        this.content = content.substring(thirdSpace);
    }
}
