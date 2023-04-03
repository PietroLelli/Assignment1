package it.unibo.ds.lab.presentation.server;

import java.util.Arrays;
import java.util.Objects;

public class CalculatorInvocation {
    private final String method;
    private final double firstArg;
    private final double[] otherArgs;

    public CalculatorInvocation(String method, double firstArg, double[] otherArgs) {
        this.method = method;
        this.firstArg = firstArg;
        this.otherArgs = otherArgs;
    }

    public String getMethod() {
        return method;
    }

    public double getFirstArg() {
        return firstArg;
    }

    public double[] getOtherArgs() {
        return otherArgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculatorInvocation that = (CalculatorInvocation) o;
        return Double.compare(that.firstArg, firstArg) == 0 && Objects.equals(method, that.method) && Arrays.equals(otherArgs, that.otherArgs);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(method, firstArg);
        result = 31 * result + Arrays.hashCode(otherArgs);
        return result;
    }
}
