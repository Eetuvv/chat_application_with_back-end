package ChatApplication;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CellRenderer extends JLabel implements ListCellRenderer<ChatMessage> {
    
    public CellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ChatMessage> list, ChatMessage msg, int index, boolean isSelected, boolean cellHasFocus) {
        setText("<html><p><i><FONT COLOR=WHITE SIZE=5>" + msg.user + "</FONT></i>&nbsp&nbsp<em><FONT COLOR=BLACK SIZE=4>" + msg.timestamp + "</FONT></em></p><br><p><FONT COLOR=WHITE>" + msg.message + "</FONT></p></html>");
        setFont(new java.awt.Font("Whitney", 1, 16));
        
        
        Color lightBackground = new java.awt.Color(112, 117, 123);
        Color lighterBackground = new java.awt.Color(122, 127, 133);
        
        Color darkBackground = new java.awt.Color(116, 121, 127);
        Color darkerBackground = new java.awt.Color(106, 111, 117);
        
        // Set every other cell different shade of gray
        if (index % 2 == 0) setBackground(darkerBackground);
        else setBackground(lightBackground);
        
        // Set shade of cell lighter when cell is clicked
        if (isSelected && index % 2 == 0) setBackground(darkBackground);
        else if (isSelected) setBackground(lighterBackground);
        return this;
    }
}
