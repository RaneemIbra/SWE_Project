package org.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Reports implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ReportID;
    String ReportName;
    int UserID;
    String FullName;
    LocalDateTime EmergencyTime;
    String Location;
    int GrouId;

    public Reports(String reportName, int reportID, int userID,
                   String fullName, LocalDateTime emergencyTime, String location, int grouId) {
        ReportID = reportID;
        UserID = userID;
        FullName = fullName;
        EmergencyTime = emergencyTime;
        Location = location;
        ReportName = reportName;
        GrouId = grouId;
    }

    public Reports() {
        ReportName = "";
        ReportID = 0;
        UserID = 0;
        FullName = "";
        EmergencyTime = null;
        Location = "";
        GrouId = 0;
    }

    public String getReportName() {
        return ReportName;
    }

    public void setReportName(String reportName) {
        ReportName = reportName;
    }

    public int getReportID() {
        return ReportID;
    }

    public void setReportID(int reportID) {
        ReportID = reportID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public LocalDateTime getEmergencyTime() {
        return EmergencyTime;
    }

    public void setEmergencyTime(LocalDateTime emergencyTime) {
        EmergencyTime = emergencyTime;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getGrouId() {
        return GrouId;
    }

    public void setGrouId(int grouId) {
        GrouId = grouId;
    }

    @Override
    public String toString() {
        return String.format("Report Name: %s\nReport ID: %d\nFull Name: %s\nUser ID: " +
                        "%d\nSubmission Time: %s\nLocation: %s", ReportName, ReportID, FullName, UserID
                , EmergencyTime, Location);
    }
}
