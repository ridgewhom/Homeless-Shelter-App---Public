package com.gatech.fourhorse.cs2340Project;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Isabelle Steffens
 */
public class checkReservation {

    @Test
    public void checkReservations() throws Exception {
        ReservationActivity reservation = new ReservationActivity();
        reservation.setTotal_beds(10);
        assertEquals(0, reservation.checkReservations(reservation.getTotal_beds() + 1));
        assertEquals(1, reservation.checkReservations(-1));
        assertEquals(2, reservation.checkReservations(reservation.getTotal_beds() - 1));
    }

}