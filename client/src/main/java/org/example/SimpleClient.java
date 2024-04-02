package org.example;

import antlr.debug.MessageAdapter;
import org.example.ocsf.AbstractClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SimpleClient extends AbstractClient {
    private static SimpleClient client = null;
    private ServerResponseCallback callback;

    public static List<Task> localList;

    private SimpleClient(String host, int port) {
        super(host, port);
    }

    public void setCallback(ServerResponseCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        if (msg instanceof List) {
            TasksList.tasks = (List<Task>) msg;
            PendingTasks.tasks = (List<Task>) msg;
        } else if (msg.toString().equals("exists") || msg.toString().equals("doesn't exist")
                || msg.toString().startsWith("LogIn") || msg.toString().equals("Don't LogIn")
                || msg.toString().equals("WrongPassword")) {
            if (callback != null) {
                callback.onResponse(msg.toString());
            }
        }
    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("localhost", 3000);
        }
        return client;
    }
}
