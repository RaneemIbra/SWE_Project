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
    private String salt;

    public Users(String fullName, String password, int userID, String emailAddress, String homeAddress, int phoneNumber, int groupID, String title) {
        FullName = fullName;
        UserID = userID;
        EmailAddress = emailAddress;
        HomeAddress = homeAddress;
        PhoneNumber = phoneNumber;
        GroupID = groupID;
        Title = title;
        Password = password;
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
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] saltBytes = new byte[16];
            secureRandom.nextBytes(saltBytes);
            this.salt = bytesToHex(saltBytes);

            String passwordWithSalt = password + salt;

            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digest.digest(passwordWithSalt.getBytes());
            this.Password = bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
