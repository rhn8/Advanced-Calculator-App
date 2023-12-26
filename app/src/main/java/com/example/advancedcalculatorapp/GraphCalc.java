package com.example.advancedcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint; // Graphics imports for drawing



import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView; // android utility imports


import com.google.android.material.button.MaterialButton; // Button widget

import org.mariuszgromada.math.mxparser.*; // String expression parser


public class GraphCalc extends AppCompatActivity implements View.OnClickListener {

    // Stores the image view data
    private ImageView img;
    // The Canvas object stores information on what to draw
    // onto its associated bitmap.
    private Canvas mCanvas;

    // The Paint object stores how to draw.
    private Paint mPaint = new Paint();

    // The bitmap represents the pixels that will be displayed.
    private Bitmap mBitmap;

    EditText inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_calc);

        img = (ImageView)findViewById(R.id.img);
        // sets imageView

        MaterialButton sketch = findViewById(R.id.sketch);
        sketch.setOnClickListener(this);
        // Instantiate sketch button

        inp = findViewById(R.id.funcinp);
        // sets EditText


        // Set properties of the Paint used to draw on the canvas.
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);

    }


        public void drawfunc(View view,String func) { //Draws function on canvas

        int vWidth = view.getWidth();
        int vHeight = view.getHeight(); // Obtain width and height of imageView

        Function f = new Function("f",func,"x");
        // Function instance to obtain y coordinates

        mBitmap = Bitmap.createBitmap(
                vWidth, vHeight, Bitmap.Config.ARGB_8888);
        // Creates bitmap using the imageView dimensions

        mCanvas = new Canvas(mBitmap);

        mCanvas.drawLine(vWidth/2,0,vWidth/2,vHeight,mPaint);
        mCanvas.drawLine(0,vHeight/2,vWidth,vHeight/2,mPaint);
        // Draw coordinate axes


        for (int i =0; i<view.getWidth(); i++){

             float shift = (float)(0.01*(i - view.getWidth()/2));
            // translates function to fit the canvas

            Expression e = new Expression("-100f(" + shift + ")",f);

            e.checkSyntax(); // Error checking

            if (e.getErrorMessage().contains("Errors have been found.")){ // Breaks for loop if error is found
                inp.setText("Enter a valid function");
                break;
            }

            // calculate ycoord
            double ycoord = e.calculate();

            mCanvas.drawPoint(i , (float) ycoord + view.getHeight()/2,mPaint);
        }
        img.setImageBitmap(mBitmap); // Sets the bitmap image as the imageView Bitmap

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.sketch:
                String func = inp.getText().toString(); // Obtains the string input

                try { // Try catch, in case of invalid inputs
                    drawfunc(img, func);
                }
                catch (Exception e){
                    System.out.println(e);
                    inp.setText("Enter a valid function");
                }
                break;
            case R.id.graphcalc_exit:
                Intent intent = new Intent(this,FrontPage.class);
                startActivity(intent);

        }

    }

}
