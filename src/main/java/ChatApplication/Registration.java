package ChatApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField passwordField2 = new JPasswordField();
    private final JLabel passwordCheck = new JLabel("Salasanat eivät täsmää");
    private final JTextField emailField = new JTextField();
    private final JButton registerButton = new JButton();
    private final JButton loginButton = new JButton();
    private final Authentication authentication = Authentication.getInstance();

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

        usernameField.setBounds(350, 220, 300, 45);
        usernameField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        usernameField.setToolTipText("Syötä käyttäjänimesi tähän");

        JLabel passwordLabel = new JLabel("Salasana");
        passwordLabel.setBounds(350, 270, 100, 25);
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));

        passwordField.setBounds(350, 295, 300, 45);
        passwordField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        passwordField.setToolTipText("Syötä haluamasi salasana tähän");

        JLabel passwordLabel2 = new JLabel("Salasana uudelleen");
        passwordLabel2.setBounds(350, 340, 150, 25);
        passwordLabel2.setForeground(textColor);
        passwordLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));

        passwordField2.setBounds(350, 370, 300, 45);
        passwordField2.setFont(new java.awt.Font("Segoe UI", 1, 18));
        passwordField2.setToolTipText("Syötä yllä kirjoittamasi salasana uudelleen");

        passwordCheck.setBounds(660, 345, 175, 25);
        passwordCheck.setFont(new java.awt.Font("Segoe UI", 1, 16));
        passwordCheck.setForeground(Color.RED);
        passwordCheck.setVisible(false);

        JLabel emailLabel = new JLabel("Sähköpostiosoite");
        emailLabel.setBounds(350, 425, 150, 25);
        emailLabel.setForeground(textColor);
        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));

        emailField.setBounds(350, 450, 300, 45);
        emailField.setFont(new java.awt.Font("Segoe UI", 1, 18));
        emailField.setToolTipText("Syötä toimiva sähköpostiosoite");

        registerButton.setBackground(new java.awt.Color(79, 119, 240));
        registerButton.setForeground(textColor);
        registerButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        registerButton.setText("Rekisteröidy");
        registerButton.setBounds(350, 515, 300, 40);

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
        
        registrationFrame.getRootPane().setDefaultButton(registerButton);

        // Add functionality to register button
        registerButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            registerUser();
        });

        // Add functionality to login button
        loginButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            setVisible(false);
            registrationFrame.dispose();
            Login login = new Login();
            login.setVisible(true);
        });

        // Add listeners to text fields and password fields to change background to red if field is empty
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
                    passwordsMatch();
                } else if (String.valueOf(passwordField2.getPassword()).isEmpty()) {
                    passwordField.setBackground(redBackground);
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });

        passwordField2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                Color redBackground = new java.awt.Color(251, 93, 93);
                if (!String.valueOf(passwordField2.getPassword()).isEmpty()) {
                    passwordsMatch();
                } else if (String.valueOf(passwordField2.getPassword()).isEmpty()) {
                    passwordField2.setBackground(redBackground);
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });

        emailField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                Color redBackground = new java.awt.Color(251, 93, 93);
                if (!emailField.getText().isEmpty()) {
                    emailField.setBackground(Color.WHITE);
                } else if (emailField.getText().isEmpty()) {
                    emailField.setBackground(redBackground);
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });

    }

    private void registerUser() {
        String user = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String password2 = String.valueOf(passwordField2.getPassword());
        String email = emailField.getText();
        String nickname = user;

        UIManager.put("OptionPane.okButtonText", "OK");
        Color redBackground = new java.awt.Color(251, 93, 93);

        // Bunch of if-statements to check if text fields are empty
        if (password.isEmpty()) {
            passwordField.setBackground(redBackground);
        } else {
            passwordField.setBackground(Color.WHITE);
        }

        if (user.isEmpty()) {
            usernameField.setBackground(redBackground);
        } else {
            usernameField.setBackground(Color.WHITE);
        }

        if (email.isEmpty()) {
            emailField.setBackground(redBackground);
        } else {
            emailField.setBackground(Color.WHITE);
        }

        if (password2.isEmpty()) {
            passwordField2.setBackground(redBackground);
        } else {
            passwordField2.setBackground(Color.WHITE);
        }

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
    }

    private void passwordsMatch() {
        // Set password field backgrounds to red or white depending on if the passwords match or not
        Color redBackground = new java.awt.Color(251, 93, 93);
        if (String.valueOf(passwordField2.getPassword()).equals(String.valueOf(passwordField.getPassword()))) {
            passwordField2.setBackground(Color.WHITE);
            passwordField.setBackground(Color.WHITE);
            passwordCheck.setVisible(false);
        } else if (!String.valueOf(passwordField2.getPassword()).equals(String.valueOf(passwordField.getPassword()))) {
            passwordField.setBackground(redBackground);
            passwordField2.setBackground(redBackground);
            passwordCheck.setVisible(true);
        }
    }

    // Set visiblity of registration window
    @Override
    public void setVisible(boolean visible) {
        this.registrationFrame.setVisible(visible);
    }
}
