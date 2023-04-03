package it.unibo.ds.presentation;

public class LocalCalculator implements Calculator {
    @Override
    public double sum(double first, double... others) {
        var result = first;
        for (var item : others) {
            result += item;
        }
        return result;
    }

    @Override
    public double subtract(double first, double... others) {
        var result = first;
        for (var item : others) {
            result -= item;
        }
        return result;
    }

    @Override
    public double multiply(double first, double... others) {
        var result = first;
        for (var item : others) {
            result *= item;
        }
        return result;
    }

    @Override
    public double divide(double first, double... others) {
        var result = first;
        for (var item : others) {
            if (item == 0.0) {
                throw new ArithmeticException("Cannot divide by 0.0");
            }
            result /= item;
        }
        return result;
    }
}
