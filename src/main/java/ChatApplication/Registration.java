package ChatApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Registration extends JFrame {

    private final JFrame registrationFrame = new JFrame("Rekisteröityminen");
    private final JPanel registrationPanel = new JPanel();

    public Registration() {
        initComponents();
        centeredFrame(registrationFrame);
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

        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrationFrame.setSize(1000, 800);
        registrationFrame.setResizable(false);

        registrationFrame.add(registrationPanel);
        registrationPanel.setLayout(null);
        registrationPanel.setBackground(new java.awt.Color(60, 63, 65));
        Color textColor = new java.awt.Color(187, 187, 187);

        // Create all components for registration window
        JLabel titleLabel = new JLabel("Rekisteröityminen");
        titleLabel.setFont(new java.awt.Font("Dialog", 1, 35));
        titleLabel.setBounds(350, 20, 400, 100);
        titleLabel.setForeground(textColor);

        JLabel usernameLabel = new JLabel("Käyttäjänimi");
        usernameLabel.setBounds(350, 190, 100, 25);
        usernameLabel.setForeground(textColor);
        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));

        JTextField usernameField = new JTextField();
        usernameField.setBounds(350, 220, 300, 45);
        usernameField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        usernameField.setToolTipText("Syötä käyttäjänimesi tähän");

        JLabel passwordLabel = new JLabel("Salasana");
        passwordLabel.setBounds(350, 270, 100, 25);
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(350, 295, 300, 45);
        passwordField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        passwordField.setToolTipText("Syötä haluamasi salasana tähän");

        JLabel passwordLabel2 = new JLabel("Salasana uudelleen");
        passwordLabel2.setBounds(350, 340, 150, 25);
        passwordLabel2.setForeground(textColor);
        passwordLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));

        JPasswordField passwordField2 = new JPasswordField();
        passwordField2.setBounds(350, 370, 300, 45);
        passwordField2.setFont(new java.awt.Font("Segoe UI", 1, 18));
        passwordField2.setToolTipText("Syötä yllä kirjoittamasi salasana uudelleen");

        JLabel passwordCheck = new JLabel("Salasanat eivät täsmää");
        passwordCheck.setBounds(660, 345, 175, 25);
        passwordCheck.setFont(new java.awt.Font("Segoe UI", 1, 16));
        passwordCheck.setForeground(Color.RED);
        passwordCheck.setVisible(false);

        JLabel emailLabel = new JLabel("Sähköpostiosoite");
        emailLabel.setBounds(350, 425, 150, 25);
        emailLabel.setForeground(textColor);
        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));

        JTextField emailField = new JTextField();
        emailField.setBounds(350, 450, 300, 45);
        emailField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        emailField.setToolTipText("Syötä toimiva sähköpostiosoite");

        JButton registerButton = new JButton();
        registerButton.setBackground(new java.awt.Color(79, 119, 240));
        registerButton.setForeground(textColor);
        registerButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        registerButton.setText("Rekisteröidy");
        registerButton.setBounds(350, 515, 300, 40);

        JButton loginButton = new JButton();
        loginButton.setBackground(new java.awt.Color(79, 119, 240));
        loginButton.setForeground(textColor);
        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        loginButton.setText("Jo käyttäjä? Kirjaudu");
        loginButton.setBounds(350, 575, 300, 40);

        // Add components to JPanel
        registrationPanel.add(titleLabel);
        registrationPanel.add(usernameLabel);
        registrationPanel.add(usernameField);
        registrationPanel.add(passwordLabel);
        registrationPanel.add(passwordField);
        registrationPanel.add(passwordLabel2);
        registrationPanel.add(passwordField2);
        registrationPanel.add(emailLabel);
        registrationPanel.add(emailField);
        registrationPanel.add(passwordCheck);
        registrationPanel.add(loginButton);
        registrationPanel.add(registerButton);

        // Add functionality to register button
        registerButton.addActionListener((java.awt.event.ActionEvent evt) -> {

            String user = usernameField.getText();
            String password = passwordField.getText();
            String password2 = passwordField2.getText();
            String email = emailField.getText();
            String nickname = user;

            //public synchronized void registerUser(String nickname, String username, String password, String email, String role)
            
            UIManager.put("OptionPane.okButtonText", "OK");
            if (password.isEmpty() || user.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Täytä kaikki tiedot", "Kirjautumisvirhe", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(password2)) {
                passwordCheck.setVisible(true);
                passwordField.setBackground(new java.awt.Color(251, 93, 93));
                passwordField2.setBackground(new java.awt.Color(251, 93, 93));
                JOptionPane.showMessageDialog(null, "Salasanat eivät täsmää", "Kirjautumisvirhe", JOptionPane.ERROR_MESSAGE);
            } else {
                if (!authentication.addUser(user, password, email, nickname)) {
                    JOptionPane.showMessageDialog(null, "Käyttäjänimi on jo rekisteröity", "Kirjautumisvirhe", JOptionPane.ERROR_MESSAGE);
                    usernameField.setBackground(new java.awt.Color(251, 93, 93));
                } else {
                    JOptionPane.showMessageDialog(null, "Rekisteröityminen onnistui", "Rekisteröityminen", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                    this.dispose();
                    Login login = new Login();
                    login.setVisible(true);
                }
            }
        });

        // Add functionality to login button
        loginButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            setVisible(false);
            Login login = new Login();
            login.setVisible(true);
        });
    }

    // Set visiblity of registration window
    @Override
    public void setVisible(boolean visible) {
        registrationFrame.setVisible(visible);
    }
}
