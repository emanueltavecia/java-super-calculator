package ui.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

import logic.CalculatorLogic.QuadrupleCalculation;
import logic.CalculatorLogic.Result;
import java.text.DecimalFormat;

public class CalculatorRuleOfThree extends JPanel {

    private JTextField fieldA;
    private JTextField fieldB;
    private JTextField fieldR1;
    private JTextField fieldR2;
    private JLabel resultA;
    private JLabel resultB;
    private JLabel resultR1;
    private JLabel resultR2;
    private final QuadrupleCalculation quadrupleCalculation;
    private boolean updating = false;
    private JTextField secondLastEditedField = null;
    private JTextField lastEditedField = null;

    private static final int QUADRANT_WIDTH = 270;
    private static final int QUADRANT_HEIGHT = 140;
    private static final int QUADRANT_HORIZONTAL_SPACING = 10;
    private static final int QUADRANT_VERTICAL_SPACING = 10;
    private static final int BORDER_PADDING_X = 30;
    private static final int BORDER_PADDING_Y = 20;

    public CalculatorRuleOfThree(String title, String labelA, String labelB, String labelR1, String labelR2,
            String equalSuffix, String formulaText, int indexX, int indexY, QuadrupleCalculation quadrupleCalculation) {
        this.quadrupleCalculation = quadrupleCalculation;

        setLayout(null);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160)),
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)));

        int baseX = BORDER_PADDING_X + indexX * (QUADRANT_WIDTH + QUADRANT_HORIZONTAL_SPACING);
        int baseY = BORDER_PADDING_Y + indexY * (QUADRANT_HEIGHT + QUADRANT_VERTICAL_SPACING);
        setBounds(baseX, baseY, 300, 120);

        JLabel labelAText = new JLabel(labelA);
        labelAText.setBounds(10, 20, 30, 25);
        labelAText.setForeground(Color.RED);
        add(labelAText);

        fieldA = new JTextField();
        fieldA.setBounds(30, 20, 100, 25);
        applyNumericFilter(fieldA);
        add(fieldA);

        resultA = new JLabel();
        resultA.setBounds(30, 20, 100, 25);
        resultA.setForeground(Color.RED);
        resultA.setVisible(false);
        add(resultA);

        JLabel labelBText = new JLabel(labelB);
        labelBText.setBounds(10, 50, 30, 25);
        labelBText.setForeground(Color.BLUE);
        add(labelBText);

        fieldB = new JTextField();
        fieldB.setBounds(30, 50, 100, 25);
        applyNumericFilter(fieldB);
        add(fieldB);

        resultB = new JLabel();
        resultB.setBounds(30, 50, 100, 25);
        resultB.setForeground(Color.BLUE);
        resultB.setVisible(false);
        add(resultB);

        JLabel labelR1Text = new JLabel(labelR1);
        labelR1Text.setBounds(160, 20, 30, 25);
        labelR1Text.setForeground(Color.GREEN);
        add(labelR1Text);

        fieldR1 = new JTextField();
        fieldR1.setBounds(180, 20, 100, 25);
        applyNumericFilter(fieldR1);
        add(fieldR1);

        resultR1 = new JLabel();
        resultR1.setBounds(180, 20, 100, 25);
        resultR1.setForeground(Color.GREEN);
        resultR1.setVisible(false);
        add(resultR1);

        JLabel labelR2Text = new JLabel(labelR2);
        labelR2Text.setBounds(160, 50, 30, 25);
        labelR2Text.setForeground(Color.GRAY);
        add(labelR2Text);

        fieldR2 = new JTextField();
        fieldR2.setBounds(180, 50, 100, 25);
        applyNumericFilter(fieldR2);
        add(fieldR2);

        resultR2 = new JLabel();
        resultR2.setBounds(180, 50, 100, 25);
        resultR2.setForeground(Color.GRAY);
        resultR2.setVisible(false);
        add(resultR2);

        JLabel equalSymbol1 = new JLabel(equalSuffix);
        equalSymbol1.setFont(new Font("Arial", Font.PLAIN, 12));
        equalSymbol1.setBounds(140, 20, 20, 20);
        add(equalSymbol1);
        JLabel equalSymbol2 = new JLabel(equalSuffix);
        equalSymbol2.setFont(new Font("Arial", Font.PLAIN, 12));
        equalSymbol2.setBounds(140, 50, 20, 20);
        add(equalSymbol2);

        JLabel formulaLabel = new JLabel(formulaText);
        formulaLabel.setFont(new Font("Courier New", Font.PLAIN, 12));
        formulaLabel.setBounds(10, 80, 200, 20);
        add(formulaLabel);

        configureFields();
    }

    public class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string == null)
                return;
            if (isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
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
            if (isValidInput(newText)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidInput(String text) {
            return text.matches("^-?\\d*,?\\d*$") || text.isEmpty();
        }
    }

    private void applyNumericFilter(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new NumericDocumentFilter());
    }

    private void configureFields() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                JTextField field = getSourceField(e);
                if (field != null) {
                    updateEditedFields(field);
                    calculate();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                JTextField field = getSourceField(e);
                if (field != null) {
                    if (field.getText().isEmpty()) {
                        if (field == lastEditedField) {
                            lastEditedField = secondLastEditedField;
                            secondLastEditedField = null;
                        } else if (field == secondLastEditedField) {
                            secondLastEditedField = null;
                        }
                        hideResults();
                    }
                    calculate();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };

        fieldA.getDocument().addDocumentListener(listener);
        fieldB.getDocument().addDocumentListener(listener);
        fieldR1.getDocument().addDocumentListener(listener);
        fieldR2.getDocument().addDocumentListener(listener);
    }

    private JTextField getSourceField(DocumentEvent e) {
        if (e.getDocument() == fieldA.getDocument())
            return fieldA;
        if (e.getDocument() == fieldB.getDocument())
            return fieldB;
        if (e.getDocument() == fieldR1.getDocument())
            return fieldR1;
        if (e.getDocument() == fieldR2.getDocument())
            return fieldR2;
        return null;
    }

    private void updateEditedFields(JTextField field) {
        if (field != lastEditedField) {
            secondLastEditedField = lastEditedField;
            lastEditedField = field;
            hideResults();
        }
    }

    private void hideResults() {
        resultA.setVisible(false);
        resultB.setVisible(false);
        resultR1.setVisible(false);
        resultR2.setVisible(false);
    }

    private double parseNumber(String text) {
        try {
            return Double.parseDouble(text.replace(",", "."));
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    private void calculate() {
        if (updating)
            return;
        updating = true;

        try {
            double valueA = parseNumber(fieldA.getText());
            double valueB = parseNumber(fieldB.getText());
            double valueR1 = parseNumber(fieldR1.getText());
            double valueR2 = parseNumber(fieldR2.getText());

            boolean fieldAFilled = !Double.isNaN(valueA);
            boolean fieldBFilled = !Double.isNaN(valueB);
            boolean fieldR1Filled = !Double.isNaN(valueR1);
            boolean fieldR2Filled = !Double.isNaN(valueR2);

            int filledCount = 0;
            if (fieldAFilled)
                filledCount++;
            if (fieldBFilled)
                filledCount++;
            if (fieldR1Filled)
                filledCount++;
            if (fieldR2Filled)
                filledCount++;

            if (filledCount == 3) {

                Result result = quadrupleCalculation.calculate(valueA, valueB, valueR1, valueR2);

                if (result.isSuccess()) {
                    String formatted = new DecimalFormat("#,##0.00").format(result.getValue()).replace(".", ",");

                    if (!fieldAFilled) {
                        resultA.setText(formatted);
                        fieldA.setVisible(false);
                        resultA.setVisible(true);
                    } else if (!fieldBFilled) {
                        resultB.setText(formatted);
                        fieldB.setVisible(false);
                        resultB.setVisible(true);
                    } else if (!fieldR1Filled) {
                        resultR1.setText(formatted);
                        fieldR1.setVisible(false);
                        resultR1.setVisible(true);
                    } else {
                        resultR2.setText(formatted);
                        fieldR2.setVisible(false);
                        resultR2.setVisible(true);
                    }
                }
            } else {
                fieldA.setVisible(true);
                resultA.setVisible(false);

                fieldB.setVisible(true);
                resultB.setVisible(false);

                fieldR1.setVisible(true);
                resultR1.setVisible(false);

                fieldR2.setVisible(true);
                resultR2.setVisible(false);
            }

        } finally {
            updating = false;
        }

        repaint();
        revalidate();
    }
}
