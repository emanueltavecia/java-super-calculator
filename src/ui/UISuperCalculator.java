package ui;

import javax.swing.JFrame;
import ui.components.CalculatorQuadrant;
import ui.components.CalculatorRuleOfThree;
import ui.components.PasswordGeneratorQuadrant;
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
                "Aplicar incremento % num valor",
                "Valor inicial R$ (a)",
                "% incremento (b)",
                "Resultado",
                null,
                "%",
                null,
                "v = a + (a * (b / 100))",
                1, 0,
                CalculatorLogic.calculateIncrement));

        add(new CalculatorQuadrant(
                "Amostragem - Quanto X% representa de Y?",
                "Total (a)",
                "Porcentagem (b)",
                "Corresponde a",
                null,
                "%",
                null,
                "v = (a * b) / 100",
                2, 0,
                CalculatorLogic.calculatePercentageOfTotal));

        add(new CalculatorQuadrant(
                "Amostragem 2 - Quanto X representa de Y?",
                "Total (a)",
                "Parte (b)",
                "Corresponde a %",
                null,
                null,
                "%",
                "v = (b * 100) / a",
                3, 0,
                CalculatorLogic.calculatePortionOfTotal));

        add(new CalculatorQuadrant(
                "Valor era A e paguei B, qual foi o desconto %?",
                "Valor original (a)",
                "Valor c/desconto (b)",
                "% desconto",
                null,
                null,
                "%",
                "v = ((a - b) / a) * 100",
                0, 1,
                CalculatorLogic.calculatePercentageDiscountFromTwoValues));

        add(new CalculatorQuadrant(
                "Variação Delta [%] - diferença % entre valores",
                "Valor inicial (a)",
                "Valor final (b)",
                "Diferença %",
                null,
                "",
                "%",
                "v = ((b - a) / a) * 100",
                1, 1,
                CalculatorLogic.calculateDeltaPercentage));

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

        add(new CalculatorRuleOfThree(
                "Regra de três",
                "a:",
                "b:",
                "r1:",
                "r2:",
                " = ",
                "r2 = (r1 * b) / a",
                0, 2,
                CalculatorLogic.calculateRuleOfThree));

        add(new PasswordGeneratorQuadrant());
    }
}
