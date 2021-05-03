package com.gatech.fourhorse.cs2340Project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Class for Register
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        password = findViewById(R.id.passwdText);
        username = findViewById(R.id.usernameText);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        configureCancelButton();
        configureRegisterButton();
    }
    private void configureCancelButton(){
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void configureRegisterButton(){
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Things We Need To Do:
                //Make sure there is a username and password
                CharSequence sequence1 = username.getText();
                String userString = sequence1.toString();
                CharSequence sequence2 = password.getText();
                String passwordString = sequence2.toString();
                if(!userString.isEmpty()
                        && !passwordString.isEmpty()) {
                    /*
                    //Now we need to send this off to make sure username isn't taken
                    Object object = spinner.getSelectedItem();
                    boolean b = createUser(userString,
                            passwordString,
                            object.toString());
                    if(b) {
                        Toast t = Toast.makeText(getApplicationContext(), "Registered",
                                Toast.LENGTH_SHORT);
                        t.show();
                        finish(); //Go back to start screen
                    } else {
                        Toast t = Toast.makeText(getApplicationContext(), "Username taken",
                                Toast.LENGTH_SHORT);
                        t.show();
                    }*/
                    Object object = spinner.getSelectedItem();
                    String str = object.toString();
                    UserType type;
                    if(str.equalsIgnoreCase(UserType.ADMIN.toString())){
                        type = UserType.ADMIN;
                    } else {
                        type = UserType.GENERAL;
                    }
                    Date date = new Date();

                    Model.createUser(userString,passwordString,"","",date, Gender.NOT_SPECIFIED,type);

                    Toast t = Toast.makeText(getApplicationContext(), "Registered",
                            Toast.LENGTH_SHORT);
                    t.show();
                    finish();
                } else {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Please make a username and password",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    

private boolean createUser(String username, String password, String type)  {
    //System.out.println(username + password + type);
    Context context = getApplicationContext();
    File file = new File(context.getFilesDir(), "Users.txt");
    try{
        if(!file.exists()) {
            if (!file.createNewFile()) {
                return false;
            }
        }
    } catch (Exception ignored){

    }

    return createUser(file, username, password, type);
}


    /**
     * Creates a user
     * @param file the file
     * @param username the username
     * @param password the password
     * @param type the type of the user
     * @return whether the user was created
     */
    public static boolean createUser(File file, String username, String password, String type) {
    try (FileInputStream input = new FileInputStream(file)) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();
        while (line  != null) {
            //.........
            //System.out.println("WOOF" + line);
            String arr[] = line.split("]]]"); //there's a long story behind these braces...
            //System.out.println("arr0 is " + arr[0]);
            if (arr[0].equals(username)) {
                return false;
            }
            line = reader.readLine();
        }
        reader.close();
        input.close();
        //no user found therefore, must be safe to create
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        //PrintWriter pw = new PrintWriter(bw);
        //pw.close();
        bw.write(username + "]]]" + password + "]]]" + type + "\n");
        bw.close();
        fw.close();
        //System.out.println(getApplicationContext().fileList()[0]);
        return true;
    } catch (Exception e) {
        //System.out.println("ERROR");
        return false;
    }
}


}
