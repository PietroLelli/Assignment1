package it.unibo.ds.lab.presentation.client;

import it.unibo.ds.presentation.Calculator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSideCalculator implements Calculator {

    private final InetSocketAddress server;

    public ClientSideCalculator(String host, int port) {
        this.server = new InetSocketAddress(host, port);
    }

    @Override
    public double sum(double first, double... others) {
        return rpc("sum", first, others);
    }

    @Override
    public double subtract(double first, double... others) {
        return rpc("subtract", first, others);
    }

    @Override
    public double multiply(double first, double... others) {
        return rpc("multiply", first, others);
    }

    @Override
    public double divide(double first, double... others) {
        return rpc("divide", first, others);
    }

    private double rpc(String method, double first, double... others) {
        try (var socket = new Socket()) {
            socket.connect(server);
            marshallInvocation(socket, method, first, others);
            return unmarshallResult(socket);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void marshallInvocation(Socket socket, String method, double first, double... others) throws IOException {
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(method);
            outputStream.writeDouble(first);
            outputStream.writeInt(others.length);
            for(int i = 0; i < others.length; i++){
                outputStream.writeDouble(others[i]);
            }
        }finally {
            socket.shutdownOutput();
        }
    }

    private double unmarshallResult(Socket socket) throws IOException {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            int statusCode = inputStream.readInt();
            switch (statusCode){
                case 0:
                    return inputStream.readDouble();
                case 2:
                    throw new ArithmeticException();
                case 1:
                    throw new Error("Bad client implementation. Conctact sales"); //Exception not handled. It's an error caused by the client not by the server
                default:
                    throw new IllegalStateException();
            }
        }finally {
            socket.shutdownInput();
        }
    }
}
