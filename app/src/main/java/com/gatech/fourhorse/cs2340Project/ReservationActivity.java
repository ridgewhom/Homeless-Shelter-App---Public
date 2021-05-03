package com.gatech.fourhorse.cs2340Project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that is used to control reserving or canceling
 */
public class ReservationActivity extends AppCompatActivity {

    private int total_beds = 0;
    private String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        //assert b != null;
        //String beds = (String)b.get("beds"); //get capacity
        //id = (String) b.get("id");
        //total_beds = Integer.parseInt(beds);

        TextView tv1 = findViewById(R.id.num_beds);
        String beds = Model.getCurrentShelter().getCapacity();

        if (beds.isEmpty()) {
            beds = "999"; //beds are unknown so we'll use a very high number
        } else if (!beds.matches("[0-9]+")) {
            //If capacity is not just a number, grab all numbers
            //within string and sum them together
            //One specific case of "32 for family, 68 for singles"
            //will be 110 for example
            //Otherwise there has to be a specific activity for a
            //different screen and a lot of mess due to increase of edge
            //cases, for instance how many beds does an apartment count for?
            //and ad infinity
            //so it just assumes all beds are 1 reservation and can be taken by
            //anyone

            Integer sum = 0;

            Pattern pattern = Pattern.compile("(\\d+)\\D+");//digits
            List<String> list = new ArrayList<>();
            Matcher matcher = pattern.matcher(beds); //grab digits
            while (matcher.find()) {
                list.add(matcher.group(1)); //add digits to list
            }
            for (int i = 0; i < list.size(); i++) {
                sum += Integer.parseInt(list.get(i));//sum all entries of list
            }
            beds = sum.toString();
        }
        total_beds = Integer.parseInt(beds);

