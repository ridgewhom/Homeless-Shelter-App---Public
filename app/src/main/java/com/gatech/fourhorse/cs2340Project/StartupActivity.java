package com.gatech.fourhorse.cs2340Project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Class used on app startup
 */
public class StartupActivity extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        image = (ImageView) findViewById(R.id.shelter_img);
        imageAnimation();

        //(new DatabaseTest()).runTests(); comment model.configure if you want to test
        Model.configure(new VoidAction() {
            @Override
            public void voidAction() {

            }
        });

        configureLoginButton();
        configureRegisterButton();
    }
    private void  configureLoginButton(){
        Button loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(StartupActivity.this, LoginActivity.class));
            }
        }) ;
    }
    private void  configureRegisterButton(){
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(StartupActivity.this, RegisterActivity.class));
            }
        }) ;
    }
    public void imageAnimation() {
        Animation img = new AlphaAnimation(1.00f, 0.00f);
        img.setDuration(5000);
        img.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                image.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        image.startAnimation(img);
    }
}
