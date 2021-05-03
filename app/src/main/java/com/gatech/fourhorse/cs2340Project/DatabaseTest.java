package com.gatech.fourhorse.cs2340Project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseTest {
/*
    private void assertTrue(boolean bool) {
        if (!bool) {
            throw new RuntimeException("Not equal");
        }
    }

    public void runTests() {
        configureModel();
    }

    private void configureModel() {
        Model.configure(new VoidAction() {
            @Override
            public void voidAction() {
                assertTrue(13 == Model.numberOfShelters());
                assertTrue(2 == Model.nextEmptyReservation);
                System.out.println("Configuration Tests passed");
                testFindUserReal();
            }
        });
    }

    private void testFindUserReal() {
        Model.findUser("username", new UserAction() {
            @Override
            public void userAction(User user) {
                assertTrue(user.getUsername().equals("username"));
                assertTrue(user.getPassword().equals("password"));
                assertTrue(user.getFirstName().equals("first"));
                assertTrue(user.getLastName().equals("last"));
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date dateOfBirth;
                try {
                    dateOfBirth = dateFormat.parse("01/01/2000");
                } catch (ParseException e) {
                    throw new RuntimeException("Date problems");
                }
                assertTrue(user.getDateOfBirth().equals(dateOfBirth));
                assertTrue(user.getGender().equals(Gender.MALE));
                assertTrue(user.getUserType().equals(UserType.GENERAL));
                System.out.println("Find User Real Passed");
                testFindUserFake();
            }
        }, new VoidAction() {
            @Override
            public void voidAction() {
                throw new RuntimeException("User username not found");
            }
        });
    }

    private void testFindUserFake() {
        Model.findUser("aakjlkdf", new UserAction() {
            @Override
            public void userAction(User user) {
                throw new RuntimeException("User aakjlkdf found");
            }
        }, new VoidAction() {
            @Override
            public void voidAction() {
                System.out.println("Test Find User Fake passed");
                testSetUserNoReservation();
            }
        });
    }

    private void testCreateUser() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date dateOfBirth;
        try {
            dateOfBirth = dateFormat.parse("15/05/2005");
        } catch (ParseException e) {
            throw new RuntimeException("Date problems");
        }
        Model.createUser("TestUsername", "TestPassword", "TestFirstName",
                "TestLastName", dateOfBirth, Gender.NOT_SPECIFIED, UserType.GENERAL);
        System.out.println("Create User tested");
    }

    private void testSetUserNoReservation() {
        Model.findUser("aberchenko", new UserAction() {
            @Override
            public void userAction(final User user) {
                Model.setUser(user, new VoidAction() {
                    @Override
                    public void voidAction() {
                        assertTrue(user.equals(Model.getUser()));
                        assertTrue(Model.getUser().getReservation() == null);
                        System.out.println("Test User No Reservation passed");
                        testUserReservation();
                    }
                });
            }
        }, new VoidAction() {
            @Override
            public void voidAction() {
                throw new RuntimeException("User aberchenko not found");
            }
        });
    }

    public void testUserReservation() {
        Model.findUser("username", new UserAction() {
            @Override
            public void userAction(final User user) {
                Model.setUser(user, new VoidAction() {
                    @Override
                    public void voidAction() {
                        assertTrue(user.equals(Model.getUser()));
                        assertTrue(Model.getUser().getReservation() != null);
                        assertTrue(Model.getUser().getReservation().getBeds() == 5);
                        assertTrue(Model.getUser().getReservation().getReservationIndex() == 1);
                        assertTrue(Model.getUser().getReservation().getShelter().getKey() == 2);
                        System.out.println("Test User Reservation passed");
                    }
                });
            }
        }, new VoidAction() {
            @Override
            public void voidAction() {
                throw new RuntimeException("User username not found");
            }
        });
    }
*/
}
