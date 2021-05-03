package com.gatech.fourhorse.cs2340Project;

import java.util.ArrayList;
import java.util.Date;

public class Model {

    public static boolean configured = false;

    public static int nextEmptyReservation = 1;



    public int getNextEmptyReservation() {
        return nextEmptyReservation;
    }

    private static ArrayList<Shelter> shelters = new ArrayList<>();



    //needed for refreshing
    private static Shelter tmpShelter;
    public static Shelter getTmpShelter() {
        return tmpShelter;
    }

    public static void setTmpShelter(Shelter tmpShelter) {
        Model.tmpShelter = tmpShelter;
    }




    /**
     * Represents the shelter currently being seen in detail. If a reservation is created, it is
     * created for this shelter. Update as appropriate, or set to null if no current shelter.
     */
    private static Shelter currentShelter;
    public static Shelter getCurrentShelter() {
        return currentShelter;
    }
    public static void setCurrentShelter(Shelter currentShelter) {
        Model.currentShelter = currentShelter;
    }

    private static User user;
    public static User getUser() {
        return user;
    }
    public static void setUser(User user, ReservationAction completionAction) {
        Model.user = user;
        DataLoader.userLoadReservation(user, completionAction);
    }

    public static void configure(VoidAction completionAction) {
        DataLoader.start();
        DataLoader.loadShelters(completionAction);
        DataLoader.getNextEmptyReservation(-1, new IntegerAction() {
            @Override
            public void integerAction(int number) {
                nextEmptyReservation = number;
            }
        });
    }

    /**
     * Attempts to find a user with username user in the database. If successful, successAction is
     * called with the found user. Otherwise failureAction is called. This does not set the current
     * user. For login, used to see if the username is correct. For registration, used to see if the
     * username is already used.
     * @param username
     * @param successAction
     * @param failureAction
     */
    public static void findUser(String username, UserAction successAction,
                                VoidAction failureAction) {
        DataLoader.findUser(username, successAction, failureAction);
    }

    /**
     * Saves the user with these parameters into the database. This does not set the current user.
     * Used in registration.
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param gender
     * @param userType
     */
    public static void createUser(String username, String password, String firstName,
                                  String lastName, Date dateOfBirth, Gender gender,
                                  UserType userType) {
        User newUser = new User(username, password, firstName, lastName, dateOfBirth, gender,
                userType, "False");
        DataLoader.saveUser(newUser);
    }

    /**
     * Adds a shelter to the shelter list. Used during configuration.
     * @param shelter
     */
    public static void addShelter(Shelter shelter) {
        shelters.add(shelter);
        System.out.println("\"" + shelter.getName() + "\" loaded");
    }

    /**
     * Returns the number of shelters
     * @return
     */
    public static int numberOfShelters() {
        return shelters.size();
    }

    /**
     * Gets the shelter at index index
     * @param index
     * @return
     */
    public static Shelter getShelter(int index) {
        return shelters.get(index);
    }

    /**
     * Returns the list of shelters
     * @return
     */
    public static ArrayList<Shelter> getShelters() {
        return shelters;
    }

    /**
     * Changes the number of beds reserved at Model.currentShelter by Model.user to newSpots. If
     * there was no reservation before, a new one is created. If newSpots is zero, then any existing
     * reservation is deleted.
     * @param newSpots
     */
    public static void reserve(int newSpots) {
        if (user.getReservation() != null) {
            if (newSpots != user.getReservation().getBeds()) {
                // Number of beds has changed
                if (newSpots == 0) {
                    // Deletes reservation
                    deleteReservation(user.getReservation());
                } else {
                    // Modifies reservation
                    System.out.println(user.getReservation());
                    modifyReservation(user.getReservation(), newSpots);
                }
            }
        } else {
            System.out.println("L00g creates");
            // Creates reservation
            createReservation(user, currentShelter, newSpots);
        }
    }

    private static void createReservation(User user, Shelter shelter, int beds) {
        Reservation newReservation = new Reservation(nextEmptyReservation, user, shelter, beds);
        shelter.changeCurrentAvailable(-beds);
        DataLoader.createReservation(newReservation);
        DataLoader.getNextEmptyReservation(nextEmptyReservation + 1, new IntegerAction() {
            @Override
            public void integerAction(int number) {
                nextEmptyReservation = number;
                DataLoader.setNextEmptyReservation(nextEmptyReservation);
            }
        });
    }

    private static void deleteReservation(Reservation reservation) {
        reservation.getUser().setReservation(null);
        if (reservation.getShelterIndex() < reservation.getShelter().getNextEmptyReservation()) {
            reservation.getShelter().setNextEmptyReservation(reservation.getShelterIndex());
        }
        reservation.getShelter().changeCurrentAvailable(reservation.getBeds());
        if (reservation.getReservationIndex() < nextEmptyReservation) {
            nextEmptyReservation = reservation.getReservationIndex();
        }
        DataLoader.deleteReservation(reservation);
        DataLoader.setNextEmptyReservation(nextEmptyReservation);
    }

    private static void modifyReservation(Reservation reservation, int newSpots) {
        reservation.getShelter().changeCurrentAvailable(reservation.getBeds() - newSpots);
        reservation.setBeds(newSpots);
        DataLoader.modifyReservation(reservation);
    }

    /**
     * Gets the next empty reservation from this shelter's reservation list. Calls completionAction
     * with the found nextEmptyReservation upon completion. Calls it with -1 if an error occurs.
     * @param shelter
     * @param previousEmpty
     * @param completionAction
     */
    public static void getNextEmptyReservation(Shelter shelter, int previousEmpty,
            IntegerAction completionAction) {
        DataLoader.getNextEmptyReservation(shelter, previousEmpty, completionAction);
    }

}
