package com.gatech.fourhorse.cs2340Project;

public enum Gender {
    MALE ("Male"),
    FEMALE ("Female"),
    NOT_SPECIFIED ("Not Specified");

    private String printVersion;

    Gender(String printVersion) {
        this.printVersion = printVersion;
    }

    public static Gender getGender(String printVersion) {
        switch (printVersion) {
            case "Male": return Gender.MALE;
            case "Female": return Gender.FEMALE;
            case "Not Specified": return Gender.NOT_SPECIFIED;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return printVersion;
    }
}
