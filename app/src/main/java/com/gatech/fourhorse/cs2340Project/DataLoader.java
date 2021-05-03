package com.gatech.fourhorse.cs2340Project;

import android.util.Log;

import com.google.firebase.database.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataLoader {
    private static DatabaseReference mDatabase;

    public static void start() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public static void refreshShelters(final String key, final ShelterAction action, final VoidAction completionAction) {
        if(key.equalsIgnoreCase("0")){
            Model.getShelters().clear();
        }
        DatabaseReference shelterRef = mDatabase.child("Shelters").child(key);
        shelterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("Shelter Name").getValue(String.class);
                    String capacity = snapshot.child("Capacity").getValue(String.class);
                    int numericCapacity = Integer.parseInt(snapshot
                            .child("Numeric Capacity").getValue(String.class));
                    int available = Integer.parseInt(snapshot.child("Available")
                            .getValue(String.class));
                    String restrictions = snapshot.child("Restrictions")
                            .getValue(String.class);
                    double longitude = Double.parseDouble(snapshot.child("Longitude")
                            .getValue(String.class));
                    double latitude = Double.parseDouble(snapshot.child("Latitude")
                            .getValue(String.class));
                    String address = snapshot.child("Address").getValue(String.class);
                    String notes = snapshot.child("Special Notes").getValue(String.class);
                    String phone = snapshot.child("Phone Number").getValue(String.class);
                    int nextEmptyReservation = Integer.parseInt(snapshot
                            .child("Next Empty Reservation").getValue(String.class));
                    Shelter shelter = new Shelter(Integer.parseInt(key), name, capacity, numericCapacity, available,
                            restrictions, longitude, latitude, address, notes, phone,
                            nextEmptyReservation);
                    action.shelterAction(shelter);
                    int i = Integer.parseInt(key);
                    i+=1;
                    String str = Integer.toString(i);
                    refreshShelters(str,action,completionAction);
                } else {
                    completionAction.voidAction();
                }

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("onCancelled", " cancelled");
            }
        });
    }


    public static void refreshShelter(final String key, final ShelterAction action) {
        DatabaseReference shelterRef = mDatabase.child("Shelters").child(key);
        shelterRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String name = snapshot.child("Shelter Name").getValue(String.class);
                String capacity = snapshot.child("Capacity").getValue(String.class);
                int numericCapacity = Integer.parseInt(snapshot
                        .child("Numeric Capacity").getValue(String.class));
                int available = Integer.parseInt(snapshot.child("Available")
                        .getValue(String.class));
                String restrictions = snapshot.child("Restrictions")
                        .getValue(String.class);
                double longitude = Double.parseDouble(snapshot.child("Longitude")
                        .getValue(String.class));
                double latitude = Double.parseDouble(snapshot.child("Latitude")
                        .getValue(String.class));
                String address = snapshot.child("Address").getValue(String.class);
                String notes = snapshot.child("Special Notes").getValue(String.class);
                String phone = snapshot.child("Phone Number").getValue(String.class);
                int nextEmptyReservation = Integer.parseInt(snapshot
                        .child("Next Empty Reservation").getValue(String.class));
                Shelter shelter = new Shelter(Integer.parseInt(key), name, capacity, numericCapacity, available,
                        restrictions, longitude, latitude, address, notes, phone,
                        nextEmptyReservation);
                action.shelterAction(shelter);
                System.out.println("L0G" + shelter!=null +"\n");
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("onCancelled", " cancelled");
            }
        });

    }
               
    



    /**
     * Attempts to find user with username username. If successful, performs successAction with the
     * found user, otherwise performs failureAction.
     */
    public static void findUser(final String username, final UserAction successAction,
                                final VoidAction failureAction) {
        DatabaseReference userRef = mDatabase.child("Users").child(username);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = dataSnapshot.child("Password").getValue(String.class);
                    String firstName = dataSnapshot.child("First Name").getValue(String.class);
                    String lastName = dataSnapshot.child("Last Name").getValue(String.class);
                    String genderString = dataSnapshot.child("Gender").getValue(String.class);
                    Gender gender = Gender.getGender(genderString);
                    String userString = dataSnapshot.child("User Type").getValue(String.class);
                    UserType userType = UserType.getUserType(userString);
                    String dateString = dataSnapshot.child("Date of Birth").getValue(String.class);
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    String banned = dataSnapshot.child("Banned").getValue(String.class);
                    Date dateOfBirth;
                    try {
                        dateOfBirth = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        failureAction.voidAction();
                        return;
                    }

                    User user = new User(username, password, firstName, lastName, dateOfBirth,
                            gender, userType, banned);
                    successAction.userAction(user);

                } else {
                    failureAction.voidAction();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                failureAction.voidAction();
            }
        });
    }

    public static void loadShelters(VoidAction completionAction) {
        loadAShelter(0, completionAction);
    }

    private static void loadAShelter(final int index, final VoidAction completionAction) {
        DatabaseReference shelterRef = mDatabase.child("Shelters").child(Integer.toString(index));
        ValueEventListener shelterListener = new ValueEventListener() {
            boolean start = true;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                // ...
                if (start) {
                    start = false;
                    if (dataSnapshot.exists()) {
                        int key = index;
                        String name = dataSnapshot.child("Shelter Name").getValue(String.class);
                        String capacity = dataSnapshot.child("Capacity").getValue(String.class);
                        int numericCapacity = Integer.parseInt(dataSnapshot
                                .child("Numeric Capacity").getValue(String.class));
                        int available = Integer.parseInt(dataSnapshot.child("Available")
                                .getValue(String.class));
                        String restrictions = dataSnapshot.child("Restrictions")
                                .getValue(String.class);
                        double longitude = Double.parseDouble(dataSnapshot.child("Longitude").
                                getValue(String.class));
                        double latitude = Double.parseDouble(dataSnapshot.child("Latitude").
                                getValue(String.class));
                        String address = dataSnapshot.child("Address").getValue(String.class);
                        String notes = dataSnapshot.child("Special Notes").getValue(String.class);
                        String phone = dataSnapshot.child("Phone Number").getValue(String.class);
                        int nextEmptyReservation = Integer.parseInt(dataSnapshot
                                .child("Next Empty Reservation").getValue(String.class));
                        Shelter shelter = new Shelter(key, name, capacity, numericCapacity, available,
                                restrictions, longitude, latitude, address, notes, phone,
                                nextEmptyReservation);
                        Model.addShelter(shelter);
                        loadAShelter(index + 1, completionAction);
                    } else {
                        Model.configured = true;
                        completionAction.voidAction();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Shelter failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
                System.out.println("Database loading failed: "
                        + databaseError.toException().getLocalizedMessage());
            }
        };
        shelterRef.addListenerForSingleValueEvent(shelterListener);
    }


    public static void banUser(User user){
        mDatabase.child("Users").child(user.getUsername()).child("Banned").setValue("True");
        mDatabase.child("Users").child(user.getUsername()).child("New Password")
                .setValue(user.getPassword());
    }
    public static void unBanUser(User user){
        mDatabase.child("Users").child(user.getUsername()).child("Banned").setValue("False");
        mDatabase.child("Users").child(user.getUsername()).child("New Password").setValue("");
    }

    public static void saveUser(User user) {
        mDatabase.child("Users").child(user.getUsername()).child("Password")
                .setValue(user.getPassword());
        mDatabase.child("Users").child(user.getUsername()).child("First Name")
                .setValue(user.getFirstName());
        mDatabase.child("Users").child(user.getUsername()).child("Last Name")
                .setValue(user.getLastName());
        mDatabase.child("Users").child(user.getUsername()).child("Gender")
                .setValue(user.getGender().toString());
        mDatabase.child("Users").child(user.getUsername()).child("User Type")
                .setValue(user.getUserType().toString());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        mDatabase.child("Users").child(user.getUsername()).child("Date of Birth")
                .setValue(dateFormat.format(user.getDateOfBirth()));
        mDatabase.child("Users").child(user.getUsername()).child("Banned")
                .setValue(user.getBanned());
        if(user.getReservation()!= null) {
            mDatabase.child("Users").child(user.getUsername()).child("Reservation")
                    .setValue(user.getReservation().getReservationIndex());
        } else {
            mDatabase.child("Users").child(user.getUsername()).child("Reservation").setValue("-1");
        }
    }

    /**
     * Loads the reservation belonging to user from the database into the user object
     * @param user the user
     * @param completionAction called upon completion, regardless of success
     */
    public static void userLoadReservation(final User user, final ReservationAction completionAction) {
        mDatabase.child("Users").child(user.getUsername()).child("Reservation")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String indexString = dataSnapshot.getValue(String.class);
                            try {
                                int index = Integer.parseInt(indexString);
                                userLoadReservation(user, index, completionAction);
                            } catch (NumberFormatException e) {
                                completionAction.reservationAction(null);
                            }
                        } else {
                            completionAction.reservationAction(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        completionAction.reservationAction(null);
                    }
                });
    }

    private static void userLoadReservation(final User user, final int index, final ReservationAction completionAction) {
        mDatabase.child("Reservations").child("" + index)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String bedsString = dataSnapshot.child("Beds").getValue(String.class);
                            int beds = Integer.parseInt(bedsString);
                            String shelterIndexString = dataSnapshot.child("Shelter")
                                    .getValue(String.class);
                            int shelterIndex = Integer.parseInt(shelterIndexString);
                            Shelter shelter = Model.getShelter(shelterIndex);
                            String reservationShelterIndexString = dataSnapshot
                                    .child("Reservation Shelter Index").getValue(String.class);
                            int reservationShelterIndex = Integer
                                    .parseInt(reservationShelterIndexString);

                            Reservation res = new Reservation(index, user, shelter, reservationShelterIndex, beds);
                            completionAction.reservationAction(res);
                        } else {
                            completionAction.reservationAction(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        completionAction.reservationAction(null);
                    }
                });
    }

    public static void createReservation(Reservation reservation) {
        mDatabase.child("Reservations").child("" + reservation.getReservationIndex()).child("Beds")
                .setValue("" + reservation.getBeds());
        mDatabase.child("Reservations").child("" + reservation.getReservationIndex())
                .child("Reservation Shelter Index").setValue("" + reservation.getShelterIndex());
        mDatabase.child("Reservations").child("" + reservation.getReservationIndex())
                .child("Shelter").setValue("" + reservation.getShelter().getKey());
        mDatabase.child("Reservations").child("" + reservation.getReservationIndex())
                .child("User").setValue(reservation.getUser().getUsername());
        mDatabase.child("Users").child(reservation.getUser().getUsername()).child("Reservation")
                .setValue("" + reservation.getReservationIndex());
        mDatabase.child("Shelters").child("" + reservation.getShelter().getKey())
                .child("Reservations").child("" + reservation.getShelterIndex())
                .setValue("" + reservation.getReservationIndex());
        mDatabase.child("Shelters").child("" + reservation.getShelter().getKey())
                .child("Available").setValue("" + reservation.getShelter().getCurrentAvailable());
    }

    public static void modifyReservation(Reservation reservation) {
        mDatabase.child("Reservations").child("" + reservation.getReservationIndex()).child("Beds")
                .setValue("" + reservation.getBeds());
        mDatabase.child("Shelters").child("" + reservation.getShelter().getKey()).child("Available")
                .setValue("" + reservation.getShelter().getCurrentAvailable());
    }

    public static void deleteReservation(Reservation reservation) {
        mDatabase.child("Users").child(reservation.getUser().getUsername()).child("Reservation")
                .setValue("-1");
        mDatabase.child("Shelters").child("" + reservation.getShelter().getKey()).child("Reservations")
                .child("" + reservation.getShelterIndex()).setValue("-1");
        mDatabase.child("Shelters").child("" + reservation.getShelter().getKey())
                .child("Next Empty Reservation")
                .setValue("" + reservation.getShelter().getNextEmptyReservation());
        mDatabase.child("Shelters").child("" + reservation.getShelter().getKey()).child("Available")
                .setValue("" + reservation.getShelter().getCurrentAvailable());
        mDatabase.child("Reservations").child("" + reservation.getReservationIndex())
                .setValue("Deleted");
    }

    public static void setNextEmptyReservation(int nextEmptyReservation) {
        mDatabase.child("Next Empty Reservation").setValue("" + nextEmptyReservation);
    }

    /**
     * Gets the next empty reservation from the global reservation list
     * @param previousEmpty the previous empty reservation. -1 if simply reading the last value.
     *                      Otherwise the next empty reservation will be searched for and found
     * @param completionAction the action performed on completion. -1 is the input if not found
     */
    public static void getNextEmptyReservation(final int previousEmpty, final IntegerAction completionAction) {
        if (previousEmpty == -1) {
            mDatabase.child("Next Empty Reservation")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String indexString = dataSnapshot.getValue(String.class);
                        int index = Integer.parseInt(indexString);
                        completionAction.integerAction(index);
                    } else {
                        completionAction.integerAction(-1);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    completionAction.integerAction(-1);
                }
            });
        } else {
            mDatabase.child("Reservations").child("" + previousEmpty)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        try {
                            // Is a String
                            String isAString = dataSnapshot.getValue(String.class);
                            completionAction.integerAction(previousEmpty);
                        } catch (DatabaseException e) {
                            // Not a String
                            getNextEmptyReservation(previousEmpty + 1, completionAction);
                        }
                    } else {
                        completionAction.integerAction(previousEmpty);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    completionAction.integerAction(-1);
                }
            });
        }
    }

    public static void setNextEmptyReservation(int nextEmptyReservation, Shelter shelter) {
        mDatabase.child("Shelters").child("" + shelter.getKey()).child("Next Empty Reservation")
                .setValue("" + nextEmptyReservation);
    }

    public static void getNextEmptyReservation(final Shelter shelter, final int previousEmpty,
                                               final IntegerAction completionAction) {
        mDatabase.child("Shelters").child("" + shelter.getKey()).child("Reservations")
                .child("" + previousEmpty).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String indexString = dataSnapshot.getValue(String.class);
                    if (indexString == "-1") {
                        completionAction.integerAction(previousEmpty);
                    } else {
                        getNextEmptyReservation(shelter, previousEmpty + 1, completionAction);
                    }
                } else {
                    completionAction.integerAction(previousEmpty);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                completionAction.integerAction(-1);
            }
        });
    }

}
