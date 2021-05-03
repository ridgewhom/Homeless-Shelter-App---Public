package com.gatech.fourhorse.cs2340Project;

import java.util.Date;

public class User {

    private String username;
    public String getUsername() { return username; }

    private String password;
    public String getPassword() { return password; }

    private String firstName;
    public String getFirstName() { return firstName; }

    private String lastName;
    public String getLastName() { return lastName; }

    private Date dateOfBirth;
    public Date getDateOfBirth() { return dateOfBirth; }

    private Gender gender;
    public Gender getGender() { return gender; }

    private UserType userType;
    public UserType getUserType() { return userType; }

    private String banned;
    public String getBanned(){return banned;}

    private Reservation reservation;    // User can only have one reservation at a time (or null)
    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public User(String username, String password, String firstName, String lastName,
                Date dateOfBirth, Gender gender, UserType userType) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.userType = userType;
        this.banned = "false";
    }
    public User(String username, String password, String firstName, String lastName,
                Date dateOfBirth, Gender gender, UserType userType, String banned) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.userType = userType;
        this.banned = banned;
    }

}
