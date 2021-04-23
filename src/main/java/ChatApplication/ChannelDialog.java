package ChatApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ListCellRenderer;

public class ChannelDialog {

    private JList list;
    private JLabel label;
    private JOptionPane optionPane;
    private JButton okButton, cancelButton;
    private ActionListener okEvent, cancelEvent;
    private JDialog dialog;

    public ChannelDialog(String message, JList listToDisplay) {
        list = listToDisplay;
        label = new JLabel(message);
        createAndDisplayOptionPane();
    }

    public ChannelDialog(String title, String message, JList listToDisplay) {
        this(message, listToDisplay);
        dialog.setTitle(title);
    }

    // Nested class for rendering channel list cells
    class ChannelListRenderer extends JLabel implements ListCellRenderer<Object> {

        public ChannelListRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            setFont(new java.awt.Font("Minion", 1, 13));

            // Set every other cell different shade
            if (index % 2 == 0) {
                setBackground(new java.awt.Color(255, 255, 255));
            } else {
                setBackground(new java.awt.Color(250, 250, 250));
            }

            // Set shade of cell lighter when cell is clicked
            if (isSelected) {
                setBackground(new java.awt.Color(210, 210, 210));
            }
            return this;
        }
    }

    private void createAndDisplayOptionPane() {
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{okButton, cancelButton});
        dialog = optionPane.createDialog("Kanavavalikko");
        dialog.setSize(300, 300);
    }

    private void setupButtons() {
        okButton = new JButton("Valmis");
        //okButton.setBackground(new java.awt.Color(60, 151, 51));
        okButton.setBackground(new java.awt.Color(79, 119, 240));
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(e -> handleOkButtonClick(e));

        cancelButton = new JButton("Peruuta");
        cancelButton.setBackground(new java.awt.Color(181, 60, 51));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> handleCancelButtonClick(e));
    }

    private JPanel layoutComponents() {
        JPanel panel = new JPanel(new BorderLayout(2, 5));
        label.setFont(new java.awt.Font("Dialog", 1, 16));
        panel.add(label, BorderLayout.NORTH);
        
        list.setFixedCellHeight(25);
        list.setCellRenderer(new ChannelListRenderer());
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(list);

        panel.add(scrollPane, BorderLayout.CENTER);
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
            // If user didn't select a channel, display error message
            if (list.getSelectedValue() == null) {
                UIManager.put("OptionPane.okButtonText", "OK");
                JOptionPane.showMessageDialog(null, "Et valinnut kanavaa. Valitse kanava.", "Kanavavirhe", JOptionPane.ERROR_MESSAGE);
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

    public Object getSelectedItem() {
        return list.getSelectedValue();
    }
}
