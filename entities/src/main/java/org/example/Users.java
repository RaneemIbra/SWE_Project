package org.example;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.persistence.*;

@Entity
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int UserID;   //the set id should be sufficient, no need for generated id
    String FullName;
    String Password;
    String EmailAddress;
    String HomeAddress;
    int PhoneNumber;
    int GroupID;
    String Title;
    boolean Active;

    public Users(String fullName, String password, int userID, String emailAddress,
                 String homeAddress, int phoneNumber, int groupID, String title, boolean active) {
        FullName = fullName;
        UserID = userID;
        EmailAddress = emailAddress;
        HomeAddress = homeAddress;
        PhoneNumber = phoneNumber;
        GroupID = groupID;
        Title = title;
        Password = password;
        Active =active;
    }

    public Users() {
        FullName = "";
        UserID = 0;
        EmailAddress = "";
        HomeAddress = "";
        PhoneNumber = 0;
        GroupID = 0;
        Title = "User";
        Password = "";
        Active = false;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        this.Active = active;
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
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

    @Override
    public String toString() {
        return String.format("User Name: %s\nUser ID: %d\nUser Email Address: %s\nUser Home Address: " +
                        "%s\nUser Phone Number: %d\nUser Group ID: %s\n", FullName, UserID, EmailAddress,
                HomeAddress, PhoneNumber, GroupID);
    }
}
