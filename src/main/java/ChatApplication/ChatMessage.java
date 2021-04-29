package ChatApplication;

public class ChatMessage {
    
    String user;
    String timestamp;
    String message;
    String channel;
    
    public ChatMessage(String channel, String user, String message, String timestamp) {
        Authentication authentication = Authentication.getInstance();
        
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;
        this.channel = channel;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public String getChannel() {
        return this.channel;
    }
    public String getMessage() {
        return this.message;
    }
}
