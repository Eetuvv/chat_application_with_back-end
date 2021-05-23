package ChatApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordDialog {

    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField passwordField2 = new JPasswordField();
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;
    private final String title;

    public PasswordDialog(String title) {
        this.title = title;

        createAndDisplayOptionPane();
    }

    private void createAndDisplayOptionPane() {
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{okButton, cancelButton});

        dialog = new JDialog();
        dialog.add(optionPane);
        dialog = optionPane.createDialog(title);
        dialog.pack();
    }

    private void setupButtons() {
        okButton = new JButton("Valmis");
        okButton.setBackground(new java.awt.Color(79, 119, 240));
        okButton.addActionListener(e -> handleOkButtonClick(e));

        cancelButton = new JButton("Peruuta");
        cancelButton.setBackground(new java.awt.Color(181, 60, 51));
        cancelButton.addActionListener(e -> handleCancelButtonClick(e));
    }

    private JPanel layoutComponents() {
        JPanel panel = new JPanel();
        JLabel oldPwordlabel = new JLabel("Anna nykyinen salasana");
        JLabel newPwordlabel = new JLabel("Anna uusi salasana");

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createHorizontalGlue());

        oldPwordlabel.setFont(new java.awt.Font("Dialog", 1, 14));
        newPwordlabel.setFont(new java.awt.Font("Dialog", 1, 14));
        panel.add(oldPwordlabel);
        panel.add(passwordField);
        panel.add(newPwordlabel);
        panel.add(passwordField2);

        return panel;
    }

    public void setOnOk(ActionListener event) {
        okEvent = event;
    }

    public void setOnClose(ActionListener event) {
        cancelEvent = event;
    }

    private void handleOkButtonClick(ActionEvent e) {
        Authentication authentication = Authentication.getInstance();
        if (okEvent != null) {
            okEvent.actionPerformed(e);
            String currentPassword = String.valueOf(passwordField.getPassword());
            String newPassword = String.valueOf(passwordField2.getPassword());
            if (currentPassword.isEmpty() || newPassword.isEmpty()) {
                UIManager.put("OptionPane.okButtonText", "OK");
                JOptionPane.showMessageDialog(null, "Täytä molemmat kentät!", "Virhe", JOptionPane.ERROR_MESSAGE);
            } else if (authentication.authenticateUser(authentication.getLoggedUser(), currentPassword) != 200) {
                UIManager.put("OptionPane.okButtonText", "OK");
                System.out.println("logged user: " + authentication.getLoggedUser());
                JOptionPane.showMessageDialog(null, "Nykyinen salasana on väärä!", "Salasanavirhe",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                hide();
            }
        }
    }

    private void handleCancelButtonClick(ActionEvent e) {
        if (cancelEvent != null) {
            cancelEvent.actionPerformed(e);
        }
        hide();
    }

    public void show() {
        dialog.setVisible(true);
    }

    private void hide() {
        dialog.setVisible(false);
    }

    public String getText() {
        return String.valueOf(passwordField2.getPassword());
    }
}
