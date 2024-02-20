package org.example;

import java.io.Serializable;

public class Users implements Serializable {
    String FullName;
    int UserID;
    String EmailAddress;
    String HomeAddress;
    int PhoneNumber;
    int GroupID;
    String Title;

    public Users(String fullName, int userID, String emailAddress, String homeAddress, int phoneNumber, int groupID, String title) {
        FullName = fullName;
        UserID = userID;
        EmailAddress = emailAddress;
        HomeAddress = homeAddress;
        PhoneNumber = phoneNumber;
        GroupID = groupID;
        Title = title;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getHomeAddress() {
        return HomeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        HomeAddress = homeAddress;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
