package bgu.spl.net.BGS;

public class LoginMessage extends MessageFromClient {

    private String username, password;

    public LoginMessage(String content)
    {
        super();
        int firstSpace = content.indexOf(' ');
        int secondSpace = firstSpace + content.substring(firstSpace + 1).indexOf(' ');
        username = content.substring(firstSpace + 1, secondSpace);
        password = content.substring(secondSpace + 1);
    }
}