        tv1.setText(beds);
        //update_vac(id,0);
        configureReserveButton();
        configureCancelButton();
        updateRemaining();
    }
    private void updateRemaining(){
        System.out.println("L02G" + Model.getUser().getReservation() != null + "\n");

        DataLoader.refreshShelter(Integer.toString(Model.getCurrentShelter().getKey()),
                new ShelterAction() {
                    @Override
                    public void shelterAction(Shelter shelter) {
                        Model.setCurrentShelter(shelter);
                        Integer new_value = Model.getCurrentShelter().getCurrentAvailable();
                        TextView text = findViewById(R.id.num_vacancies);
                        text.setText(String.format(Locale.getDefault(),"%d",new_value));
                    }
                });
        //System.out.println((Model.getTmpShelter()!=null) + "L0G");
        //Model.setCurrentShelter(Model.getTmpShelter());
        Integer new_value = Model.getCurrentShelter().getCurrentAvailable();
        TextView text = findViewById(R.id.num_vacancies);
        text.setText(String.format(Locale.getDefault(),"%d",new_value));
    }

    private void configureCancelButton(){
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataLoader.refreshShelter(Integer.toString(Model.getCurrentShelter().getKey()),
                        new ShelterAction() {
                            @Override
                            public void shelterAction(Shelter shelter) {
                                Model.setCurrentShelter(shelter);
                                Integer new_value = Model.getCurrentShelter().getCurrentAvailable();
                                TextView text = findViewById(R.id.num_vacancies);
                                text.setText(String.format(Locale.getDefault(),"%d",new_value));
                                if(Model.getUser().getReservation()!= null){
                                    Model.getUser().getReservation().setShelter(shelter);
                                    Model.reserve(0);
                                    //EditText num_chosen = findViewById(R.id.num_cancel);
                                    //CharSequence sequence1 = num_chosen.getText();
                                    //String string1 = sequence1.toString();
                                    //if (!string1.isEmpty()) {
                                    //    int num = Integer.parseInt(string1);
                                    //    //update_vac(id, num);
                                    //}
                                } else {
                                    Toast t;
                                    t = Toast.makeText(getApplicationContext(), "No Reservation to Cancel",
                                            Toast.LENGTH_SHORT);
                                    t.show();
                                }
                                updateRemaining();
                            }
                        });


            }
        });
    }
    private void configureReserveButton(){
        Button cancelButton = findViewById(R.id.reserve_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText num_chosen = findViewById(R.id.num_reserve);
                CharSequence sequence1 = num_chosen.getText();
                String string1 = sequence1.toString();
                if(!string1.isEmpty()) {
                    final int num = Integer.parseInt(string1);
                    DataLoader.refreshShelter(Integer.toString(Model.getCurrentShelter().getKey()),
                            new ShelterAction() {
                                @Override
                                public void shelterAction(Shelter shelter) {
                                    Model.setCurrentShelter(shelter);
                                    Integer new_value = Model.getCurrentShelter().getCurrentAvailable();
                                    TextView text = findViewById(R.id.num_vacancies);
                                    text.setText(String.format(Locale.getDefault(),"%d",new_value));
                                    if(Model.getUser().getReservation()!= null){
                                        Model.getUser().getReservation().setShelter(shelter);
                                    }
                                    if(Model.getUser().getReservation()!= null){
                                        if(Model.getUser().getReservation().getShelter().getKey()==Model.getCurrentShelter().getKey()) {
                                            if (Model.getCurrentShelter().getCurrentAvailable() +
                                                    Model.getUser().getReservation().getBeds() - num < 0) {
                                                Toast t;
                                                t = Toast.makeText(getApplicationContext(), "Attempted to Reserve More slots than available",
                                                        Toast.LENGTH_SHORT);
                                                t.show();
                                            } else {
                                                Model.reserve(num);
                                            }
                                        } else {
                                            Toast t;
                                            t = Toast.makeText(getApplicationContext(), "Please cancel your existing reservation, or select the original shelter you reserved",
                                                    Toast.LENGTH_SHORT);
                                            t.show();
                                        }
                                    } else {
                                        if(Model.getCurrentShelter().getCurrentAvailable() - num < 0){
                                            Toast t;
                                            t = Toast.makeText(getApplicationContext(), "Attempted to Reserve More slots than available",
                                                    Toast.LENGTH_SHORT);
                                            t.show();
                                        } else {
                                            Model.reserve(num);
                                        }
                                    }
                                    //update_vac(id, num * -1);
                                    //negative because vacancies will be removed; 0
                                    updateRemaining();
                                }

                                });
                            }

            }
        });
    }
    /*
    /**you can also update this with num = 0, to reload the field
     *
     * @param id of shelter
     * @param num number to change remaining vacancies by
     */

    /*
    private void update_vac(String id, Integer num)  {
        Context context = getApplicationContext();
        File file = new File(context.getFilesDir(), id + ".txt");
        try {
            if (!file.exists()){
                if(file.createNewFile()) {
                    FileWriter writer = new FileWriter(file, false);
                    String str = Integer.toString(total_beds);
                    writer.write(str); writer.flush(); writer.close();
                }
            }
                int a = readNumFromFile(file,total_beds);
                Integer new_value = a + num;
                //get integer from line and modify it by num
                String new_val_str = Integer.toString(new_value);
                //convert back to string because binary files
                Toast t;
                int check = checkReservations(new_value);
                if (check == 0) {
                    t = Toast.makeText(getBaseContext(),
                        "Canceled too many reservations",
                        Toast.LENGTH_LONG);
                    t.show();
                } else if (check == 1) {
                    t = Toast.makeText(getBaseContext(),
                            "Not enough vacancies to satisfy request",
                            Toast.LENGTH_LONG);
                    t.show();
                }
                if((new_value > total_beds) || (new_value < 0)) {return;}
                FileWriter fw = new FileWriter(file, false);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(new_val_str); bw.close(); fw.close();
                TextView text = findViewById(R.id.num_vacancies);
                text.setText(String.format(Locale.getDefault(),"%d",new_value));
        } catch (Exception ignored) {}
    }

    /**
     * check if there is space for new reservations
     */
    public int checkReservations (Integer new_value) {
        if(new_value > total_beds){
            return 0;
        } else if (new_value < 0) {
            return 1;
        }
        return 2;
    }

    /**
     *
     * @param file file to read
     * @param totalBeds value to default if file is empty
     * @return default value or value in file
     */
    public static int readNumFromFile(File file, int totalBeds){
        try {
            FileInputStream input = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            if (line == null) {
                line = Integer.toString(totalBeds);
            }
            reader.close();
            input.close();
            return Integer.parseInt(line.trim());
        } catch (Exception e){
            return 0;
        }
    }
    /**
     * getter total beds
     */
    public int getTotal_beds() {
        return total_beds;
    }

    /**
     * set total beds
     */
    public void setTotal_beds(int total) {
        total_beds = total;
    }
}