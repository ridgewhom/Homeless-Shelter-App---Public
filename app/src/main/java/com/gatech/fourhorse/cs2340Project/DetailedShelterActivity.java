package com.gatech.fourhorse.cs2340Project;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that is used after a user selects a shelter to get more info of.
 */
public class DetailedShelterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_shelter);


        Intent i = getIntent();
        Bundle b = i.getExtras();
        //System.out.println("HIYA" + b.get("name") + b.get("id"));
        //uncomment if you want some console debugging
        ListView lv = findViewById(R.id.list_view);


        assert b != null;
        //readShelterData((String)b.get("name")); //was id before
        readShelterData();
        ListAdapter adapter = new DoubleStringAdapter(this, shelterInfo);
        lv.setAdapter(adapter);

        configureMakeCancelButton();
    }


    /**
     * law of demeter stuff
     * @param string a double string that you want to grab info from
     * @return second string of the double string
     */
    private String stringGetter(GenericDoubleString string){
        return string.getStr2();
    }

    /**
     * method to configure the cancel button
     */
    private void  configureMakeCancelButton(){
        Button loginButton = findViewById(R.id.make_cancel_button);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Intent intent = new Intent(DetailedShelterActivity.this, ReservationActivity.class);
                startActivity(intent);
                //finish();
            }
        }) ;
    }











    private final ArrayList<GenericDoubleString> shelterInfo = new ArrayList<>();


    private void readShelterData(){
        Shelter shelter = Model.getCurrentShelter();

        GenericDoubleString str = new GenericDoubleString("Unique Key",Integer.toString(Model.getCurrentShelter().getKey()));
        shelterInfo.add(str);
        
        str = new GenericDoubleString("Shelter Name",shelter.getName());
        shelterInfo.add(str);
        
        str = new GenericDoubleString("Capacity",shelter.getCapacity());
        shelterInfo.add(str);

        str = new GenericDoubleString("Restrictions",shelter.getRestrictions());
        shelterInfo.add(str);
        
        str = new GenericDoubleString("Longitude",Double.toString(shelter.getLongitude()));
        shelterInfo.add(str);

        str = new GenericDoubleString("Latitude",Double.toString(shelter.getLatitude()));
        shelterInfo.add(str);
        
        str = new GenericDoubleString("Address",shelter.getAddress());
        shelterInfo.add(str);

        str = new GenericDoubleString("Special Notes",shelter.getNotes());
        shelterInfo.add(str);
        
        str = new GenericDoubleString("Phone Number",shelter.getPhone());
        shelterInfo.add(str);

    }

}
