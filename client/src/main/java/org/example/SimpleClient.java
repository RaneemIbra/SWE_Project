package org.example;

import antlr.debug.MessageAdapter;
import javafx.scene.control.Alert;
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
            try {
                localList = (List<Task>) msg;
                if(!localList.toString().startsWith("[Report")&&!localList.toString().startsWith("[User")
                &&!localList.toString().startsWith("[Message")){
                    TasksList.tasks.clear();
                    PendingTasks.tasks.clear();
                    MyTasks.tasks.clear();
                    for (Task task : localList) {
                        if (task.getAuthorized().equals("Authorized")) {
                            TasksList.tasks.add(task);
                            if(PrimaryController.viewUser !=null){
                                if (task.getUserName().equals(PrimaryController.viewUser.getFullName()) ||
                                        task.getVolunteer().equals(PrimaryController.viewUser.getFullName())) {
                                    MyTasks.tasks.add(task);
                                }
                            }else{
                                if (task.getUserName().equals(PrimaryController.currentUser.getFullName()) ||
                                        task.getVolunteer().equals(PrimaryController.currentUser.getFullName())) {
                                    System.out.println("handle client");
                                    MyTasks.tasks.add(task);
                                }
                            }
                        } else if (task.getAuthorized().equals("Unauthorized")) {
                            PendingTasks.tasks.add(task);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(localList.toString().startsWith("[User")){
                    UsersList.users = (List<Users>) msg;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                if(localList.toString().startsWith("[Message")){
                    PrimaryController.notifList = (List<NotificationMessage>) msg;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                EmergencyReports.reports = (List<Reports>) msg;
            } catch (Exception e) {
                e.printStackTrace();
            }
            callback.onResponse("Ready");
        } else if (msg.toString().equals("exists") || msg.toString().equals("doesn't exist")
                || msg.toString().startsWith("LogIn") || msg.toString().equals("Don't LogIn")
                || msg.toString().equals("WrongPassword")|| msg.equals("Changed")) {
            if (callback != null) {
                callback.onResponse(msg.toString());
            }
        }else if(msg.toString().startsWith("Emergency")){
            try {
                SimpleClient.getClient().sendToServer("get notifs");
            }catch (Exception e){
                e.printStackTrace();
            }
            if(callback!=null){
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
