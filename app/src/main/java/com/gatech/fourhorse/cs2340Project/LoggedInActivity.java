package com.gatech.fourhorse.cs2340Project;


import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that is used immediately after the user logs in
 */
public class LoggedInActivity extends AppCompatActivity {

    private Spinner gender_spinner;
    private Spinner ages_spinner;
    private EditText searchText;

    private ListView lv;

    private String gender;
    private String name;
    private String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        System.out.println("L0G " + Model.getUser().getReservation());
        //get extra
        Intent i = getIntent();
        Bundle b = i.getExtras();

        //assert b != null;
        //CharSequence username = (String)b.get("username");
        //CharSequence type = (String)b.get("type");
        String username = Model.getUser().getUsername();
        String type = Model.getUser().getUserType().toString();


        TextView tv1 = findViewById(R.id.show_user);
        tv1.setText(username); //change this to current user username
        TextView tv2 = findViewById(R.id.show_type);
        tv2.setText(type); //change text view to current type
        lv = findViewById(R.id.listView);

        searchText = findViewById(R.id.search_text);


        gender_spinner = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> genders = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender_spinner.setAdapter(genders);


        ages_spinner = findViewById(R.id.ages_spinner);
        ArrayAdapter<CharSequence> ages = ArrayAdapter.createFromResource(this,
                R.array.ages, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        ages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ages_spinner.setAdapter(ages);

        readShelterData();



        ListAdapter arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                shelterInfo);

        lv.setAdapter(arrayAdapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence v = ((TextView)view).getText();
                String item = v.toString();



                final Intent intent = new Intent(LoggedInActivity.this,
                        DetailedShelterActivity.class);
                intent.putExtra("name",item);
                for (Shelter shelter : Model.getShelters()){
                    if(shelter.getName().contains(item)){
                        Model.setCurrentShelter(shelter);
                        break;
                    }
                }
                DataLoader.refreshShelters("0", new ShelterAction() {
                    @Override
                    public void shelterAction(Shelter shelter) {
                        Model.getShelters().add(shelter);
                    }
                }, new VoidAction() {
                    @Override
                    public void voidAction() {
                        startActivity(intent);
                        //finish();
                    }
                });




            }
        });

        configureLogoutButton();
        configureClearButton();
        configureSearchButton();
        configureMapsButton();
        configureCommandsButton();

    }
    private void configureCommandsButton(){
        Button button = findViewById(R.id.command_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getUser().getUserType()==UserType.ADMIN){
                    Intent intent = new Intent(LoggedInActivity.this, CommandsActivity.class);
                    startActivity(intent);
                } else {
                    Toast t;
                    t = Toast.makeText(getApplicationContext(), "You are not an admin",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    private void configureMapsButton(){
        Button mapButton = findViewById(R.id.search_map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoggedInActivity.this, MapsActivity.class);
                Object item = gender_spinner.getSelectedItem();
                String string1 = item.toString();
                string1 = string1.trim();
                gender = string1;
                Object item2 = ages_spinner.getSelectedItem();
                String string2 = item2.toString();
                string2 = string2.trim();
                age = string2;
                CharSequence sequence = searchText.getText();
                name = sequence.toString();
                if("Male".equalsIgnoreCase(gender)) {
                    gender = "Men";
                }
                if("Female".equals(gender)) {
                    gender = "Women";
                }
                if("Families with newborns".equals(age)) {
                    age = "newborns";
                }
                intent.putExtra("gender",gender);
                intent.putExtra("age",age);
                intent.putExtra("name",name);
                startActivity(intent);

            }
        });
    }

    private final List<String> shelterInfo = new ArrayList<>();
    private void readShelterData() {
        int num = Model.numberOfShelters();
        for( Shelter shelter : Model.getShelters()){
            shelterInfo.add(shelter.getName());
        }


    }

    private void updateShelterInfo(String gender, String age, String name) {
        String age1 = age;
        String name1 = name;
        shelterInfo.clear();

        boolean checkGender = gender.contains("Any");
        boolean checkAge = age1.contains("Any");
        boolean checkName = name1.isEmpty();
        //gender = gender.toLowerCase();
        age1 = age1.toLowerCase();
        name1 = name1.toLowerCase();
        for( Shelter shelter : Model.getShelters()){
            if (

                    (checkName || shelter.getName().toLowerCase().contains(name1))
                            && (checkGender || shelter.getRestrictions().contains(gender))
                            && (checkAge || shelter.getRestrictions().contains(age1))
                    ) {
                shelterInfo.add(shelter.getName());
            }
        }

        lv.invalidateViews();
    }

    private void configureLogoutButton(){
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureSearchButton(){
        Button searchButton = findViewById(R.id.search_button);



        //some case where we don't have easy matching

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object item = gender_spinner.getSelectedItem();
                String string1 = item.toString();
                string1 = string1.trim();
                gender = string1;
                Object item2 = ages_spinner.getSelectedItem();
                String string2 = item2.toString();
                string2 = string2.trim();
                age = string2;
                CharSequence sequence = searchText.getText();
                name = sequence.toString();
                if("Male".equalsIgnoreCase(gender)) {
                    gender = "Men";
                }
                if("Female".equals(gender)) {
                    gender = "Women";
                }
                if("Families with newborns".equals(age)) {
                    age = "newborns";
                }
                //System.out.println("gender,age,name" + gender + "," + age + "," + name);
                updateShelterInfo(gender,age,name);

            }
        });

    }

    private void configureClearButton(){
        Button clearButton = findViewById(R.id.clear_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender_spinner.setSelection(0);
                ages_spinner.setSelection(0);
                searchText.setText("");
            }
        });
    }
}

