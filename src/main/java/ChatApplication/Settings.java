package ChatApplication;

import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public class Settings extends JFrame {

    private JFrame settingsFrame = new JFrame("Asetukset");
    private JPanel settingsPanel = new JPanel();

    public Settings() {
        initComponents();
        centeredFrame(settingsFrame);
    }

    private void centeredFrame(javax.swing.JFrame jFrame) {
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - jFrame.getWidth()) / 2;
        int iCoordY = (objDimension.height - jFrame.getHeight()) / 2;
        jFrame.setLocation(iCoordX, iCoordY);
    }

    private void initComponents() {
        Authentication authentication = Authentication.getInstance();

        settingsFrame.setSize(1000, 800);
        settingsFrame.setResizable(false);
        settingsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        settingsFrame.add(settingsPanel);
        settingsPanel.setLayout(null);
        settingsPanel.setBackground(new java.awt.Color(60, 63, 65));
        Color textColor = new java.awt.Color(187, 187, 187);

        // Components for settings window
        JLabel titleLabel = new JLabel("Asetukset");
        titleLabel.setBounds(350, 20, 400, 100);
        titleLabel.setForeground(textColor);
        titleLabel.setFont(new java.awt.Font("Dialog", 1, 44));

        JLabel usernameLabel = new JLabel("Käyttäjänimi");
        usernameLabel.setBounds(250, 190, 200, 25);
        usernameLabel.setForeground(textColor);
        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel userLoggedInLabel = new JLabel("Käyttäjä");
        userLoggedInLabel.setBounds(440, 190, 100, 25);
        userLoggedInLabel.setForeground(textColor);
        userLoggedInLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel passwordLabel = new JLabel("Salasana");
        passwordLabel.setBounds(250, 280, 100, 25);
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel passwordUserLabel = new JLabel("******");
        passwordUserLabel.setBounds(440, 280, 100, 25);
        passwordUserLabel.setForeground(textColor);
        passwordUserLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel emailLabel = new JLabel("Sähköpostiosoite");
        emailLabel.setBounds(250, 360, 400, 25);
        emailLabel.setForeground(textColor);
        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel emailUserLabel = new JLabel("nikopeny@gmail.com");
        emailUserLabel.setBounds(440, 360, 400, 25);
        emailUserLabel.setForeground(textColor);
        emailUserLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JButton changePasswordButton = new JButton();
        changePasswordButton.setBorder(new RoundedButton(10));
        changePasswordButton.setBackground(new java.awt.Color(79, 119, 240));
        changePasswordButton.setForeground(textColor);
        changePasswordButton.setFont(new java.awt.Font("Segoe UI", 1, 10));
        changePasswordButton.setText("Vaihda salasana");
        changePasswordButton.setBounds(700, 280, 150, 25);
        changePasswordButton.setFocusable(false);

        JButton changeEmailButton = new JButton();
        changeEmailButton.setBorder(new RoundedButton(10));
        changeEmailButton.setBackground(new java.awt.Color(79, 119, 240));
        changeEmailButton.setForeground(textColor);
        changeEmailButton.setFont(new java.awt.Font("Segoe UI", 1, 10));
        changeEmailButton.setText("Vaihda sähköposti");
        changeEmailButton.setBounds(700, 360, 150, 25);
        changeEmailButton.setFocusable(false);

        JLabel nicknameInChatLabel = new JLabel("*Nimimerkki näkyy muille käyttäjille keskustelussa");
        nicknameInChatLabel.setBounds(250, 500, 400, 25);
        nicknameInChatLabel.setForeground(textColor);
        nicknameInChatLabel.setFont(new java.awt.Font("Comic Sans MS", 1, 14));

        JLabel nicknameLabel = new JLabel("Nimimerkki");
        nicknameLabel.setBounds(250, 460, 400, 25);
        nicknameLabel.setForeground(textColor);
        nicknameLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel userNicknameLabel = new JLabel("Lempinimi");
        userNicknameLabel.setBounds(440, 460, 100, 25);
        userNicknameLabel.setForeground(textColor);
        userNicknameLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JButton changeNicknameButton = new JButton();
        changeNicknameButton.setBorder(new RoundedButton(10));
        changeNicknameButton.setBackground(new java.awt.Color(79, 119, 240));
        changeNicknameButton.setForeground(textColor);
        changeNicknameButton.setFont(new java.awt.Font("Segoe UI", 1, 10));
        changeNicknameButton.setText("Vaihda nimimerkki");
        changeNicknameButton.setBounds(700, 460, 150, 25);
        changeNicknameButton.setFocusable(false);

        JButton logoutButton = new JButton();
        logoutButton.setBorder(new RoundedButton(10));
        logoutButton.setBackground(new java.awt.Color(158, 63, 65));
        logoutButton.setForeground(textColor);
        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        logoutButton.setText("Kirjaudu ulos");
        logoutButton.setBounds(700, 675, 200, 30);
        logoutButton.setFocusable(false);

        // Add components to JPanel
        settingsPanel.add(titleLabel);
        settingsPanel.add(usernameLabel);
        settingsPanel.add(userLoggedInLabel);
        settingsPanel.add(passwordLabel);

        settingsPanel.add(passwordUserLabel);
        settingsPanel.add(emailLabel);
        settingsPanel.add(emailUserLabel);
        settingsPanel.add(changePasswordButton);
        settingsPanel.add(changeEmailButton);
        settingsPanel.add(nicknameInChatLabel);
        settingsPanel.add(nicknameLabel);
        settingsPanel.add(userNicknameLabel);
        settingsPanel.add(changeNicknameButton);
        settingsPanel.add(logoutButton);

        // Adding functionality to buttons
        changePasswordButton.addActionListener((java.awt.event.ActionEvent evt) -> {

            System.out.println("Pop up ikkuna vaihtamaan salis ");

        });

        changeEmailButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String name = JOptionPane.showInputDialog(settingsFrame,
                        "Syötä uusi tieto", null);
            }
        });

        changeNicknameButton.addActionListener((java.awt.event.ActionEvent evt) -> {

            System.out.println("Pop up ikkuna vaihtamaan nikki ");

        });

        logoutButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            // Close all windows
            closeAllDialogs();
            // Open new login window
            Login login = new Login();
            login.setVisible(true);
            // Set logged user to empty string when user logs out
            authentication.setLoggedUser("");
        });
    }

    private void closeAllDialogs() {
        Window[] windows = getWindows();

        for (Window window : windows) {
            if (window instanceof JFrame) {
                window.dispose();
            }
        }
    }

    // Set visiblity of settings window
    @Override
    public void setVisible(boolean visible) {
        settingsFrame.setVisible(visible);
    }
}
