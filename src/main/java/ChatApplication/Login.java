package ChatApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class Login extends JFrame {

    private final JFrame loginFrame = new JFrame("Kirjautuminen");
    private final JPanel loginPanel = new JPanel();
    //private ServerConnection connection = new ServerConnection("C:\\Users\\Eetu\\Documents\\NetBeansProjects\\Chat_application_with_back_end\\localhost.cer");
    private final ServerConnection connection = ServerConnection.getInstance();

    public Login() {
        initComponents();
        centeredFrame(loginFrame);
    }

    // Center window
    private void centeredFrame(javax.swing.JFrame jFrame) {
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - jFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - jFrame.getHeight()) / 2;
        jFrame.setLocation(iCoordX, iCoordY);
    }

    private void initComponents() {
        // Get an instance of authentication class
        Authentication authentication = Authentication.getInstance();

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(1000, 800);
        loginFrame.setResizable(false);

        loginFrame.add(loginPanel);
        loginPanel.setLayout(null);
        loginPanel.setBackground(new java.awt.Color(60, 63, 65));
        Color textColor = new java.awt.Color(187, 187, 187);

        // Create all components for login window
        JLabel titleLabel = new JLabel("Kirjautuminen");
        titleLabel.setFont(new java.awt.Font("Dialog", 1, 44));
        titleLabel.setBounds(350, 20, 300, 100);
        titleLabel.setForeground(textColor);

        JLabel usernameLabel = new JLabel("Käyttäjänimi");
        usernameLabel.setBounds(350, 190, 100, 25);
        usernameLabel.setForeground(textColor);
        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));

        JTextField usernameField = new JTextField();
        usernameField.setBounds(350, 220, 300, 45);
        usernameField.setToolTipText("Syötä käyttäjänimesi tähän");
        usernameField.setFont(new java.awt.Font("Segoe UI", 1, 18));

        JLabel passwordLabel = new JLabel("Salasana");
        passwordLabel.setBounds(350, 265, 100, 25);
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(350, 290, 300, 45);
        passwordField.setToolTipText("Syötä salasanasi tähän");
        passwordField.setFont(new java.awt.Font("Segoe UI", 1, 18));

        JCheckBox passwordCheckBox = new JCheckBox("Näytä salasana");
        passwordCheckBox.setBounds(346, 340, 150, 20);
        passwordCheckBox.setBackground(new java.awt.Color(60, 63, 65));
        passwordCheckBox.setForeground(textColor);

        JButton loginButton = new JButton();
        loginButton.setBackground(new java.awt.Color(79, 119, 240));
        loginButton.setForeground(textColor);
        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        loginButton.setText("Kirjaudu");
        loginButton.setBounds(350, 380, 300, 40);

        JButton registerButton = new JButton();
        registerButton.setBackground(new java.awt.Color(79, 119, 240));
        registerButton.setForeground(textColor);
        registerButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        registerButton.setText("Uusi käyttäjä? Rekisteröidy");
        registerButton.setBounds(350, 440, 300, 40);

        // Set tooltip text color and background
        UIManager.put("ToolTip.background", Color.white);
        UIManager.put("ToolTip.border", new LineBorder(Color.BLACK, 1));

        // Add components to JPanel
        loginPanel.add(titleLabel);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(passwordCheckBox);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);
        
        loginFrame.getRootPane().setDefaultButton(loginButton);

        // Set login button functionality
        loginButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            String user = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            UIManager.put("OptionPane.okButtonText", "OK");
            Color redBackground = new java.awt.Color(251, 93, 93);

            if (user.isEmpty()) {
                usernameField.setBackground(redBackground);
            }

            if (password.isEmpty()) {
                passwordField.setBackground(redBackground);
            }

            if (password.isEmpty() || user.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Käyttäjätunnus tai salasana ei saa olla tyhjä", "Kirjautumisvirhe", JOptionPane.ERROR_MESSAGE);
            } else {
                // Check if username and password are correct
                int response = authentication.authenticateUser(user, password);

                if (response == 200) {
                    authentication.setLoggedUser(user);
                    authentication.setUserDetails(user, password);
                    // TODO add nick
                    authentication.setLoggedNick(user);
                    
                    // Close login window and open chat window
                    this.setVisible(false);
                    this.dispose();
                    Chat chatWindow = Chat.getInstance();
                    chatWindow.setVisible(true);
                } else if (response == 401) { // 401 if username or password incorrect
                    JOptionPane.showMessageDialog(null, "Väärä käyttäjätunnus tai salasana", "Kirjautumisvirhe", JOptionPane.ERROR_MESSAGE);
                } else { // Show a connection error if response code is something else
                    JOptionPane.showMessageDialog(null, "Palvelimeen ei saatu yhteyttä", "Yhteysvirhe", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set register button functionality
        registerButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            setVisible(false);
            Registration registration = new Registration();
            registration.setVisible(true);
        });

        // Make password visible when checkbox is clicked
        passwordCheckBox.addActionListener((ActionEvent e) -> {
            if (passwordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        usernameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                Color redBackground = new java.awt.Color(251, 93, 93);
                if (!usernameField.getText().isEmpty() && usernameField.getBackground().equals(redBackground)) {
                    usernameField.setBackground(Color.WHITE);
                } else if (usernameField.getText().isEmpty()) {
                    usernameField.setBackground(redBackground);
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                Color redBackground = new java.awt.Color(251, 93, 93);
                if (!String.valueOf(passwordField.getPassword()).isEmpty() && passwordField.getBackground().equals(redBackground)) {
                    passwordField.setBackground(Color.WHITE);
                } else if (usernameField.getText().isEmpty()) {
                    passwordField.setBackground(redBackground);
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });

        loginButton.addKeyListener(new java.awt.event.KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Activate login button when enter key is pressed
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Set register button functionality
        registerButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            setVisible(false);
            Registration registration = new Registration();
            registration.setVisible(true);
        });

        // Make password visible when checkbox is clicked
        passwordCheckBox.addActionListener((ActionEvent e) -> {
            if (passwordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });
    }

// Set visibility of login window
    @Override
    public void setVisible(boolean visible) {
        loginFrame.setVisible(visible);
    }
}
