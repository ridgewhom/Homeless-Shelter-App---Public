package com.gatech.fourhorse.cs2340Project;


class GenericDoubleString {

    private String str1;
    private String str2 = "";

    public GenericDoubleString(String str1) {
        this.str1 = str1;
    }

    public GenericDoubleString(String str1, String str2){
        this.str1=str1;
        this.str2=str2;
    }
    /**
     * Gets str1
     * @return str1
     */
    public String getStr1() {
        return str1;
    }

    /**
     * Gets str2
     * @return str2
     */
    public String getStr2() {
        return str2;
    }

    /**
     * Sets str2
     * @param str2 the value str2 is set to
     */
    public void setStr2(String str2) {
        this.str2 = str2;
    }


}
