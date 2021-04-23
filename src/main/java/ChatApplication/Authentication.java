package ChatApplication;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authentication {

    private final HashMap<String, User> users;
    private String loggedUser;
    private String loggedPassword;
    private static Authentication singleton = null;

    public Authentication() {
        this.users = new HashMap<>();
        this.loggedUser = "";
    }

    public static synchronized Authentication getInstance() {
        // Create a singleton to only create one instance at a time
        if (singleton == null) {
            singleton = new Authentication();
        }
        return singleton;
    }

    public boolean authenticateUser(String username, String password) {
        ServerConnection connection = new ServerConnection("C:\\Users\\Eetu\\Documents\\NetBeansProjects\\Chat_application_with_back_end\\localhost.cer");
        try {
            if (connection.authenticate(username, password)) {
                System.out.println("onkotrue");
                setPassword(password);
                return true;
            }
            // Check if user exists and if password matches
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | javax.security.cert.CertificateException ex) {
            Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addUser(String username, String password, String email, String nickname) {
        ServerConnection connection = new ServerConnection("C:\\Users\\Eetu\\Documents\\NetBeansProjects\\Chat_application_with_back_end\\localhost.cer");
        try {
            if (connection.registerUser(nickname, username, password, email, email) == 200) {
                return true;
            }
        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | javax.security.cert.CertificateException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setLoggedUser(String name) {
        // Set user status to logged in
        this.loggedUser = name;
    }

    public String getLoggedUser() {
        // Get name of a user currently logged in
        return this.loggedUser;
    }

    public void setPassword(String password) {
        this.loggedPassword = password;
    }

    public String getPassword() {
        return this.loggedPassword;
    }
}
