package com.gatech.fourhorse.cs2340Project;

import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Class For Google Maps
 *
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String gender = ""; //= (String)b.get("gender");
        String name= ""; //= (String)b.get("name");
        String age= ""; //= (String)b.get("age");
        if(b!= null){
            gender = (String)b.get("gender");
            name = (String)b.get("name");
            age = (String)b.get("age");
        }
        updateShelterInfo(gender,age,name);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager manager = getSupportFragmentManager();
        SupportMapFragment mapFragment =
                (SupportMapFragment) manager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Context context = getApplicationContext();

        for(Shelter shelter: shelterInfo){

            double lat = shelter.getLatitude();
            double lng = shelter.getLongitude();

            LatLng latlng = new LatLng(lat,lng);
            String str = shelter.getName() + " " + shelter.getPhone();
            MarkerOptions a = new MarkerOptions();
            a = a.position(latlng);
            a = a.title(str);
            googleMap.addMarker(a);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        }



        // Add a marker in Sydney and move the camera

        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    //private final List<ArrayList<String>> shelters2d = new ArrayList<>();
    private final List<Shelter> shelterInfo = new ArrayList<>();
    /**
     *
     * @param gender gender to search
     * @param age   age to search
     * @param name name to search
     */
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
        for (Shelter shelter : Model.getShelters()) {
            if (

                    (checkName || shelter.getName().toLowerCase().contains(name1))
                            && (checkGender || shelter.getRestrictions().contains(gender))
                            && (checkAge || shelter.getRestrictions().contains(age1))
                    ) {
                shelterInfo.add(shelter);
            }
        }
    }
}