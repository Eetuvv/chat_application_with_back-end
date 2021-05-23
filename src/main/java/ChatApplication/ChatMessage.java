package ChatApplication;

public class ChatMessage {

    private final String user;
    private final String timestamp;
    private final String message;
    private final String channel;

    public ChatMessage(String channel, String user, String message, String timestamp) {
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

    public String getTimestamp() {
        return this.timestamp;
    }
}
