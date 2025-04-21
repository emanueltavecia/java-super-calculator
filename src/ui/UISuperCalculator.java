package ui;

import javax.swing.JFrame;
import ui.components.CalculatorQuadrant;
import logic.CalculatorLogic;

public class UISuperCalculator extends JFrame {
    public UISuperCalculator() {
        setTitle("Super Calculator");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        createComponents();

        setVisible(true);
    }

    private void createComponents() {
        add(new CalculatorQuadrant(
                "Aplicar desconto % num valor",
                "Valor inicial R$ (a)",
                "% desconto (b)",
                "Resultado",
                null,
                "%",
                null,
                "v = a - (a * (b / 100))",
                0, 0,
                CalculatorLogic.calculateDiscount));

        add(new CalculatorQuadrant(
            "Qual era o valor original?",
            "Valor final R$ (a)",
            "% desconto (b)",
            "Valor inicial",
            null,
            "%",
            null,
            "v = (a*100 / (100 - b))",
            2, 1,
            CalculatorLogic.calculateOriginalValue));
    }
}
