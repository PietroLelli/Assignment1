package it.unibo.ds.lab.presentation.server;

import java.util.Objects;

public class CalculatorResult {

    public static final int STATUS_SUCCESS = 0;
    public static final int BAD_CONTENT = 1;
    public static final int DIVIDE_BY_ZERO = 2;
    public static final int INTERNAL_SERVER_ERROR = 3;

    private final int status;
    private final Double result;

    public CalculatorResult(int status, Double result) {
        this.status = status;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public Double getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculatorResult that = (CalculatorResult) o;
        return status == that.status && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, result);
    }
}
