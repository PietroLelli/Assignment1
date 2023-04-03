package it.unibo.ds.lab.presentation.server;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import it.unibo.ds.presentation.*;

import javax.crypto.spec.GCMParameterSpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Objects;

public class ServerSideAuthenticatorStub extends Thread {

    private final Socket ephemeralSocket;
    private final Authenticator delegate;

    public ServerSideAuthenticatorStub(Socket socket, Authenticator delegate) {
        this.ephemeralSocket = Objects.requireNonNull(socket);
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public void run() {
        try (ephemeralSocket) {
            var request = unmarshallRequest(ephemeralSocket);
            var response = computeResponse(request);
            marshallResponse(ephemeralSocket, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request<?> unmarshallRequest(Socket socket) throws IOException {
        try{
            Gson gson = GsonUtils.createGson();
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            Request<?> request = gson.fromJson(reader, Request.class);
            return request;
        }finally {
            socket.shutdownInput();
        }
    }

    private Response<?> computeResponse(Request<?> request) {
        switch (request.getMethod()){
            case "authorize":
                try {
                    Token result = delegate.authorize((Credentials) request.getArgument());
                    return new AuthorizeResponse(Response.Status.OK, "", result);
                } catch (BadContentException e) {
                    return new AuthorizeResponse(Response.Status.BAD_CONTENT, "", null);
                } catch (WrongCredentialsException e) {
                    return new AuthorizeResponse(Response.Status.WRONG_CREDENTIALS, "", null);
                }
            case "register":
                try {
                    delegate.register((User) request.getArgument());
                    return new EmptyResponse(Response.Status.OK,"ok");
                } catch (BadContentException e) {
                    return new EmptyResponse(Response.Status.BAD_CONTENT,"");
                } catch (ConflictException e) {
                    return new EmptyResponse(Response.Status.CONFLICT,"");
                }
            default:
                throw new Error("This should never happen");
        }
    }

    private void marshallResponse(Socket socket, Response<?> response) throws IOException {
        try{
            Gson gson = GsonUtils.createGson();
            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
            gson.toJson(response, writer);
            writer.flush();
        }finally {
            socket.shutdownOutput();
        }
    }
}
