package ui.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import logic.CalculatorLogic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemListener;

public class PasswordGeneratorQuadrant extends JPanel {
    private JCheckBox uppercaseCheck;
    private JCheckBox lowercaseCheck;
    private JCheckBox numbersCheck;
    private JCheckBox symbolsCheck;
    private JSpinner lengthSpinner;
    private SpinnerNumberModel spinnerModel;
    private JTextField passwordField;
    private JButton generateButton;

    public PasswordGeneratorQuadrant() {
        setLayout(null);
        setBounds(340, 320, 300, 180);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160)),
                "Gerador de Senhas",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.PLAIN, 12)));

        uppercaseCheck = new JCheckBox("Maiúsculas");
        uppercaseCheck.setSelected(true);
        lowercaseCheck = new JCheckBox("Minúsculas");
        lowercaseCheck.setSelected(true);
        numbersCheck = new JCheckBox("Números");
        numbersCheck.setSelected(true);
        symbolsCheck = new JCheckBox("Símbolos");
        symbolsCheck.setSelected(true);

        uppercaseCheck.setBounds(25, 30, 120, 20);
        lowercaseCheck.setBounds(155, 30, 120, 20);
        numbersCheck.setBounds(25, 60, 120, 20);
        symbolsCheck.setBounds(155, 60, 120, 20);

        ItemListener checkboxListener = e -> updateMinimumLength();
        uppercaseCheck.addItemListener(checkboxListener);
        lowercaseCheck.addItemListener(checkboxListener);
        numbersCheck.addItemListener(checkboxListener);
        symbolsCheck.addItemListener(checkboxListener);

        add(uppercaseCheck);
        add(lowercaseCheck);
        add(numbersCheck);
        add(symbolsCheck);

        JLabel lengthLabel = new JLabel("Tamanho:");
        lengthLabel.setBounds(20, 90, 80, 20);
        add(lengthLabel);

        spinnerModel = new SpinnerNumberModel(4, 4, 100, 1);
        lengthSpinner = new JSpinner(spinnerModel);
        lengthSpinner.setBounds(100, 90, 50, 20);
        add(lengthSpinner);

        generateButton = new JButton("Gerar");
        generateButton.setBounds(160, 90, 100, 30);
        generateButton.addActionListener(e -> {
            boolean uc = this.isUppercaseSelected();
            boolean lc = this.isLowercaseSelected();
            boolean num = this.isNumbersSelected();
            boolean sym = this.isSymbolsSelected();
            int length = this.getPasswordLength();

            String errorMessage = CalculatorLogic.validateSelectedPasswordOptions(uc,
                    lc,
                    num,
                    sym,
                    length);

            if (!errorMessage.isEmpty()) {
                JOptionPane.showMessageDialog(this, errorMessage);
                return;
            }

            String password = CalculatorLogic.generatePassword(uc, lc, num, sym, length);
            this.setPassword(password);
        });
        add(generateButton);

        passwordField = new JTextField();
        passwordField.setBounds(20, 130, 250, 25);
        passwordField.setEditable(false);
        passwordField.setBackground(new Color(255, 255, 255));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        add(passwordField);
    }

    private void updateMinimumLength() {
        int selectedCount = getSelectedCheckboxCount();

        int minLength = Math.max(1, selectedCount);

        spinnerModel.setMinimum(minLength);

        if ((int) lengthSpinner.getValue() < minLength) {
            lengthSpinner.setValue(minLength);
        }
    }

    private int getSelectedCheckboxCount() {
        int count = 0;
        if (uppercaseCheck.isSelected())
            count++;
        if (lowercaseCheck.isSelected())
            count++;
        if (numbersCheck.isSelected())
            count++;
        if (symbolsCheck.isSelected())
            count++;
        return count;
    }

    public boolean isUppercaseSelected() {
        return uppercaseCheck.isSelected();
    }

    public boolean isLowercaseSelected() {
        return lowercaseCheck.isSelected();
    }

    public boolean isNumbersSelected() {
        return numbersCheck.isSelected();
    }

    public boolean isSymbolsSelected() {
        return symbolsCheck.isSelected();
    }

    public int getPasswordLength() {
        return (int) lengthSpinner.getValue();
    }

    public void setPassword(String password) {
        passwordField.setText(password);
    }
}
