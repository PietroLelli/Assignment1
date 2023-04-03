package it.unibo.ds.lab.presentation.client;

import com.google.gson.Gson;
import it.unibo.ds.presentation.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static it.unibo.ds.presentation.Response.Status.*;

public class ClientSideAuthenticator implements Authenticator {

    private final InetSocketAddress server;

    public ClientSideAuthenticator(String host, int port) {
        this.server = new InetSocketAddress(host, port);
    }

    @Override
    public void register(User user) throws BadContentException, ConflictException {
        try {
            rpc(new RegisterRequest(user), EmptyResponse.class);
        } catch (WrongCredentialsException e) {
            throw new IllegalStateException("Inconsistent behaviour of server: unexpected WrongCredentialsException", e);
        }
    }

    @Override
    public Token authorize(Credentials credentials) throws BadContentException, WrongCredentialsException {
        try {
            return rpc(new AuthorizeRequest(credentials), AuthorizeResponse.class);
        } catch (ConflictException e) {
            throw new IllegalStateException("Inconsistent behaviour of server: unexpected ConflictException", e);
        }
    }

    private <T, R> R rpc(Request<T> request, Class<? extends Response<R>> responseType) throws BadContentException, ConflictException, WrongCredentialsException {
        try (var socket = new Socket()) {
            socket.connect(server);
            marshallRequest(socket, request);
            return unmarshallResponse(socket, responseType);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private <T> void marshallRequest(Socket socket, Request<T> request) throws IOException {
        try {
            Gson gson = GsonUtils.createGson();
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
            gson.toJson(request, writer);//If the Gson obj contains a serializer for the request obj it will serialize it
            writer.flush();

        }finally {
            socket.shutdownOutput();
        }

    }

    private <T> T unmarshallResponse(Socket socket, Class<? extends Response<T>> responseType) throws IOException, BadContentException, ConflictException, WrongCredentialsException {
        try {
            Gson gson = GsonUtils.createGson();
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            Response<T> response = responseType.cast(gson.fromJson(reader,responseType));
            switch (response.getStatus()){
                case OK:
                    return response.getResult();
                case CONFLICT:
                    throw new ConflictException();
                case BAD_CONTENT:
                    throw new BadContentException();
                case WRONG_CREDENTIALS:
                    throw new WrongCredentialsException();
                default:
                    return null;//NON LO SO

            }

        }finally {

            socket.shutdownInput();
        }

    }


}
