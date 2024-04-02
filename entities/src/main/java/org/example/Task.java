package org.example;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int TaskID;
    String TaskName;
    String TaskDescription;
    String UserName;
    int UserID;
    String State;
    LocalDateTime SubmissionTime;
    String Volunteer;
    String Authorized;
    String DueDate;
    public Task(int taskID, String taskName, String taskDescription, String userName, int userID,
                String state, LocalDateTime submissionTime, String volunteer, String authorized, String dueDate) {
        TaskID = taskID;
        TaskName = taskName;
        TaskDescription = taskDescription;
        UserName = userName;
        UserID = userID;
        State = state;
        SubmissionTime = submissionTime;
        Volunteer = volunteer;
        Authorized = authorized;
        DueDate = dueDate;
    }

    public Task() {

    }

    public String getAuthorized() {
        return Authorized;
    }

    public void setAuthorized(String authorized) {
        Authorized = authorized;
    }

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public LocalDateTime getSubmissionTime() {
        return SubmissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        SubmissionTime = submissionTime;
    }

    public String getVolunteer() {
        return Volunteer;
    }

    public void setVolunteer(String volunteer) {
        Volunteer = volunteer;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    @Override
    public String toString() {
        return String.format("Task ID: %d\nTask Description: %s\nUser Name: %s\nUser ID: " +
                "%d\nState: %s\nVolunteer: %s\nSubmission Time: %s\nDue Date: %s",
                TaskID, TaskDescription, UserName, UserID, State, Volunteer, SubmissionTime, DueDate);
    }
}
