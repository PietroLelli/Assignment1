package it.unibo.ds.presentation;

public interface Calculator {
    double sum(double first, double... others);

    double subtract(double first, double... others);

    double multiply(double first, double... others);

    double divide(double first, double... others);
}
