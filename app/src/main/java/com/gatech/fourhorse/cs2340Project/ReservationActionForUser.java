package com.gatech.fourhorse.cs2340Project;

/**
 * Created by nullpo on 4/24/18.
 */

public class  ReservationActionForUser {

    public static void ReservationAction(Reservation reserve){
        Model.getUser().setReservation(reserve);
    }
}
