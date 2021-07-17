package ChatApplication;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatChannel {

    private String currentChannel;
    private final ServerConnection connection = ServerConnection.getInstance();

    public ChatChannel() {
        this.currentChannel = "Yleinen";
        addChannel("Yleinen");
    }

    public void addChannel(String channel) {
        Authentication authentication = Authentication.getInstance();
        ChatMessage msg = new ChatMessage(channel, " ", " ", " ");
        try {
            if (!authentication.getLoggedUser().isEmpty()) {
                connection.postMessageToChannel(authentication.getLoggedUser(), authentication.getPassword(), msg);
            }
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList listChannels() {
        Authentication authentication = Authentication.getInstance();
        try {
            return connection.listChannels(authentication.getLoggedUser(), authentication.getPassword());
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | java.security.cert.CertificateException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setCurrentChannel(String channel) {
        this.currentChannel = channel;
    }

    public String getCurrentChannel() {
        return this.currentChannel;
    }

    public ArrayList<ChatMessage> getMessagesFromChannel(String channel) {
        Authentication authentication = Authentication.getInstance();
        ArrayList<ChatMessage> msgs = new ArrayList<>();
        try {
            ArrayList<String> channels = connection.listChannels(authentication.getLoggedUser(), authentication.getPassword());
            if (channels.toString().contains(channel)) {;
                msgs = connection.getMessagesFromChannel(channel);
                if (msgs != null) {
                    return msgs;
                }
            } else {
                System.out.println("Could not get messages from channel:" + channel);
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | CertificateException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ChatMessage> getNewestMessages(String channel) {
        Authentication authentication = Authentication.getInstance();

        try {
            if (!authentication.getLoggedUser().isEmpty()) {
                return connection.getMessagesSince(channel);
            }
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean addMessageToChannel(ChatMessage msg) {
        Authentication authentication = Authentication.getInstance();

        try {
            ArrayList<String> channels = connection.listChannels(authentication.getLoggedUser(), authentication.getPassword());
            if (channels.toString().contains(msg.getChannel())) {
                connection.postMessageToChannel(authentication.getLoggedUser(), authentication.getPassword(), msg);
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | CertificateException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
