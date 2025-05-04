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
    public interface CalculationFunction {
        Result calculate(double a, double b);
    }

    public interface QuadrupleCalculation {
        Result calculate(double a, double b, double R1, double R2);
    }

    public static CalculationFunction calculateDiscount = (initialValue, discount) -> {
        if (!Double.isNaN(initialValue) && !Double.isNaN(discount)) {
            double result = initialValue - (initialValue * discount / 100);
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        return Result.failure();
    };

    public static CalculationFunction calculateIncrement = (initialValue, increment) -> {
        if (!Double.isNaN(initialValue) && !Double.isNaN(increment)) {
            double result = initialValue + (initialValue * increment / 100);
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        return Result.failure();
    };

    public static CalculationFunction calculatePercentageOfTotal = (total, percentage) -> {
        if (!Double.isNaN(total) && !Double.isNaN(percentage)) {
            double result = (total * percentage) / 100;
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        return Result.failure();
    };

    public static CalculationFunction calculatePortionOfTotal = (total, portion) -> {
        if (!Double.isNaN(total) && !Double.isNaN(portion)) {
            double result = (portion * 100) / total;
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        return Result.failure();
    };

    public static CalculationFunction calculatePercentageDiscountFromTwoValues = (originalValue, paidValue) -> {
        if (!Double.isNaN(originalValue) && !Double.isNaN(paidValue)) {
            double result = ((originalValue - paidValue) / originalValue) * 100;
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        return Result.failure();
    };

    public static CalculationFunction calculateDeltaPercentage = (initialValue, finalValue) -> {
        if (!Double.isNaN(initialValue) && !Double.isNaN(finalValue)) {
            double result = ((finalValue - initialValue) / initialValue) * 100;
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        return Result.failure();
    };

    public static CalculationFunction calculateOriginalValue = (finalValue, discount) -> {
        if (!Double.isNaN(finalValue) && !Double.isNaN(discount)) {
            double result = finalValue * 100 / (100 - discount);
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        return Result.failure();
    };

    public static QuadrupleCalculation calculateRuleOfThree = (valueA, valueB, valueR1, valueR2) -> {
        if (!Double.isNaN(valueA) && !Double.isNaN(valueB) && !Double.isNaN(valueR1)) {
            double result = (valueR1 * valueB) / valueA;
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        if (!Double.isNaN(valueA) && !Double.isNaN(valueB) && !Double.isNaN(valueR2)) {
            double result = (valueR2 * valueA) / valueB;
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        if (!Double.isNaN(valueA) && !Double.isNaN(valueR1) && !Double.isNaN(valueR2)) {
            double result = (valueR2 * valueA) / valueR1;
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        if (!Double.isNaN(valueB) && !Double.isNaN(valueR1) && !Double.isNaN(valueR2)) {
            double result = (valueR1 * valueB) / valueR2;
            if (!Double.isNaN(result)) {
                return Result.success(result);
            }
        }
        return Result.failure();
    };
}
