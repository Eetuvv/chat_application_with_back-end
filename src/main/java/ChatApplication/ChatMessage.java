package ChatApplication;

public class ChatMessage {
    
    String user;
    String timestamp;
    String message;
    String channel;
    
    public ChatMessage(String channel, String user, String message) {
        Authentication authentication = Authentication.getInstance();
        
        if (authentication.getLoggedUser().isEmpty()) {
            this.user = "Anonymous";
        } else {
            this.user = authentication.getLoggedUser();
        }
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
