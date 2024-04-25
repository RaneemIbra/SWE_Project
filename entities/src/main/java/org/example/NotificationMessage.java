package org.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class NotificationMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int messageID;
    String message;
    LocalDateTime time;
    String sender;
    String receiver;
    String taskName;
    int taskID;
    public NotificationMessage(int messageID, String message, LocalDateTime time, String sender, String receiver) {
        this.messageID = messageID;
        this.message = message;
        this.time = time;
        this.sender= sender;
        this.receiver = receiver;
    }

    public NotificationMessage(int messageID,String taskName, int taskID , String message, LocalDateTime time, String sender, String receiver) {
        this.messageID = messageID;
        this.message = message;
        this.time = time;
        this.sender= sender;
        this.receiver = receiver;
        this.taskName = taskName;
        this.taskID = taskID;
    }

    public NotificationMessage() {
        messageID =0;
        message = "";
        time  = null;
        sender="";
        receiver="";
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("Message ID: %d\nSender: %s\nReceiver: %s\nMessage: %s\nSubmission Time: %s\nTask ID: %d\nTask Name: %s",
                messageID,sender,receiver ,message, time, taskID, taskName);
    }
}
