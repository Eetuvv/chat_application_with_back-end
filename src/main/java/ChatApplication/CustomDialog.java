package ChatApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomDialog {

    private JTextField textField;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;
    private final String title;
    private final JLabel label;
    
    public CustomDialog(String title, String message) {
        this.title = title;
        this.label = new JLabel(message);
        
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createHorizontalGlue());

        textField = new JTextField();
        label.setFont(new java.awt.Font("Dialog", 1, 14));
        
        panel.add(label);
        panel.add(textField);
        
        return panel;
    }

    public void setOnOk(ActionListener event) {
        okEvent = event;
    }

    public void setOnClose(ActionListener event) {
        cancelEvent = event;
    }

    private void handleOkButtonClick(ActionEvent e) {
        if (okEvent != null) {
            okEvent.actionPerformed(e);
        }
        hide();
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
        return textField.getText();
    }
}
