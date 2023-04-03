package it.unibo.ds.lab.presentation.server;

import it.unibo.ds.presentation.Calculator;
import it.unibo.ds.presentation.LocalCalculator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class ServerSideCalculatorStub extends Thread {

    private final Calculator delegate = new LocalCalculator(); //Local object for calculate all the operations
    private final Socket ephemeralSocket;

    public ServerSideCalculatorStub(Socket socket) {
        this.ephemeralSocket = Objects.requireNonNull(socket);
    }

    @Override
    public void run() {
        try (ephemeralSocket) {
            var invocation = unmarshallInvocation(ephemeralSocket);
            var result = computeResult(invocation);
            marshallResult(ephemeralSocket, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CalculatorInvocation unmarshallInvocation(Socket socket) throws IOException {
        try{
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String methodName = inputStream.readUTF();
            double firstArg = inputStream.readDouble();
            int readLength = inputStream.readInt(); //Length of the array of the arguments
            double[] otherArgs = new double[readLength];
            for(int i = 0; i < readLength; i++){
                otherArgs[i] = inputStream.readDouble();
            }
            return  new CalculatorInvocation(methodName, firstArg, otherArgs);
        }finally {
            socket.shutdownInput();
        }
    }

    private CalculatorResult computeResult(CalculatorInvocation invocation) {
        try {
            double result;
            switch (invocation.getMethod()) {
                case "sum":
                    result = delegate.sum(invocation.getFirstArg(), invocation.getOtherArgs());
                    break;
                case "subtract":
                    result = delegate.subtract(invocation.getFirstArg(), invocation.getOtherArgs());
                    break;
                case "multiply":
                    result = delegate.multiply(invocation.getFirstArg(), invocation.getOtherArgs());
                    break;
                case "divide":
                    result = delegate.divide(invocation.getFirstArg(), invocation.getOtherArgs());
                    break;
                default:
                    return new CalculatorResult(CalculatorResult.BAD_CONTENT, null);
            }
            return new CalculatorResult(CalculatorResult.STATUS_SUCCESS, result);
        }catch (ArithmeticException e){
            return new CalculatorResult(CalculatorResult.DIVIDE_BY_ZERO, null);
        }catch (RuntimeException e){
            return new CalculatorResult(CalculatorResult.INTERNAL_SERVER_ERROR, null);
        }
    }

    private void marshallResult(Socket socket, CalculatorResult result) throws IOException {
        try{
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeInt(result.getStatus());
            if(result.getStatus() == CalculatorResult.STATUS_SUCCESS){
                outputStream.writeDouble(result.getResult());
            }
        }finally {
            socket.shutdownOutput();
        }
    }
}
