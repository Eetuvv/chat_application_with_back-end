package ChatApplication;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.cert.CertificateException;

public class ChatChannel {

    private ArrayList<ChatMessage> messages;
    HashMap<String, ChatChannel> channels;
    String currentChannel;
    private ServerConnection connection = new ServerConnection("C:\\Users\\Eetu\\Documents\\NetBeansProjects\\Chat_application_with_back_end\\localhost.cer");

    public ChatChannel() {
        this.channels = new HashMap<>();
        this.messages = new ArrayList<>();
        this.currentChannel = "Kanava";
    }

    /* public void addDefaultChannels() {
        // Add some example channels with different sports
        this.channels.put("Yleinen", new ChatChannel());
        addMessageToChannel("Yleinen", new ChatMessage("Täällä on yleistä keskustelua.", "01.01.2021 00:00:00"));
        this.channels.put("Jalkapallo", new ChatChannel());
        addMessageToChannel("Jalkapallo", new ChatMessage("Täällä keskustellaan jalkapallosta.", "01.01.2021 00:00:00"));

        this.channels.put("Jääkiekko", new ChatChannel());
        addMessageToChannel("Jääkiekko", new ChatMessage("Täällä keskustellaan jääkiekosta.", "01.01.2021 00:00:00"));

        this.channels.put("Tennis", new ChatChannel());
        addMessageToChannel("Tennis", new ChatMessage("Täällä keskustellaan tenniksestä.", "01.01.2021 00:00:00"));

        this.channels.put("Hiihto", new ChatChannel());
        addMessageToChannel("Hiihto", new ChatMessage("Täällä keskustellaan hiihdosta.", "01.01.2021 00:00:00"));

        this.channels.put("Jääpallo", new ChatChannel());
        addMessageToChannel("Jääpallo", new ChatMessage("Täällä keskustellaan jääpallosta.", "01.01.2021 00:00:00"));

        this.channels.put("Yleisurheilu", new ChatChannel());
        addMessageToChannel("Yleisurheilu", new ChatMessage("Täällä keskustellaan yleisurheilusta.", "01.01.2021 00:00:00"));

        this.channels.put("Salibandy", new ChatChannel());
        addMessageToChannel("Salibandy", new ChatMessage("Täällä keskustellaan salibandysta.", "01.01.2021 00:00:00"));

        this.channels.put("Koripallo", new ChatChannel());
        addMessageToChannel("Koripallo", new ChatMessage("Täällä keskustellaan koripallosta.", "01.01.2021 00:00:00"));
    }
     */
 /*public void addChannel(String channel) {
        this.channels.put(channel, new ChatChannel());
    }
     */
    public ArrayList listChannels() {
        Authentication authentication = Authentication.getInstance();
        ArrayList<String> channelList = new ArrayList<>();

        try {
            return connection.listChannels(authentication.getLoggedUser(), authentication.getPassword());
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | java.security.cert.CertificateException ex) {
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
                return connection.getMessagesFromChannel(channel);
            } else {
                System.out.println("getMessagesFromChannel issue");
            }
        } catch (IOException | java.security.cert.CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | CertificateException ex) {
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
                connection.postMessageToChannel(authentication.getLoggedUser(), authentication.getPassword(), msg);
            } else {
                System.out.println("eitoimi");
            }
        } catch (IOException | java.security.cert.CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | CertificateException ex) {
            Logger.getLogger(ChatChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
