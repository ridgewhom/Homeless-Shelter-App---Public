package com.gatech.fourhorse.cs2340Project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Class for User To Login
 */
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    private int tries_remaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password = findViewById(R.id.passwdText);
        username = findViewById(R.id.usernameText);
        tries_remaining=3;

        configureCancelButton();
        configureLoginButton();
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
    private void configureLoginButton(){
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text1 = username.getText();
                String user_name = text1.toString();
                CharSequence text2 = password.getText();
                final String pass_word = text2.toString();
                /*String type = login(text1.toString(), text2.toString());
                if(!"".equals(type)){
                    //more inefficiency!
                    Intent intent = new Intent(LoginActivity.this, LoggedInActivity.class);
                    intent.putExtra("username",text1.toString());
                    intent.putExtra("password",text2.toString());
                    intent.putExtra("type",type);

                    startActivity(intent);
                    finish();
                } else {
                    Toast t;
                    t = Toast.makeText(getApplicationContext(), "Wrong Username or Password",
                            Toast.LENGTH_SHORT);
                    t.show();
                }*/
                Model.findUser(user_name, new UserAction() {
                    @Override
                    public void userAction(User user) {
                        if(user.getBanned().equalsIgnoreCase("True")){
                            Toast t;
                            t = Toast.makeText(getApplicationContext(), "That User Is Banned or Locked Out",
                                    Toast.LENGTH_SHORT);
                            t.show();
                        }
                        if(user.getPassword().equals(pass_word)){
                            if(user.getBanned().equalsIgnoreCase("True")){
                                Toast t;
                                t = Toast.makeText(getApplicationContext(), "That User Is Banned or Locked Out",
                                        Toast.LENGTH_SHORT);
                                t.show();
                            } else {
                                final Intent intent = new Intent(LoginActivity.this, LoggedInActivity.class);
                                Model.setUser(user, new ReservationAction() {
                                    @Override
                                    public void reservationAction(Reservation reserve) {
                                            Model.getUser().setReservation(reserve);
                                    }
                                });
                                DataLoader.refreshShelters("0", new ShelterAction() {
                                    @Override
                                    public void shelterAction(Shelter shelter) {
                                        Model.getShelters().add(shelter);
                                    }
                                }, new VoidAction() {
                                    @Override
                                    public void voidAction() {
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }
                        } else {
                            tries_remaining--;
                            Toast t;
                            t = Toast.makeText(getApplicationContext(), "Wrong Password",
                                    Toast.LENGTH_SHORT);
                            t.show();
                            if(tries_remaining<=0){
                                Toast t2;
                                t2 = Toast.makeText(getApplicationContext(), "Tries Exhausted, Account is Locked",
                                        Toast.LENGTH_LONG);
                                t2.show();

                                DataLoader.banUser(user);
                            }
                        }
                    }
                }, new VoidAction() {
                    @Override
                    public void voidAction() {
                        Toast t;
                        t = Toast.makeText(getApplicationContext(), "Wrong Username or Password",
                                Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
            }
        });
    }

    private String login(String name, String password) {
        Context c = getApplicationContext();
        File file = new File(c.getFilesDir(), "Users.txt");
        try {
            if (!file.exists()) {
                if(!file.createNewFile()){
                    return "";
                }
            }
        } catch (Exception ignored) {

        }

        try (FileInputStream input = new FileInputStream(file)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            while (line  != null) {
                //.........
                //System.out.println("WOOF" + line);
                String arr[] = line.split("]]]"); //there's a long story behind these braces...
                //System.out.println("arr0 is " + arr[0]);
                if (arr[0].equals(name)) {
                    if(arr[1].equals(password)){
                        return arr[2]; //returns type to pass on
                    }
                }
                line = reader.readLine();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }
}
