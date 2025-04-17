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
}
