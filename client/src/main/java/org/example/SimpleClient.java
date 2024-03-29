package org.example;

import antlr.debug.MessageAdapter;
import org.example.ocsf.AbstractClient;

import java.time.LocalDateTime;
import java.util.List;

public class SimpleClient extends AbstractClient {
    private static SimpleClient client = null;

    private SimpleClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        TasksList.tasks = (List<Task>) msg;
    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("localhost", 3000);
        }
        return client;
    }
}
