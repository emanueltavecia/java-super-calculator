package ui.components;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class CalculatorField extends JPanel {
    private JLabel prefixLabel;
    private JTextField inputField;
    private JLabel suffixLabel;
    private JLabel resultLabel;

    public CalculatorField(String labelText, String suffix, int x, int y, int width, int height, Color highlightColor) {
        setLayout(null);
        setBounds(x, y, width, height);
        setOpaque(false);

        prefixLabel = new JLabel(labelText, SwingConstants.RIGHT);
        prefixLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        prefixLabel.setBounds(0, 0, 120, height);
        prefixLabel.setForeground(highlightColor);
        add(prefixLabel);

        inputField = new JTextField();
        inputField.setBounds(125, 0, 90, height);
        add(inputField);

        suffixLabel = null;
        if (suffix != null && !suffix.isEmpty()) {
            suffixLabel = new JLabel(suffix);
            suffixLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            suffixLabel.setBounds(220, 0, 50, height);
            suffixLabel.setForeground(highlightColor);
            add(suffixLabel);
        }

        resultLabel = new JLabel();
        resultLabel.setBounds(125, 0, 90, height);
        resultLabel.setVisible(false);
        resultLabel.setForeground(highlightColor);
        add(resultLabel);

        configureNumericFilter();
    }

    private void configureNumericFilter() {
        DocumentFilter numericFilter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null)
                    return;
                if (validateInput(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null)
                    return;
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength());
                newText = newText.substring(0, offset) + text + newText.substring(offset + length);
                if (validateInput(newText)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean validateInput(String text) {
                return text.matches("^-?\\d*,?\\d*$") || text.isEmpty();
            }
        };

        ((AbstractDocument) inputField.getDocument()).setDocumentFilter(numericFilter);
    }

    public JTextField getInputField() {
        return inputField;
    }

    public String getText() {
        return inputField.getText();
    }

    public void setText(String text) {
        inputField.setText(text);
    }

    public void setResultVisibility(boolean showResult, String resultText) {
        inputField.setVisible(!showResult);
        resultLabel.setVisible(showResult);

        if (showResult && resultText != null) {
            resultLabel.setText(resultText);
        }
    }

    public boolean isInputEmpty() {
        return inputField.getText().trim().isEmpty();
    }

    public void addDocumentListener(javax.swing.event.DocumentListener listener) {
        inputField.getDocument().addDocumentListener(listener);
    }
}
