import logic.CalculatorLogic;
import test.CalculatorTest;
import ui.UISuperCalculator;

public class App {
    public static void main(String[] args) {
        new UISuperCalculator();

        CalculatorTest calculatorTest = new CalculatorTest(CalculatorLogic.calculateDeltaPercentage);

        calculatorTest.testCalculatorFunction(4, 8);
        calculatorTest.testRuleOfThree(1, 2, 6);
        calculatorTest.testPasswordGeneration(false, false, true, false, 999);
    }
}
