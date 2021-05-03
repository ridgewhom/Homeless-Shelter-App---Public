package com.gatech.fourhorse.cs2340Project;

import java.util.HashMap;
import java.util.Map;

public class Shelter {
    private int key;
    private String name;
    private String capacity;
    private int numericCapacity;
    private int currentAvailable;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String notes;
    private String phone;
    private Double distance;
    private Map<Integer, Reservation> reservations;
    private int nextEmptyReservation;

    public Shelter(int key, String name, String capacity, int numericCapacity, int available,
                   String restrictions, double longitude, double latitude, String address,
                   String notes, String phone, int nextEmptyReservation) {
        this.key = key;
        this.name = name;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.notes = notes;
        this.phone = phone;
        this.numericCapacity = numericCapacity;

        /*if let location = Model.location {
            let lat1 = .pi * location[0] / 180
            let lat2 = .pi * latitude / 180
            let long1 = .pi * location[1] / 180
            let long2 = .pi * longitude / 180
            let dLat = abs(lat1 - lat2)
            let dLong = abs(long1 - long2)
            let a = pow(sin(dLat/2), 2) + cos(lat1)*cos(lat2)*pow(sin(dLong/2), 2)
            let c = 2 * atan2(sqrt(a), sqrt(1-a))
            distance = Double(round(10 * 3959 * c) / 10)
        }*/
        distance = null;

        reservations = new HashMap<>();

        currentAvailable = available;
        this.nextEmptyReservation = nextEmptyReservation;
        //reservations = [:]
    }

    public int getKey() {
        return key;
    }
    public String getName() {
        return name;
    }
    public String getCapacity() {
        return capacity;
    }
    public int getNumericCapacity() {
        return numericCapacity;
    }
    public int getCurrentAvailable() {
        return currentAvailable;
    }
    public void setCurrentAvailable(int currentAvailable) {
        this.currentAvailable = currentAvailable;
    }
    public void changeCurrentAvailable(int change) {
        this.currentAvailable += change;
    }
    public String getRestrictions() {
        return restrictions;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public String getAddress() {
        return address;
    }
    public String getNotes() {
        return notes;
    }
    public String getPhone() {
        return phone;
    }
    public Double getDistance() {
        return distance;
    }
    private Reservation getReservation(int index) {
        return reservations.get(index);
    }
    private int getNumReservations() {
        return reservations.size();
    }
    public int getNextEmptyReservation() {
        return nextEmptyReservation;
    }
    public void setNextEmptyReservation(int nextEmptyReservation) {
        this.nextEmptyReservation = nextEmptyReservation;
    }

    /**
     * Used to load a reservation from the database
     * @param reservation
     * @param index
     */
    public void loadReservation(Reservation reservation, int index) {
        reservations.put(new Integer(index), reservation);
    }

    /**
     * Used to add a new reservation during normal use
     * @param reservation
     */
    public void addReservation(Reservation reservation) {
        reservations.put(nextEmptyReservation, reservation);
        Model.getNextEmptyReservation(this, nextEmptyReservation, new IntegerAction() {
            public void integerAction(int num) {
                nextEmptyReservation = num;
                DataLoader.setNextEmptyReservation(nextEmptyReservation, Shelter.this);
            }
        });
    }
}
