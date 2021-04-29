package ChatApplication;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatChannel {

    private ArrayList<ChatMessage> messages;
    HashMap<String, ChatChannel> channels;
    String currentChannel;
    private final ServerConnection connection = new ServerConnection("C:\\Users\\Eetu\\Documents\\NetBeansProjects\\Chat_application_with_back_end\\localhost.cer");

    public ChatChannel() {
        this.channels = new HashMap<>();
        this.messages = new ArrayList<>();
        this.currentChannel = "Kanava";
    }

    public void addChannel(String channel) {
        Authentication authentication = Authentication.getInstance();
        ChatMessage msg = new ChatMessage(channel, " ", " ", " ");
        try {
            connection.postMessageToChannel(authentication.getLoggedUser(), authentication.getPassword(), msg);
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList listChannels() {
        Authentication authentication = Authentication.getInstance();
        ArrayList<String> channelList = new ArrayList<>();

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
        // Todo add channel
        Authentication authentication = Authentication.getInstance();

        try {
            ArrayList<String> channels = connection.listChannels(authentication.getLoggedUser(), authentication.getPassword());
            if (channels.toString().contains(channel)) {
                if (connection.getMessagesFromChannel(channel) != null){
                    System.out.println("jee!");
                    System.out.println("Messages from channel " + channel + ":" + connection.getMessagesFromChannel(channel));
                    if (connection.getMessagesFromChannel(channel).isEmpty()) {
                        System.out.println("Its empty!!!");
                    }
                    return connection.getMessagesFromChannel(channel);
                }
            } else {
                System.out.println("getMessagesFromChannel issue");
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | CertificateException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean addMessageToChannel(ChatMessage msg) {
        Authentication authentication = Authentication.getInstance();

        try {
            ArrayList<String> channels = connection.listChannels(authentication.getLoggedUser(), authentication.getPassword());
            if (channels.toString().contains(msg.channel)) {
                System.out.println("chnl " + msg.channel);
                System.out.println("chnl2" + msg.channel);
                connection.postMessageToChannel(authentication.getLoggedUser(), authentication.getPassword(), msg);
            } else {
                System.out.println("eitoimi");
            }
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | CertificateException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
