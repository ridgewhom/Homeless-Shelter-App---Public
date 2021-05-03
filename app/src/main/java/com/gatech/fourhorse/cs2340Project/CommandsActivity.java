package com.gatech.fourhorse.cs2340Project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CommandsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);

        configureBanButton();
        configureUnBanButton();
    }


    private void  configureBanButton(){
        Button loginButton = findViewById(R.id.ban_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                EditText username = findViewById(R.id.ban_text);
                CharSequence text1 = username.getText();
                String user_name = text1.toString();
                if(user_name.trim().isEmpty()){
                    Toast t;
                    t = Toast.makeText(getApplicationContext(), "Username is empty",
                            Toast.LENGTH_SHORT);
                    t.show();
                } else {

                Model.findUser(user_name, new UserAction() {
                    @Override
                    public void userAction(User user) {
                        DataLoader.banUser(user);
                        Toast t;
                        t = Toast.makeText(getApplicationContext(), user.getUsername() + " is Banned",
                                Toast.LENGTH_SHORT);
                        t.show();
                    }
                }, new VoidAction() {
                    @Override
                    public void voidAction() {
                        Toast t;
                        t = Toast.makeText(getApplicationContext(), "User Doesn't Exist",
                                Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
                }
            }
        }) ;

    }

    private void  configureUnBanButton(){
        Button loginButton = findViewById(R.id.unban_Button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                EditText username = findViewById(R.id.ban_text);
                CharSequence text1 = username.getText();
                String user_name = text1.toString();
                if(user_name.trim().isEmpty()){
                    Toast t;
                    t = Toast.makeText(getApplicationContext(), "Username is empty",
                            Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    Model.findUser(user_name, new UserAction() {
                        @Override
                        public void userAction(User user) {
                            DataLoader.unBanUser(user);
                            Toast t;
                            t = Toast.makeText(getApplicationContext(), user.getUsername() + " is unbanned",
                                    Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }, new VoidAction() {
                        @Override
                        public void voidAction() {
                            Toast t;
                            t = Toast.makeText(getApplicationContext(), "User Doesn't Exist",
                                    Toast.LENGTH_SHORT);
                            t.show();
                        }
                    });
                }
            }
        }) ;
    }
}
