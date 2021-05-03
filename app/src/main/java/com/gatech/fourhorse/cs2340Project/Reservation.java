package com.gatech.fourhorse.cs2340Project;

public class Reservation {

    private int reservationIndex;
    public int getReservationIndex() {
        return reservationIndex;
    }

    private User user;
    public User getUser() {
        return user;
    }

    private Shelter shelter;
    public Shelter getShelter() {
        return shelter;
    }
    public void setShelter(Shelter shelter){this.shelter = shelter;}

    private int shelterIndex; //The index of the reservation in shelter's reservation list
    public int getShelterIndex() {
        return shelterIndex;
    }

    private int beds;
    public int getBeds() {
        return beds;
    }
    public void setBeds(int beds) {
        this.beds = beds;
    }

    /**
     * Used for loading a reservation from the database
     */
    public Reservation(int reservationIndex, User user, Shelter shelter, int shelterIndex,
                       int beds) {
        this.reservationIndex = reservationIndex;
        this.user = user;
        this.shelter = shelter;
        this.shelterIndex = shelterIndex;
        this.beds = beds;

        user.setReservation(this);
        shelter.loadReservation(this, shelterIndex);
    }

    /**
     * Used for creating a reservation during normal use
     */
    public Reservation(int reservationIndex, User user, Shelter shelter, int beds) {
        this.reservationIndex = reservationIndex;
        this.user = user;
        this.shelter = shelter;
        this.beds = beds;

        shelterIndex = shelter.getNextEmptyReservation();

        user.setReservation(this);
        shelter.addReservation(this);

    }
}
