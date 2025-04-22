package logic;

public class CalculatorLogic {
    public static class Result {
        private final double value;
        private final boolean success;

        private Result(double value, boolean success) {
            this.value = value;
            this.success = success;
        }

        public static Result success(double value) {
            return new Result(value, true);
        }

        public static Result failure() {
            return new Result(0, false);
        }

        public double getValue() {
            return value;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    @FunctionalInterface
    public interface TripleCalculation {
        Result calculate(double a, double b, double result);
    }

    public interface QuadrupleCalculation {
        Result calculate(double a, double b, double R1, double R2);
    }

    public static TripleCalculation calculateDiscount = (initialValue, discount, finalValue) -> {
        if (!Double.isNaN(initialValue) && !Double.isNaN(discount)) {
            return Result.success(initialValue - (initialValue * discount / 100));
        }
        if (!Double.isNaN(initialValue) && !Double.isNaN(finalValue)) {
            return Result.success((1 - finalValue / initialValue) * 100);
        }
        if (!Double.isNaN(discount) && !Double.isNaN(finalValue)) {
            return Result.success(finalValue / (1 - discount / 100));
        }
        return Result.failure();
    };

    public static QuadrupleCalculation calculateRuleOfThree = (valueA, valueB, valueR1, valueR2) -> {
        if (!Double.isNaN(valueA) && !Double.isNaN(valueB) && !Double.isNaN(valueR1)) {
            return Result.success((valueR1 * valueB) / valueA);
        }
        if (!Double.isNaN(valueA) && !Double.isNaN(valueB) && !Double.isNaN(valueR2)) {
            return Result.success((valueR2 * valueA) / valueB);
        }
        if (!Double.isNaN(valueA) && !Double.isNaN(valueR1) && !Double.isNaN(valueR2)) {
            return Result.success((valueR2 * valueA) / valueR1);
        }
        if (!Double.isNaN(valueB) && !Double.isNaN(valueR1) && !Double.isNaN(valueR2)) {
            return Result.success((valueR1 * valueB) / valueR2);
        }
        return Result.failure();
    };
}
