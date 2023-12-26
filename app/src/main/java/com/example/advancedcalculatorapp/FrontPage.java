package com.example.advancedcalculatorapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity; // Base class for activities

// classes for android widgets/features
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;



public class FrontPage extends AppCompatActivity implements View.OnClickListener {

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {  // Displays the activity and determines widget functionality
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page); // Sets the content to be viewed as the front page activity- location: res/layout/activity_front_page

        Intent perm = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
        startActivity(perm); // Obtain file permissions

        System.out.println(Environment.isExternalStorageManager());

        Button genCalc = (Button) findViewById(R.id.button1); // Instantiates a Button object
        genCalc.setOnClickListener(this); // sets the Onclicklistener for this object

        Button gcseCalc = (Button) findViewById(R.id.button2);
        gcseCalc.setOnClickListener(this);

        Button aLevelCalc = (Button) findViewById(R.id.button3);
        aLevelCalc.setOnClickListener(this);

        Button cameraSolve = (Button) findViewById(R.id.button4);
        cameraSolve.setOnClickListener(this);

        Button graphCalc = (Button) findViewById(R.id.button5);
        graphCalc.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button1:
                Intent intent1 = new Intent(this,GeneralCalc.class); //Intent to change activity to GeneralCalc
                startActivity(intent1); // Starts the activity
                break;
            case R.id.button2:
                Intent intent2 = new Intent(this,gcseCalc.class); //Intent to change activity to GCSECalc
                startActivity(intent2); // Starts the activity
                break;

            case R.id.button3:
                Intent intent3 = new Intent(this,AlevelCalc.class); //Intent to change activity to AlevelCalc
                startActivity(intent3); // Starts the activity
                break;

            case R.id.button4:
                Intent intent4 = new Intent(this,CameraSolve.class); //Intent to change activity to CameraSolve
                startActivity(intent4); // Starts the activity
                break;
            case R.id.button5:
                Intent intent5 = new Intent(this,GraphCalc.class); //Intent to change activity to GraphCalc
                startActivity(intent5); // Starts the activity
                break;


        }
    }
}
