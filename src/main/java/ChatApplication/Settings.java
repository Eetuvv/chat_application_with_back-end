package ChatApplication;

import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;

public class Settings extends Chat {

    private final JFrame settingsFrame = new JFrame("Asetukset");
    private final JPanel settingsPanel = new JPanel();

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
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setAlwaysOnTop(true);

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
        usernameLabel.setBounds(200, 190, 200, 25);
        usernameLabel.setForeground(textColor);
        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel userLoggedInLabel = new JLabel(authentication.getLoggedUser());
        userLoggedInLabel.setBounds(390, 190, 250, 25);
        userLoggedInLabel.setForeground(textColor);
        userLoggedInLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel passwordLabel = new JLabel("Salasana");
        passwordLabel.setBounds(200, 280, 100, 25);
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel passwordUserLabel = new JLabel("******");
        passwordUserLabel.setBounds(390, 280, 100, 25);
        passwordUserLabel.setForeground(textColor);
        passwordUserLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel emailLabel = new JLabel("Sähköpostiosoite");
        emailLabel.setBounds(200, 360, 400, 25);
        emailLabel.setForeground(textColor);
        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel emailUserLabel = new JLabel(authentication.getLoggedEmail());
        emailUserLabel.setBounds(390, 360, 250, 25);
        emailUserLabel.setForeground(textColor);
        emailUserLabel.setFont(new java.awt.Font("Segoe UI", 1, 15));

        JButton changePasswordButton = new JButton();
        changePasswordButton.setBorder(new RoundedButton(10));
        changePasswordButton.setBackground(new java.awt.Color(79, 119, 240));
        changePasswordButton.setForeground(textColor);
        changePasswordButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        changePasswordButton.setText("Vaihda salasana");
        changePasswordButton.setBounds(635, 280, 150, 40);
        changePasswordButton.setFocusable(false);

        JButton changeEmailButton = new JButton();
        changeEmailButton.setBorder(new RoundedButton(10));
        changeEmailButton.setBackground(new java.awt.Color(79, 119, 240));
        changeEmailButton.setForeground(textColor);
        changeEmailButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        changeEmailButton.setText("Vaihda sähköposti");
        changeEmailButton.setBounds(635, 360, 150, 40);
        changeEmailButton.setFocusable(false);

        JLabel nicknameInChatLabel = new JLabel("Nimimerkki näkyy muille käyttäjille keskustelussa");
        nicknameInChatLabel.setBounds(200, 500, 400, 25);
        nicknameInChatLabel.setForeground(textColor);
        nicknameInChatLabel.setFont(new java.awt.Font("Segoe UI", Font.ITALIC, 14));

        JLabel nicknameLabel = new JLabel("Nimimerkki");
        nicknameLabel.setBounds(200, 460, 400, 25);
        nicknameLabel.setForeground(textColor);
        nicknameLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JLabel userNicknameLabel = new JLabel(authentication.getLoggedNick());
        userNicknameLabel.setBounds(390, 460, 250, 25);
        userNicknameLabel.setForeground(textColor);
        userNicknameLabel.setFont(new java.awt.Font("Segoe UI", 1, 20));

        JButton changeNicknameButton = new JButton();
        changeNicknameButton.setBorder(new RoundedButton(10));
        changeNicknameButton.setBackground(new java.awt.Color(79, 119, 240));
        changeNicknameButton.setForeground(textColor);
        changeNicknameButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        changeNicknameButton.setText("Vaihda nimimerkki");
        changeNicknameButton.setBounds(635, 460, 150, 40);
        changeNicknameButton.setFocusable(false);

        JButton logoutButton = new JButton();
        logoutButton.setBorder(new RoundedButton(10));
        logoutButton.setBackground(new java.awt.Color(158, 63, 65));
        logoutButton.setForeground(textColor);
        logoutButton.setFont(new java.awt.Font("Segoe UI", 1, 14));
        logoutButton.setText("Kirjaudu ulos");
        logoutButton.setBounds(620, 675, 180, 55);
        logoutButton.setFocusable(false);
        logoutButton.setToolTipText("Kirjaudu ulos ja palaa kirjautumisnäkymään");

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(143, 250, 700, 1);
        separator1.setForeground(Color.black);

        JSeparator separator2 = new JSeparator();
        separator2.setBounds(143, 339, 700, 1);
        separator2.setForeground(Color.black);

        JSeparator separator3 = new JSeparator();
        separator3.setBounds(143, 427, 700, 1);
        separator3.setForeground(Color.black);

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
        settingsPanel.add(separator1);
        settingsPanel.add(separator2);
        settingsPanel.add(separator3);

        // Adding functionality to buttons
        changePasswordButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            final JLabel selected = new JLabel("");
            PasswordDialog dialog = new PasswordDialog("Salasanan vaihtaminen");
            dialog.setOnOk(event -> {
                if (dialog.getText() != null) {
                    selected.setText(dialog.getText());
                }
            });
            dialog.show();

            String password = selected.getText();

            if (!password.isEmpty()) { // Check that password string is not empty
                // Change password if string is not empty
                authentication.editPassword(password);
            }
        });

        changeEmailButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            final JLabel selected = new JLabel("");
            CustomDialog dialog = new CustomDialog("Sähköpostin vaihtaminen", "Syötä uusi sähköposti");
            dialog.setOnOk(event -> {
                if (dialog.getText() != null) {
                    selected.setText(dialog.getText());
                }
            });
            dialog.show();

            String email = selected.getText();

            if (!email.isEmpty()) {
                String username = authentication.getLoggedUser();
                authentication.editUserDetails(username, username, email);
                authentication.setLoggedEmail(email);
                emailUserLabel.setText(email);
            }
        });

        changeNicknameButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            final JLabel selected = new JLabel("");
            CustomDialog dialog = new CustomDialog("Lempinimen vaihtaminen", "Syötä uusi lempinimi");
            dialog.setOnOk(event -> {
                if (dialog.getText() != null) {
                    selected.setText(dialog.getText());
                }
            });
            dialog.show();

            String nickname = selected.getText();

            if (!nickname.isEmpty()) {
                authentication.setLoggedNick(nickname);
                userNicknameLabel.setText(nickname);
                super.setNicknameLabel(nickname);
            }
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

        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new java.awt.Color(168, 73, 75));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new java.awt.Color(158, 63, 65));
            }
        });

        changeEmailButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                changeEmailButton.setBackground(new java.awt.Color(89, 129, 250));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                changeEmailButton.setBackground(new java.awt.Color(79, 119, 240));
            }
        });

        changeNicknameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                changeNicknameButton.setBackground(new java.awt.Color(89, 129, 250));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                changeNicknameButton.setBackground(new java.awt.Color(79, 119, 240));
            }
        });

        changePasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                changePasswordButton.setBackground(new java.awt.Color(89, 129, 250));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                changePasswordButton.setBackground(new java.awt.Color(79, 119, 240));
            }
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