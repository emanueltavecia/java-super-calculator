package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PasswordGeneratorQuadrant extends JPanel {
    private JCheckBox uppercaseCheck;
    private JCheckBox lowercaseCheck;
    private JCheckBox numbersCheck;
    private JCheckBox symbolsCheck;
    private JSpinner lengthSpinner;
    private JTextField passwordField;
    private JButton generateButton;

    public PasswordGeneratorQuadrant() {
        setLayout(null);
        setBounds(340, 320, 300, 220); // posicione conforme sua grid
        setBorder(BorderFactory.createTitledBorder("Gerador de Senhas"));

        uppercaseCheck = new JCheckBox("Maiúsculas");
        lowercaseCheck = new JCheckBox("Minúsculas");
        numbersCheck = new JCheckBox("Números");
        symbolsCheck = new JCheckBox("Símbolos");

        uppercaseCheck.setBounds(20, 30, 120, 20);
        lowercaseCheck.setBounds(150, 30, 120, 20);
        numbersCheck.setBounds(20, 60, 120, 20);
        symbolsCheck.setBounds(150, 60, 120, 20);

        add(uppercaseCheck);
        add(lowercaseCheck);
        add(numbersCheck);
        add(symbolsCheck);

        JLabel lengthLabel = new JLabel("Tamanho:");
        lengthLabel.setBounds(20, 90, 80, 20);
        add(lengthLabel);

        lengthSpinner = new JSpinner(new SpinnerNumberModel(8, 4, 100, 1));
        lengthSpinner.setBounds(100, 90, 50, 20);
        add(lengthSpinner);

        generateButton = new JButton("Gerar Senha");
        generateButton.setBounds(20, 120, 180, 30);
        add(generateButton);

        passwordField = new JTextField();
        passwordField.setBounds(20, 160, 250, 25);
        passwordField.setEditable(false);
        add(passwordField);
    }

    public void setGenerateAction(ActionListener listener) {
        generateButton.addActionListener(listener);
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
