package test;

import logic.CalculatorLogic;
import logic.CalculatorLogic.CalculationFunction;
import logic.CalculatorLogic.Result;

public class CalculatorTest {
    CalculationFunction calculationFunction;

    public CalculatorTest(CalculationFunction calculationFunction) {
        this.calculationFunction = calculationFunction;
    }

    public void testCalculatorFunction(double a, double b) {
        Result result = calculationFunction.calculate(a, b);
        if (result.isSuccess()) {
            System.out.println("Resultado: " + result.getValue());
        } else {
            System.err.println("Informe valores válidos");
        }
    }

    public void testRuleOfThree(double a, double b, double c) {
        Result result = CalculatorLogic.calculateRuleOfThree.calculate(a, b, c, Double.NaN);
        if (result.isSuccess()) {
            System.out.println("Resultado: " + result.getValue());
        } else {
            System.err.println("Informe valores válidos");
        }
    }

    public void testPasswordGeneration(boolean upper, boolean lower, boolean number, boolean symbol, int length) {
        String errorMessage = CalculatorLogic.validateSelectedPasswordOptions(upper,
                lower,
                number,
                symbol,
                length);

        if (!errorMessage.isEmpty()) {
            System.err.println(errorMessage);
            return;
        }

        String password = CalculatorLogic.generatePassword(upper,
                lower,
                number,
                symbol,
                length);

        System.out.println("Senha: " + password);
    }
}
