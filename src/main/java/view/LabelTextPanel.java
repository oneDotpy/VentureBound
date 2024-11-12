package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {
    private final JLabel label;
    private final JTextField textField;

    LabelTextPanel(JLabel label, JTextField textField) {
        this.label = label;
        this.textField = textField;

        this.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align label and text field to the left
        this.add(label);
        this.add(textField);
    }

    public JLabel getLabel() {
        return label;
    }

    public JTextField getTextField() {
        return textField;
    }
}
