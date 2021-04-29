package ChatApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class Chat extends JFrame {

    public final JFrame chatFrame = new JFrame("Chat");
    ChatChannel chatChannel = new ChatChannel();
    String currentChannel;
    private final DefaultListModel<ChatMessage> model = new DefaultListModel<>();
    JList<ChatMessage> chatArea = new JList<>(model);
    ArrayList<ChatMessage> messages;

    public Chat() {
        currentChannel = chatChannel.getCurrentChannel();
        initComponents();
        centeredFrame(chatFrame);
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

        // Add default channels and description message to each channel
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setSize(1350, 950);
        chatFrame.setResizable(false);
        chatFrame.setBackground(new java.awt.Color(60, 63, 65));

        // Create JPanel with gradient background
        JPanel chatPanel;
        chatPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    final int R = 104;
                    final int G = 106;
                    final int B = 116;
                    Paint p
                            = new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 0),
                                    getWidth(), getHeight(), new Color(R, G, B, 255), true);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };

        // Add JPanel to JFrame
        chatFrame.add(chatPanel);
        chatPanel.setLayout(null);
        chatPanel.setBackground(new java.awt.Color(60, 63, 65));

        Color textColor = new java.awt.Color(187, 187, 187);
        Color buttonColor = new java.awt.Color(60, 60, 60);

        // Create all components for chat window
        JLabel channelLabel = new JLabel("# " + currentChannel);
        channelLabel.setFont(new java.awt.Font("Dialog", 1, 32));
        channelLabel.setBounds(70, 30, 275, 50);
        channelLabel.setForeground(textColor);

        // Get messages from current channel and append them to chat area
        showMessages();

        chatArea.setBackground(new java.awt.Color(106, 111, 117));
        chatArea.setCellRenderer(new CellRenderer());
        chatArea.setFixedCellHeight(85);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(300, 2, 1035, 866);

        JTextField messageField = new JTextField();
        messageField.setBounds(300, 869, 932, 40);
        messageField.setFont(new java.awt.Font("Whitney", 1, 17));

        // Set placeholder text if message field is empty
        if (messageField.getText().length() == 0) {
            messageField.setText("Lähetä viesti kanavalle #" + currentChannel);
            messageField.setForeground(new Color(190, 190, 190));
        }

        JButton sendMessageButton = new JButton("Lähetä");
        sendMessageButton.setBounds(1232, 869, 102, 40);
        sendMessageButton.setBackground(new java.awt.Color(62, 62, 68));
        sendMessageButton.setForeground(textColor);
        //sendMessageButton.setBorder(new RoundedButton(5));
        sendMessageButton.setFocusable(false);

        JButton chooseChannelButton = new JButton("Vaihda kanava");
        chooseChannelButton.setBounds(45, 125, 200, 65);
        chooseChannelButton.setBackground(buttonColor);
        chooseChannelButton.setForeground(textColor);
        chooseChannelButton.setBorder(new RoundedButton(15));
        chooseChannelButton.setToolTipText("Vaihda chat-kanava");
        chooseChannelButton.setFocusPainted(false);
        // Set icon to button
        chooseChannelButton.setIcon(new javax.swing.ImageIcon("icons/swap_channel_icon.png"));

        //chooseChannelButton.setHorizontalAlignment(SwingConstants.);
        chooseChannelButton.setHorizontalAlignment(SwingConstants.LEFT);
        chooseChannelButton.setIconTextGap(24);

        JButton createChannelButton = new JButton(" Luo kanava");
        createChannelButton.setBounds(45, 225, 200, 65);
        createChannelButton.setBackground(buttonColor);
        createChannelButton.setForeground(textColor);
        createChannelButton.setBorder(new RoundedButton(15));
        createChannelButton.setToolTipText("Luo uusi kanava haluamallesi aiheelle");
        createChannelButton.setFocusPainted(false);

        createChannelButton.setIcon(new javax.swing.ImageIcon("icons/plus_icon.png"));
        createChannelButton.setHorizontalAlignment(SwingConstants.LEFT);
        createChannelButton.setIconTextGap(12);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 775, 300, 1);
        separator.setForeground(new java.awt.Color(45, 45, 45));

        JLabel nicknameText = new JLabel("Nimimerkki");
        nicknameText.setFocusable(false);
        nicknameText.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 21));
        nicknameText.setForeground(textColor);
        nicknameText.setBounds(78, 650, 225, 175);
        nicknameText.setToolTipText("Käyttäjän nimimerkki");
        // Set text placement based on text length
        if (!authentication.getLoggedUser().isEmpty()) {
            if (authentication.getLoggedUser().length() < 10) {
                nicknameText.setBounds(122, 650, 225, 175);
            } else if (authentication.getLoggedUser().length() > 10
                    && authentication.getLoggedUser().length() <= 15) {
                nicknameText.setBounds(78, 650, 225, 175);
            } else if (authentication.getLoggedUser().length() > 15
                    && authentication.getLoggedUser().length() < 20) {
                nicknameText.setBounds(65, 650, 225, 175);
            }
            nicknameText.setText(authentication.getLoggedUser());
        }

        JButton openSettingsButton = new JButton("Asetukset");
        openSettingsButton.setBounds(55, 775, 180, 55);
        openSettingsButton.setBackground(buttonColor);
        openSettingsButton.setForeground(textColor);
        openSettingsButton.setBorder(new RoundedButton(15));
        openSettingsButton.setToolTipText("Avaa asetukset-valikko");
        openSettingsButton.setFocusPainted(false);
        // Set icon to settings button
        openSettingsButton.setIcon(new javax.swing.ImageIcon("icons/settings_icon.png"));
        openSettingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        openSettingsButton.setIconTextGap(20);

        JButton logoutButton = new JButton("Kirjaudu ulos");
        logoutButton.setBounds(55, 845, 180, 55);
        logoutButton.setBackground(new java.awt.Color(158, 63, 65));
        logoutButton.setForeground(textColor);
        logoutButton.setBorder(new RoundedButton(15));
        logoutButton.setToolTipText("Kirjaudu ulos ja palaa kirjautumisnäkymään");
        logoutButton.setIcon(new javax.swing.ImageIcon("icons/log_out_icon.png"));
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setIconTextGap(20);

        // Set tooltip text color and background
        UIManager.put("ToolTip.background", Color.white);
        UIManager.put("ToolTip.border", new LineBorder(Color.BLACK, 1));

        // Add components to JPanel
        chatPanel.add(scrollPane);
        chatPanel.add(messageField);
        chatPanel.add(sendMessageButton);
        chatPanel.add(channelLabel);
        chatPanel.add(chooseChannelButton);
        chatPanel.add(createChannelButton);
        chatPanel.add(openSettingsButton);
        chatPanel.add(nicknameText);
        chatPanel.add(logoutButton);

        // Set funcionality to buttons
        chooseChannelButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            // Store selected value in a final JLabel variable, since lambda function requires final variable
            final JLabel selected = new JLabel("");
            JList list = new JList(chatChannel.listChannels().toArray());
            ChannelDialog dialog = new ChannelDialog("Valitse kanava listalta ", list);
            // If user presses ok button, get the chosen channel name and pass it to selected label
            dialog.setOnOk(event -> {
                if (dialog.getSelectedItem() != null) {
                    selected.setText(dialog.getSelectedItem().toString());
                }
            });
            dialog.show();
            // If user chose ok button instead of cancel button, proceed
            if (!selected.getText().isEmpty()) {// empty if the user cancels. 
                // Set channel text to chosen channel
                String selectedString = selected.getText().substring(0, 1).toUpperCase() + selected.getText().substring(1);;

                // Set channel label font size depending on channel string length
                if (selectedString.length() >= 9 && selectedString.length() < 20) {
                    channelLabel.setFont(new java.awt.Font("Dialog", 1, 28));
                } else if (selectedString.length() >= 20) {
                    channelLabel.setFont(new java.awt.Font("Dialog", 1, 26));
                } else {
                    channelLabel.setFont(new java.awt.Font("Dialog", 1, 32));
                }

                channelLabel.setText("# " + selectedString);
                // Repaint frame to not mess up gradient
                this.chatFrame.repaint();

                // Set current channel viarable in chat channel -class and global variable
                chatChannel.setCurrentChannel(selectedString);
                currentChannel = selectedString;

                // Clear chat area
                model.removeAllElements();
                // Get messages from current channel and append them to chat area
                showMessages();
            }
        });

        createChannelButton.addActionListener((ActionEvent evt) -> {
            UIManager.put("OptionPane.cancelButtonText", "Peruuta");
            UIManager.put("OptionPane.okButtonText", "Valmis");
            UIManager.put("OptionPane.yesButtonText", "Siirry");
            UIManager.put("OptionPane.noButtonText", "Peruuta");

            Object selected = JOptionPane.showInputDialog(null, "Syötä kanavan nimi", "Luo uusi kanava", JOptionPane.PLAIN_MESSAGE, null, null, null);

            if (selected != null) { // Null if user cancels
                if (!selected.toString().isEmpty()) { // Check that channel string is not empty
                    // Add new channel to channels if it doesn't yet exist
                    String channelString = selected.toString();

                    // Capitalize first letter
                    String capitalizedChannel = channelString.substring(0, 1).toUpperCase() + channelString.substring(1);;
                    ArrayList<String> channels = chatChannel.listChannels();

                    // If channel doesn't exist yet, add a new channel
                    if (!channels.stream().anyMatch(capitalizedChannel::equalsIgnoreCase)) {
                        chatChannel.addChannel(capitalizedChannel);
                        // Set new channel to current channel
                        currentChannel = capitalizedChannel;

                        if (capitalizedChannel.length() >= 9 && capitalizedChannel.length() < 20) {
                            channelLabel.setFont(new java.awt.Font("Dialog", 1, 28));
                        } else if (capitalizedChannel.length() >= 20) {
                            channelLabel.setFont(new java.awt.Font("Dialog", 1, 26));
                        } else {
                            channelLabel.setFont(new java.awt.Font("Dialog", 1, 32));
                        }
                        channelLabel.setText("# " + capitalizedChannel);
                        this.chatFrame.repaint();

                        // Clear chat area
                        model.removeAllElements();
                        // Get messages from current channel and append them to chat area

                        showMessages();
                    } else {
                        // Get channel name from channels list to make sure that capitalization is the same
                        for (int i = 0; i < channels.size(); i++) {
                            if (channels.get(i).equalsIgnoreCase(capitalizedChannel)) {
                                capitalizedChannel = channels.get(i);
                            }
                        }
                        int selection = JOptionPane.showConfirmDialog(null, "Kanava on jo olemassa, siirry kanavalle '" + capitalizedChannel + "'?", "Valitse toiminto...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        // If user chooses to switch channel, set current channel to new channel
                        if (selection == JOptionPane.YES_OPTION) {
                            chatChannel.setCurrentChannel(capitalizedChannel);
                            currentChannel = capitalizedChannel;
                            channelLabel.setText("# " + capitalizedChannel);
                            // Repaint frame to not mess up gradient
                            this.chatFrame.repaint();
                            // Clear chat area
                            model.removeAllElements();
                            // Get messages from current channel and append them to chat area
                            showMessages();
                        }
                    }
                }
            }
        });

        logoutButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            // Close chat window
            this.setVisible(false);
            this.dispose();
            // Open login window
            Login login = new Login();
            login.setVisible(true);
            // Set logged user to empty string when user logs out
            authentication.setLoggedUser("");
        });

        sendMessageButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            String message = messageField.getText();
            ZonedDateTime time = ZonedDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy HH:mm:ss");
            String timestamp = time.format(formatter);

            ChatMessage msg = new ChatMessage(currentChannel, authentication.getLoggedUser(), message, timestamp);

            Color colorComparison = new java.awt.Color(190, 190, 190);

            // Don't send a new message if message is empty or if message color equals placeholder color
            if (!message.isEmpty() && !messageField.getForeground().equals(colorComparison)) {
                model.addElement(msg);
                chatChannel.addMessageToChannel(msg);
                messageField.setText("");
            }
        });

        openSettingsButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            Settings settings = new Settings();
            settings.setVisible(true);
            // Set logged user to empty string when user logs out
            authentication.setLoggedUser("");
        });

        messageField.addKeyListener(new java.awt.event.KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // If user presses enter and messagefield has focus, send new message
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    // Check if message field has focus and message is not empty
                    if (messageField.hasFocus() && !messageField.getText().isEmpty()) {
                        String message = messageField.getText();
                        ZonedDateTime time = ZonedDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy HH:mm:ss");
                        String timestamp = time.format(formatter);

                        ChatMessage msg = new ChatMessage(currentChannel, authentication.getLoggedUser(), message, timestamp);
                        model.addElement(msg);
                        chatChannel.addMessageToChannel(msg);
                        messageField.setText("");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Set placeholder text to messagefield when it is not in focus
        messageField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                messageField.setText("");
                messageField.setForeground(new Color(50, 50, 50));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (messageField.getText().length() == 0) {
                    messageField.setText("Lähetä viesti kanavalle #" + currentChannel);
                    messageField.setForeground(new Color(190, 190, 190));
                }
            }
        });

        // Change message field text when channel label text is changed (when channel is changed)
        channelLabel.addPropertyChangeListener("text", (PropertyChangeEvent evt) -> {
            messageField.setText("Lähetä viesti kanavalle " + channelLabel.getText().replaceAll("\\s+", ""));

            // Trim string
            String channelStr = channelLabel.getText().replaceAll("\\s+", "");
            channelStr = channelStr.replace("#", "");
            currentChannel = channelStr.trim();

            messageField.setForeground(new Color(190, 190, 190));
            model.removeAllElements();
            // Get messages from current channel and append them to chat area
            showMessages();
        });

        // Set hover actions to buttons
        chooseChannelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chooseChannelButton.setBackground(new java.awt.Color(70, 70, 70));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chooseChannelButton.setBackground(new java.awt.Color(60, 60, 60));
            }
        });

        createChannelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                createChannelButton.setBackground(new java.awt.Color(70, 70, 70));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                createChannelButton.setBackground(new java.awt.Color(60, 60, 60));
            }
        });

        openSettingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                openSettingsButton.setBackground(new java.awt.Color(70, 70, 70));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                openSettingsButton.setBackground(new java.awt.Color(60, 60, 60));
            }
        });

        openSettingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                openSettingsButton.setBackground(new java.awt.Color(70, 70, 70));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                openSettingsButton.setBackground(new java.awt.Color(60, 60, 60));
            }
        });

        sendMessageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendMessageButton.setBackground(new java.awt.Color(72, 72, 81));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendMessageButton.setBackground(new java.awt.Color(62, 62, 71));
            }
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

        // Refresh messages every second
        int delay = 1000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                refreshMessages();
            }
        };
        new Timer(delay, taskPerformer).start();
    }

    // Get messages from current channel and display them in chat area
    private void showMessages() {
        this.messages = chatChannel.getMessagesFromChannel(currentChannel);
        if (messages != null && !messages.isEmpty()) {
            this.messages.forEach(msg -> {
                model.addElement(msg);
            });
        }
    }
    
    private void refreshMessages() {
        ArrayList<ChatMessage> msgs = chatChannel.getNewestMessages(currentChannel);
        
       if (msgs != null) {
           for (ChatMessage msg : msgs) {
               model.addElement(msg);
               this.messages.add(msg);
           }
       }
    }

    @Override
    public void setVisible(boolean visible) {
        chatFrame.setVisible(visible);
    }
}
