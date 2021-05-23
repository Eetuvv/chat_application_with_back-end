package ChatApplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerConnection {

    private final Authentication authentication;
    private static ServerConnection singleton = null;
    private String certificate;
    private String lastModified = null;

    public ServerConnection() {
        authentication = Authentication.getInstance();
        //this.certificate = "C:\\Users\\Eetu\\Documents\\NetBeansProjects\\Chat_application_with_back_end\\localhost.cer";
        this.certificate = "";
        System.setProperty("http.keepAlive", "false");
    }

    public static synchronized ServerConnection getInstance() {
        // Create a singleton to only create one instance at a time
        if (singleton == null) {
            singleton = new ServerConnection();
        }
        return singleton;
    }

    private void setSertificate(String certificate) {
        this.certificate = certificate;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            ServerConnection connection = ServerConnection.getInstance();
            connection.setSertificate(args[0]);
            Login login = new Login();
            login.setVisible(true);
        });
    }

    private HttpsURLConnection createHTTPSConnection(URL url) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(this.certificate));
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("localhost", certificate);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection.setReadTimeout(3 * 1000);

        return connection;
    }

    public synchronized int registerUser(String nickname, String username, String password, String email, String role) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL("https://localhost:8001/registration");
        int responseCode = 200;

        try {
            HttpsURLConnection connection = createHTTPSConnection(url);

            //  Set connection properties
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(3 * 1000);
            connection.setUseCaches(false);

            // Body for request
            JSONObject json = new JSONObject();
            json.put("user", nickname);
            json.put("action", "");

            JSONObject userdetails = new JSONObject();
            userdetails.put("username", username);
            userdetails.put("password", password);
            userdetails.put("role", role);
            userdetails.put("email", email);

            json.put("userdetails", userdetails);

            // Write request body to outputstream
            OutputStream os = connection.getOutputStream();
            String payload = json.toString();

            os.write(payload.getBytes(StandardCharsets.UTF_8));

            os.flush();
            os.close();

            responseCode = connection.getResponseCode();

            // Catch if errors if response code is not 200
            try {
                InputStream stream = connection.getInputStream();
                String text = new BufferedReader(new InputStreamReader(stream,
                        StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                stream.close();
            } catch (IOException e) {
                responseCode = connection.getResponseCode();
                System.out.println("Error! Server responded with: " + responseCode + " " + connection.getResponseMessage());
            }

        } catch (ConnectException e) {
            responseCode = 400;
            System.out.println("Connection to server failed!");
        }
        // TODO Fix error message
        return responseCode;
    }

    public synchronized ArrayList listChannels(String username, String password) throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        ArrayList<String> channelList = new ArrayList<>();
        try {

            URL url = new URL("https://localhost:8001/chat/?listChannels");
            HttpsURLConnection connection = createHTTPSConnection(url);

            // Set basic authentication
            String userCredentials = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            //  Set connection properties
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            connection.setReadTimeout(3 * 1000);
            connection.setUseCaches(false);

            InputStream stream = connection.getInputStream();

            String text = new BufferedReader(new InputStreamReader(stream,
                    StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stream.close();

            List<String> myList = new ArrayList<String>(Arrays.asList(text.split(",")));

            for (int i = 0; i < myList.size(); i++) {
                channelList.add(myList.get(i).replace("[", "").replace("]", "").trim());
            }

        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            System.out.println("Error listing channels!");
        }
        return channelList;
    }

    public synchronized int authenticate(String username, String password) throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        int responseCode = 401;
        try {
            BufferedReader reader = null;
            String payload = "body";

            URL url = new URL("https://localhost:8001/chat/?listChannels");
            HttpsURLConnection connection = createHTTPSConnection(url);

            // Set basic authentication
            String userCredentials = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            //  Set connection properties
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            connection.setReadTimeout(3 * 1000);
            connection.setUseCaches(false);

            InputStream stream = connection.getInputStream();

            String text = new BufferedReader(new InputStreamReader(stream,
                    StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stream.close();

            responseCode = connection.getResponseCode();

        } catch (ConnectException e) {
            responseCode = 400;
            System.out.println(responseCode + ": Error connecting to server");
        } catch (FileNotFoundException e) {
            System.out.println("Certificate not found!");
        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            System.out.println(responseCode + ": Authentication error");
        }

        return responseCode;
    }

    public synchronized ArrayList getMessagesFromChannel(String channel) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        ArrayList<ChatMessage> messages = new ArrayList<>();
        try {
            URL url = new URL("https://localhost:8001/chat/?channel=" + channel);
            HttpsURLConnection connection = createHTTPSConnection(url);

            String username = authentication.getLoggedUser();
            String password = authentication.getPassword();

            // Set basic authentication
            String userCredentials = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            //  Set connection properties
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            connection.setReadTimeout(3 * 1000);
            connection.setUseCaches(false);

            InputStream stream = connection.getInputStream();

            String text = new BufferedReader(new InputStreamReader(stream,
                    StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stream.close();

            this.lastModified = connection.getHeaderField("Last-Modified");

            if (text.isEmpty()) {
                return null;
            }
            JSONArray jsonArray = new JSONArray(text);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                ZonedDateTime zd = ZonedDateTime.parse(object.getString("sent"));
                LocalDateTime date = zd.toLocalDateTime();
                String formattedDate = date.format(formatter);

                ChatMessage msg = new ChatMessage(channel, object.getString("user"), object.getString("message"), formattedDate);

                // If username field is blank don't show message (don't add it to message list) (implementing channel adding this way)
                if (msg.getUser().isBlank()) {
                    continue;
                }

                messages.add(msg);
            }
        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | JSONException e) {
            System.out.println("Error getting messages from channel");
        }

        return messages;
    }

    public synchronized void postMessageToChannel(String username, String password, ChatMessage chatMessage) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL("https://localhost:8001/chat/");
        HttpsURLConnection connection = createHTTPSConnection(url);

        String userCredentials = username + ":" + password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

        //  Set connection properties
        connection.setRequestProperty("Authorization", basicAuth);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setReadTimeout(3 * 1000);
        connection.setUseCaches(false);

        // Body for request
        JSONObject json = new JSONObject();
        json.put("user", chatMessage.getUser());
        json.put("message", chatMessage.getMessage());
        json.put("channel", chatMessage.getChannel());

        DateTimeFormatter jsonDateFormatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        String dateText = now.format(jsonDateFormatter);
        json.put("sent", dateText);

        // Write to outputstream
        OutputStream os = connection.getOutputStream();
        String payload = json.toString();

        os.write(payload.getBytes(StandardCharsets.UTF_8));

        os.flush();
        os.close();

        InputStream stream = connection.getInputStream();

        String text = new BufferedReader(new InputStreamReader(stream,
                StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        stream.close();
    }

    public synchronized ArrayList getMessagesSince(String channel) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        ArrayList<ChatMessage> messages = new ArrayList<>();
        try {
            URL url = new URL("https://localhost:8001/chat/?channel=" + channel);
            HttpsURLConnection connection = createHTTPSConnection(url);

            String username = authentication.getLoggedUser();
            String password = authentication.getPassword();

            // Set basic authentication
            String userCredentials = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            //  Set connection properties
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            if (lastModified != null) {
                connection.setRequestProperty("If-Modified-Since", lastModified);
            } else {
                return null;
            }
            connection.setReadTimeout(3 * 1000);
            connection.setUseCaches(false);

            InputStream stream = connection.getInputStream();

            String text = new BufferedReader(new InputStreamReader(stream,
                    StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stream.close();

            if (connection.getHeaderField("Last-Modified") != null) {
                lastModified = connection.getHeaderField("Last-Modified");
            }

            if (text.isEmpty()) {
                return null;
            }

            JSONArray jsonArray = new JSONArray(text);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                ZonedDateTime zd = ZonedDateTime.parse(object.getString("sent"));
                LocalDateTime date = zd.toLocalDateTime();
                String formattedDate = date.format(formatter);

                ChatMessage msg = new ChatMessage(channel, object.getString("user"), object.getString("message"), formattedDate);

                // If username field is blank don't show message (don't add it to message list) (implementing channel adding this way)
                if (msg.getUser().isBlank()) {
                    continue;
                }

                messages.add(msg);
            }
        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | JSONException e) {
            System.out.println("Error getting newest messages from channel");
        }

        return messages;
    }

    public synchronized void editUserDetails(String username, String updatedUsername, String updatedEmail) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL("https://localhost:8001/chat/");
        HttpsURLConnection connection = createHTTPSConnection(url);

        String password = authentication.getPassword();
        String userCredentials = username + ":" + password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

        //  Set connection properties
        connection.setRequestProperty("Authorization", basicAuth);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setReadTimeout(3 * 1000);
        connection.setUseCaches(false);

        // Body for request
        JSONObject json = new JSONObject();
        json.put("user", username);
        json.put("action", "editUser");
        json.put("role", "user");

        JSONObject userdetails = new JSONObject();
        userdetails.put("updatedUsername", updatedUsername);
        userdetails.put("updatedEmail", updatedEmail);
        userdetails.put("role", "user");
        
        json.put("userdetails", userdetails);

        // Write to outputstream
        OutputStream os = connection.getOutputStream();
        String payload = json.toString();

        os.write(payload.getBytes(StandardCharsets.UTF_8));

        os.flush();
        os.close();

        InputStream stream = connection.getInputStream();

        String text = new BufferedReader(new InputStreamReader(stream,
                StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        stream.close();
    }

    public synchronized void editUserPassword(String username, String updatedPassword) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL("https://localhost:8001/chat/");
        HttpsURLConnection connection = createHTTPSConnection(url);

        String password = authentication.getPassword();
        String userCredentials = username + ":" + password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

        //  Set connection properties
        connection.setRequestProperty("Authorization", basicAuth);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setReadTimeout(3 * 1000);
        connection.setUseCaches(false);

        // Body for request
        JSONObject json = new JSONObject();
        json.put("user", username);
        json.put("action", "editPassword");
        json.put("updatedPassword", updatedPassword);

        // Write to outputstream
        try {
            OutputStream os = connection.getOutputStream();
            String payload = json.toString();

            os.write(payload.getBytes(StandardCharsets.UTF_8));

            os.flush();
            os.close();

            InputStream stream = connection.getInputStream();

            String text = new BufferedReader(new InputStreamReader(stream,
                    StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stream.close();
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("Error editing user password");
        }
    }

    public synchronized ArrayList getUserDetails(String username, String password) throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        ArrayList<String> userDetails = new ArrayList<>();
        try {
            BufferedReader reader = null;

            URL url = new URL("https://localhost:8001/chat/");
            HttpsURLConnection connection = createHTTPSConnection(url);

            // Set basic authentication
            String userCredentials = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            //  Set connection properties
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setReadTimeout(3 * 1000);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            JSONObject json = new JSONObject();
            json.put("user", username);
            json.put("action", "getUserDetails");

            OutputStream os = connection.getOutputStream();
            String payload = json.toString();

            os.write(payload.getBytes(StandardCharsets.UTF_8));

            os.flush();
            os.close();

            InputStream stream = connection.getInputStream();

            String text = new BufferedReader(new InputStreamReader(stream,
                    StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stream.close();

            // Strip commas and square brackets from list items and add them to new arraylist
            List<String> myList = new ArrayList<>(Arrays.asList(text.split(",")));
            for (int i = 0; i < myList.size(); i++) {
                userDetails.add(myList.get(i).replace("[", "").replace("]", "").trim());
            }

        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            System.out.println("Error getting user details");
        }

        return userDetails;
    }
}
