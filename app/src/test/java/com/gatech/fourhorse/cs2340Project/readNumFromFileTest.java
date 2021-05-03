package com.gatech.fourhorse.cs2340Project;

import android.support.annotation.Nullable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;

/**
 * Author: Ridge Ross
 */
public class readNumFromFileTest {




    /**
     *
     * @throws Exception, possible exception on creating files
     */
    @Before
    public void setUp() throws Exception {
        File f1 = new java.io.File(".");
        String current = f1.getCanonicalPath();
        @Nullable File file = new File(current + "test.txt");
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile(); //I don't care about the boolean, only action
    }
    /**
     *
     * @throws Exception possible exception on deleting files
     */
    @After
    public void tearDown() throws Exception {
        File f1 = new java.io.File(".");
        String current = f1.getCanonicalPath();
        @Nullable File file = new File(current + "test.txt");
        //noinspection ResultOfMethodCallIgnored
        file.delete();  //I don't care about the boolean, only action
    }
    /**
     * Actual Tests
     * @throws Exception possible exception on writing or opening files
     */
    @Test
    @SuppressWarnings({"FeatureEnvy"})
    //This is here because of course a test has to call features of class
    public void readNumFromFile() throws Exception {
        //First, confirm that the catch block works
        File f = new File("Doesn't_Exist.txt");
        assertEquals(0,ReservationActivity.readNumFromFile(f, 10));

        //Next, confirm that the if statement of the try block works
        File f1 = new java.io.File(".");
        String current = f1.getCanonicalPath();
        File file = new File(current + "test.txt");
        assertEquals(10,ReservationActivity.readNumFromFile(file, 10));

        //Finally confirm the try block works if it doesn't pass through the if-statement
        FileWriter writer = new FileWriter(file, false);
        final int i = 45;
        String str = Integer.toString(i);
        writer.write(str); writer.flush(); writer.close();
        assertEquals(i,ReservationActivity.readNumFromFile(file, 10));


    }

    // Branch is fully covered due to all blocks of code being ran and tested.
    // If statement is ran -> check
    // If statement is not ran -> check
    // Both of the above will cover the entire try-block
    // Exception is caught and function runs catch block -> check


}