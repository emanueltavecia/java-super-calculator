package ui.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.DecimalFormat;
import logic.CalculatorLogic.TripleCalculation;
import logic.CalculatorLogic.Result;

public class CalculatorQuadrant extends JPanel {
    private CalculatorField fieldA;
    private CalculatorField fieldB;
    private CalculatorField resultField;
    private JPanel resultPanel;
    private final TripleCalculation tripleCalculation;
    private boolean updating = false;
    private JTextField secondLastEditedField = null;
    private JTextField lastEditedField = null;
    private final String resultLabelText;
    private JLabel resultPrefixLabel;
    private JLabel resultSuffixLabel;

    private static final int QUADRANT_WIDTH = 270;
    private static final int QUADRANT_HEIGHT = 140;
    private static final int QUADRANT_HORIZONTAL_SPACING = 10;
    private static final int QUADRANT_VERTICAL_SPACING = 10;
    private static final int BORDER_PADDING_X = 30;
    private static final int BORDER_PADDING_Y = 20;

    public CalculatorQuadrant(String title, String fieldALabel, String fieldBLabel, String resultLabel,
            String fieldASuffix, String fieldBSuffix, String resultSuffix,
            String formulaText, int indexX, int indexY, TripleCalculation tripleCalculation) {
        this.tripleCalculation = tripleCalculation;
        this.resultLabelText = resultLabel;

        setLayout(null);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160)),
                title,
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)));

        int baseX = BORDER_PADDING_X + indexX * (QUADRANT_WIDTH + QUADRANT_HORIZONTAL_SPACING);
        int baseY = BORDER_PADDING_Y + indexY * (QUADRANT_HEIGHT + QUADRANT_VERTICAL_SPACING);
        setBounds(baseX, baseY, QUADRANT_WIDTH, QUADRANT_HEIGHT);

        fieldA = new CalculatorField(fieldALabel, fieldASuffix, 10, 20, 270, 20, Color.RED);
        add(fieldA);

        fieldB = new CalculatorField(fieldBLabel, fieldBSuffix, 10, 50, 270, 20, Color.BLUE);
        add(fieldB);

        resultPanel = new JPanel(null);
        resultPanel.setBounds(10, 80, 270, 20);
        resultPanel.setOpaque(false);
        add(resultPanel);

        resultPrefixLabel = new JLabel(resultLabelText, SwingConstants.RIGHT);
        resultPrefixLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        resultPrefixLabel.setBounds(0, 0, 120, 20);
        resultPrefixLabel.setForeground(new Color(64, 64, 64));
        resultPanel.add(resultPrefixLabel);

        resultSuffixLabel = null;
        if (resultSuffix != null && !resultSuffix.isEmpty()) {
            resultSuffixLabel = new JLabel(resultSuffix);
            resultSuffixLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            resultSuffixLabel.setBounds(220, 0, 50, 20);
            resultSuffixLabel.setForeground(new Color(64, 64, 64));
            resultPanel.add(resultSuffixLabel);
        }

        resultField = new CalculatorField("", "", 0, 0, 270, 20, new Color(64, 64, 64));
        resultPanel.add(resultField);

        JLabel formulaLabel = new JLabel(formulaText);
        formulaLabel.setFont(new Font("Courier New", Font.PLAIN, 12));
        formulaLabel.setBounds(10, 110, 250, 20);
        add(formulaLabel);

        configureFields();
    }

    private JTextField getFieldFromDocument(javax.swing.text.Document doc) {
        if (doc == fieldA.getInputField().getDocument())
            return fieldA.getInputField();
        if (doc == fieldB.getInputField().getDocument())
            return fieldB.getInputField();
        if (doc == resultField.getInputField().getDocument())
            return resultField.getInputField();
        return null;
    }

    private void configureFields() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                JTextField field = getFieldFromDocument(e.getDocument());
                if (field != null) {
                    updateEditedFields(field);
                    calculate();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                JTextField field = getFieldFromDocument(e.getDocument());
                if (field != null) {
                    if (field.getText().isEmpty()) {
                        if (field == lastEditedField) {
                            lastEditedField = secondLastEditedField;
                            secondLastEditedField = null;
                        } else if (field == secondLastEditedField) {
                            secondLastEditedField = null;
                        }
                        checkResultVisibility();
                    }
                    calculate();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };

        fieldA.addDocumentListener(listener);
        fieldB.addDocumentListener(listener);
        resultField.addDocumentListener(listener);
    }

    private void updateEditedFields(JTextField field) {
        if (field != lastEditedField) {
            secondLastEditedField = lastEditedField;
            lastEditedField = field;
            checkResultVisibility();
        }
    }

    private void checkResultVisibility() {
        boolean fieldAFilled = !fieldA.isInputEmpty();
        boolean fieldBFilled = !fieldB.isInputEmpty();
        boolean resultFieldFilled = !resultField.isInputEmpty();

        boolean shouldCalculate = (fieldAFilled && fieldBFilled && !resultFieldFilled) ||
                (fieldAFilled && !fieldBFilled && resultFieldFilled) ||
                (!fieldAFilled && fieldBFilled && resultFieldFilled);
        if (!shouldCalculate) {
            fieldA.setResultVisibility(false, null);
            fieldB.setResultVisibility(false, null);
            resultField.setResultVisibility(false, null);
        }

        resultPrefixLabel.setVisible(true);
        if (resultSuffixLabel != null) {
            resultSuffixLabel.setVisible(true);
        }
    }

    private double parseNumber(String text) {
        if (text == null || text.trim().isEmpty()) {
            return Double.NaN;
        }
        try {
            return Double.parseDouble(text.replace(",", "."));
        } catch (NumberFormatException e) {
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
            double resultValue = parseNumber(resultField.getText());

            boolean fieldAFilled = !Double.isNaN(valueA);
            boolean fieldBFilled = !Double.isNaN(valueB);
            boolean resultFieldFilled = !Double.isNaN(resultValue);

            String resultLabelAText = "";
            String resultLabelBText = "";
            String resultLabelCText = "";

            if ((fieldAFilled && fieldBFilled && !resultFieldFilled) ||
                    (fieldAFilled && !fieldBFilled && resultFieldFilled) ||
                    (!fieldAFilled && fieldBFilled && resultFieldFilled)) {

                Result result = tripleCalculation.calculate(valueA, valueB, resultValue);
                if (result.isSuccess()) {
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    df.setDecimalSeparatorAlwaysShown(true);
                    String formattedValue = df.format(result.getValue()).replace(".", ",");

                    if (!fieldAFilled) {
                        resultLabelAText = formattedValue;
                    } else if (!fieldBFilled) {
                        resultLabelBText = formattedValue;
                    } else {
                        resultLabelCText = formattedValue;
                    }
                }
            }

            if (!fieldAFilled) {
                fieldA.setResultVisibility(true, resultLabelAText);
            }

            if (!fieldBFilled) {
                fieldB.setResultVisibility(true, resultLabelBText);
            }

            if (!resultFieldFilled) {
                resultField.setResultVisibility(true, resultLabelCText);
            }

            checkResultVisibility();
        } finally {
            updating = false;
        }
    }
}
