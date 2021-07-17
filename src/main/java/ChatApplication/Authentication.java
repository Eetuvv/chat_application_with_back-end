package ChatApplication;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class Authentication {

    private String loggedUser;
    private String loggedPassword;
    private String loggedEmail;
    private String loggedNickname;
    private static Authentication singleton = null;

    public Authentication() {
        this.loggedUser = "";
        this.loggedEmail = "";
        this.loggedNickname = "";
        this.loggedPassword = "";
    }

    public static synchronized Authentication getInstance() {
        // Create a singleton to only create one instance at a time
        if (singleton == null) {
            singleton = new Authentication();
        }
        return singleton;
    }

    public int authenticateUser(String username, String password) {
        //ServerConnection connection = new ServerConnection("C:\\Users\\Eetu\\Documents\\NetBeansProjects\\Chat_application_with_back_end\\localhost.cer");
        ServerConnection connection = ServerConnection.getInstance();
        try {
            if (connection.authenticate(username, password) == 200) {
                setPassword(password);
                return 200;
            } else if (connection.authenticate(username, password) == 401) {
                return 401;
            }
            // Check if user exists and if password matches
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return 400;
    }

    public int addUser(String username, String password, String email, String nickname) {
        ServerConnection connection = ServerConnection.getInstance();
        try {
            if (connection.registerUser(nickname, username, password, email, "user") == 200) {
                return 200;
            } else if (connection.registerUser(nickname, username, password, email, "user") == 400) {
                return 400;
            }
        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            System.out.println("Error adding user");
        }
        return 403;
    }
    
    public void editPassword(String newPassword) {
        ServerConnection connection = ServerConnection.getInstance();
        try {
            connection.editUserPassword(loggedUser, newPassword);
            setPassword(newPassword);
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
    
    public void setUserDetails(String user, String password) {
        ServerConnection connection = ServerConnection.getInstance();
        try {
            ArrayList<String> details = connection.getUserDetails(user, password);
            setLoggedEmail(details.get(0));
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | CertificateException e) {
            e.printStackTrace();
        }
    }
    
    public void editUserDetails(String user, String updatedUsername, String updatedEmail) {
        ServerConnection connection = ServerConnection.getInstance();
        try {
            connection.editUserDetails(user, updatedUsername, updatedEmail);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException | CertificateException e) {
            e.printStackTrace();
        }
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
    

    public void setLoggedEmail(String newEmail) {
        this.loggedEmail = newEmail;
    }

    public String getLoggedEmail() {
        return this.loggedEmail;
    }

    public void setLoggedNick(String nick) {
        this.loggedNickname = nick;
    }

    public String getLoggedNick() {
        return this.loggedNickname;
    }
}
