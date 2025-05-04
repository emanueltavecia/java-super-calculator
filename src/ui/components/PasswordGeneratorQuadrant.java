package ui.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import logic.CalculatorLogic;

public class PasswordGeneratorQuadrant extends JPanel {
    private JCheckBox checkboxUpper, checkboxLower, checkboxNumber, checkboxSymbol;
    private JSpinner inputLength;
    private JButton buttonGenerate;
    private JTextField passwordResult;
    private JLabel labelError;

    private static final int QUADRANT_WIDTH = 290;
    private static final int HEIGHT_DEFAULT = 140;
    private static final int HEIGHT_WITH_ERROR = 160;
    private static final int QUADRANT_HORIZONTAL_SPACING = 10;
    private static final int QUADRANT_VERTICAL_SPACING = 10;
    private static final int BORDER_PADDING_X = 30;
    private static final int BORDER_PADDING_Y = 20;

    public PasswordGeneratorQuadrant() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160)),
                "Gerador de Senha",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)));
        int baseX = BORDER_PADDING_X + 300 + QUADRANT_HORIZONTAL_SPACING;
        int baseY = BORDER_PADDING_Y + 2 * (HEIGHT_DEFAULT + QUADRANT_VERTICAL_SPACING);
        setBounds(baseX, baseY, QUADRANT_WIDTH, HEIGHT_DEFAULT);

        checkboxUpper = new JCheckBox("Maiúsculas");
        checkboxUpper.setBounds(10, 20, 100, 20);
        checkboxUpper.setSelected(true);
        add(checkboxUpper);

        checkboxLower = new JCheckBox("Minúsculas");
        checkboxLower.setBounds(120, 20, 100, 20);
        checkboxLower.setSelected(true);
        add(checkboxLower);

        checkboxNumber = new JCheckBox("Números");
        checkboxNumber.setBounds(10, 45, 100, 20);
        checkboxNumber.setSelected(true);
        add(checkboxNumber);

        checkboxSymbol = new JCheckBox("Símbolos");
        checkboxSymbol.setBounds(120, 45, 100, 20);
        checkboxSymbol.setSelected(true);
        add(checkboxSymbol);

        JLabel lblLength = new JLabel("Tamanho");
        lblLength.setBounds(10, 75, 60, 25);
        add(lblLength);

        inputLength = new JSpinner(new SpinnerNumberModel(8, 1, 100, 1));
        inputLength.setBounds(70, 75, 50, 25);
        add(inputLength);

        buttonGenerate = new JButton("Gerar");
        buttonGenerate.setBounds(130, 75, 80, 25);
        add(buttonGenerate);

        passwordResult = new JTextField();
        passwordResult.setBounds(10, 105, 270, 25);
        passwordResult.setEditable(true);
        add(passwordResult);

        labelError = new JLabel("");
        labelError.setForeground(Color.RED);
        labelError.setFont(new Font("Arial", Font.PLAIN, 11));
        labelError.setBounds(10, 135, 270, 15);
        labelError.setVisible(false);
        add(labelError);

        ItemListener checkListener = e -> adjustLengthIfNeeded();
        checkboxUpper.addItemListener(checkListener);
        checkboxLower.addItemListener(checkListener);
        checkboxNumber.addItemListener(checkListener);
        checkboxSymbol.addItemListener(checkListener);

        ((JSpinner.DefaultEditor) inputLength.getEditor()).getTextField().setEditable(false);
        inputLength.addChangeListener(e -> updateLengthInputState());

        buttonGenerate.addActionListener(e -> generatePassword());
        updateLengthInputState();
    }

    private int getCheckedCount() {
        int count = 0;
        if (checkboxUpper.isSelected())
            count++;
        if (checkboxLower.isSelected())
            count++;
        if (checkboxNumber.isSelected())
            count++;
        if (checkboxSymbol.isSelected())
            count++;
        return count;
    }

    private void adjustLengthIfNeeded() {
        int checked = getCheckedCount();
        int current = (int) inputLength.getValue();
        if (checked == 0) {
            buttonGenerate.setEnabled(false);
            showError("Selecione pelo menos uma opção.");
        } else {
            buttonGenerate.setEnabled(true);
            hideError();
            if (current < checked) {
                inputLength.setValue(checked);
            }
        }
        updateLengthInputState();
    }

    private void updateLengthInputState() {
        int checked = getCheckedCount();
        SpinnerNumberModel model = (SpinnerNumberModel) inputLength.getModel();
        model.setMinimum(checked > 0 ? checked : 1);
        buttonGenerate.setEnabled(checked > 0);

        JComponent editor = inputLength.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setEditable(false);
        }
    }

    private void generatePassword() {
        int length = (int) inputLength.getValue();
        int checked = getCheckedCount();
        if (length < checked) {
            showError("Tamanho deve ser maior ou igual ao número de opções selecionadas.");
            return;
        }
        hideError();
        String pwd = CalculatorLogic.generatePassword(
                length,
                checkboxUpper.isSelected(),
                checkboxLower.isSelected(),
                checkboxNumber.isSelected(),
                checkboxSymbol.isSelected());
        passwordResult.setText(pwd);
    }

    private void showError(String message) {
        labelError.setText(message);
        labelError.setVisible(true);
        setBounds(getX(), getY(), QUADRANT_WIDTH, HEIGHT_WITH_ERROR);
    }

    private void hideError() {
        labelError.setText("");
        labelError.setVisible(false);
        setBounds(getX(), getY(), QUADRANT_WIDTH, HEIGHT_DEFAULT);
    }
}