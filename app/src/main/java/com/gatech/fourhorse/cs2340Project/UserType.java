package com.gatech.fourhorse.cs2340Project;

public enum UserType {
    GENERAL ("General"),
    ADMIN ("Admin"),
    SHELTER_EMPLOYEE ("Shelter Employee");

    private String printVersion;

    UserType(String printVersion) {
        this.printVersion = printVersion;
    }

    public static UserType getUserType(String printVersion) {
        switch (printVersion) {
            case "General": return UserType.GENERAL;
            case "Admin": return UserType.ADMIN;
            case "Shelter Employee": return UserType.SHELTER_EMPLOYEE;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return printVersion;
    }
}
