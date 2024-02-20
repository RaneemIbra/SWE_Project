package org.example;

import org.example.ocsf.AbstractClient;

import java.time.LocalDateTime;

public class SimpleClient extends AbstractClient {
    private static SimpleClient client = null;
    private SimpleClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        String message= (String) msg;
        System.out.println(message);
    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("localhost", 3000);
        }
        return client;
    }
}
