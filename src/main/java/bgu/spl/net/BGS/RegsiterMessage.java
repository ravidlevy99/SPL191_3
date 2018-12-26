package bgu.spl.net.BGS;

public class RegsiterMessage extends MessageFromClient {

    private String username, password;

    public RegsiterMessage(String content)
    {
        super();
        int firstSpace = content.indexOf(' ');
        int secondSpace = firstSpace + content.substring(firstSpace + 1).indexOf(' ');
        username = content.substring(firstSpace + 1, secondSpace);
        password = content.substring(secondSpace + 1);
    }

}
