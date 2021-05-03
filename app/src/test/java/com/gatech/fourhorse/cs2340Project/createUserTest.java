package com.gatech.fourhorse.cs2340Project;

import android.content.Context;
import android.support.annotation.Nullable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Author: Amiel Berchenko
 */
public class createUserTest {

    private File userFile;

    /**
     * Sets up the test by ensuring the file does not exist to start
     */
    @Before
    public void setUp() {
        userFile = new File("Users.txt");
        try{
            if(userFile.exists()) {
                if (!userFile.delete()) {
                    fail();
                }
            }
        } catch (Exception ignored){
            fail();
        }
    }

    /**
     * Tears down the test by deleting the file
     */
    @After
    public void tearDown() {
        try{
            if(userFile.exists()) {
                if (!userFile.delete()) {
                    fail();
                }
            }
        } catch (Exception ignored){
            fail();
        }
    }

    /**
     * Tests the createUser method in RegisterActivity
     * Tests for:
     *  - No file case
     *  - Empty file case
     *  - Used file case
     *  - Duplicate user case
     */
    @Test
    public void testCreateUser() {
        // Test no file
        assertEquals(false, RegisterActivity.createUser(userFile, "username",
                "password", "admin"));
        assertFalse(userFile.exists());

        // Test empty file
        try {
            if (!userFile.createNewFile()) {
                fail();
            }
        } catch (IOException e) {
            fail();
        }
        assertEquals(true, RegisterActivity.createUser(userFile, "username",
                "password", "admin"));
        try {
            FileInputStream input = new FileInputStream(userFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            assertEquals("username]]]password]]]admin", line);
            reader.close();
            input.close();
        } catch (IOException e){
            fail();
        }

        // Test file with one line
        assertEquals(true, RegisterActivity.createUser(userFile, "username2",
                "password2", "general"));
        try {
            FileInputStream input = new FileInputStream(userFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            assertEquals("username]]]password]]]admin", line);
            line = reader.readLine();
            assertEquals("username2]]]password2]]]general", line);
            reader.close();
            input.close();
        } catch (IOException e){
            fail();
        }

        // Tries to create duplicate user
        assertEquals(false, RegisterActivity.createUser(userFile, "username2",
                "password2", "general"));
        try {
            FileInputStream input = new FileInputStream(userFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            assertEquals("username]]]password]]]admin", line);
            line = reader.readLine();
            assertEquals("username2]]]password2]]]general", line);
            reader.close();
            input.close();
        } catch (IOException e){
            fail();
        }

    }

}