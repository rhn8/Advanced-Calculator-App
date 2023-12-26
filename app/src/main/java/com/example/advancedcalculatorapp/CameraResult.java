package com.example.advancedcalculatorapp;

import static com.example.advancedcalculatorapp.CameraSolve.bitmap;
// bitmap image object from CameraSolve class

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List; // Java utility classes

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;  //Classes to deal with activity result

import org.mariuszgromada.math.mxparser.Expression; // parser

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc; // OpenCV classes

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView; // android utility classes

import com.google.android.material.button.MaterialButton; // class for button widgets

public class CameraResult extends AppCompatActivity implements View.OnClickListener {

    Bitmap image = bitmap; // bitmap from CameraSolve activity

    TextView input;
    TextView solution;

    double confidenceThreshold = 0.25f; // fixed minimum confidence value for detection
    double scoreThreshold = 0.3f; // fixed confidence for the class

    String[] classes = {"0","1","2","3","4","5","6","7","8","9","+",".","/","-","*"};
    // Stores the classes in the order of the class id's

    private List<List<Double>> detect(Bitmap inputimg, Net net){  // Takes in a image Bitmap and Net object and returns a 2-d list

        Mat frame = new Mat();

        Utils.bitmapToMat(inputimg,frame); // converts bitmap image to Mat, and stores in frame Mat

        Imgproc.resize(frame, frame, new Size(640,640)); // resize img to fit the neural network params

        Imgproc.cvtColor(frame,frame, Imgproc.COLOR_RGBA2RGB); // Convert frame to rgba colourspace

        frame.convertTo(frame, CvType.CV_32F); // Convert matrix to contain 32-bit float elements

        List<List<Double>> symbols = new ArrayList<List<Double>>(); // Initialises a 2-d detected symbols list that is mutable

        Mat imageblob = Dnn.blobFromImage(frame, 0.00392, new Size(640,640), new Scalar(0,0,0), false, false);
        // Creates an imageblob of the image data and changes its scale factor

        net.setInput(imageblob); // Sets the imageblob as the input for the neural network

        Mat result = new Mat();

        result = net.forward("output0"); // Stores the output results of the feedforward algorithm in the result Mat

        result = result.reshape(1, (int) result.total() / 20); // Reshaping result from 3-d array to 2-d array

        // Result Mat is 20 dimension, in format: (x,y,z,h,object_conf, class0_conf, class1_conf....)  (15 classes + 5 extra dimensions)

        for (int i = 0; i < result.rows(); ++i){ // iterates over the number of detections / rows

            Mat detection = result.row(i); // Obtain a specific detection row

            double confidence = detection.get(0,4)[0]; //confidence that an object is present

            if (confidence>confidenceThreshold){

                Mat scores = detection.colRange(5,result.cols()); // Isolates columns containing class confidence scores

                Core.MinMaxLocResult mm = Core.minMaxLoc(scores); // stores minimum and maximum values of the scores

                Point classid = mm.maxLoc; // obtain the location of the maximum value

                if (mm.maxVal > scoreThreshold){ // If the maximum value is larger than score threshold

                    double xcoord = detection.get(0,0)[0]; // The x coordinate of the detection

                    List<Double> symbol = new ArrayList<Double>(); // A new symbol is added
                    symbol.add(classid.x); // Adds the classid
                    symbol.add(xcoord); // adds the xcoordinate
                    symbols.add(symbol); //adds symbol array to symbols
                }

            }
        }

        return symbols; // returns the 2-d list containing detected symbols
    }

    private String rearrange(List<List<Double>> symbols) {
        String s = "";

        boolean sorted = false;

        while (sorted == false) {
            sorted = true;
            for (int i = 1; i < symbols.size()-1; i++) {
                if (symbols.get(i).get(1) > symbols.get(i-1).get(1)) {
                    // Perform bubble sort on array
                    double temp = symbols.get(i).get(1);
                    symbols.get(i).set(1,symbols.get(i+1).get(1));
                    symbols.get(i+1).set(1,temp);

                    sorted = false;
                }
            }
        }
        for (List<Double> i : symbols){
            double d = i.get(0); // Obtain classid
            int num = (int) d; // cast to int
            s = s + classes[num] + ""; // convert to string symbol and add to return expression
        }
        return s;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result); // Sets camera result as the activity to be viewed

            MaterialButton exit = findViewById(R.id.camresult_exit);
            exit.setOnClickListener(this);

            MaterialButton retry = findViewById(R.id.retry);
            retry.setOnClickListener(this);

        input = findViewById(R.id.caminput);
        solution = findViewById(R.id.camresult);

        input.setMovementMethod(new ScrollingMovementMethod());
        // Creates a scroller and sets this as the MovementMethod for the input TextView

        solution.setMovementMethod(new ScrollingMovementMethod());
        // Creates a scroller and sets this as the MovementMethod for the solution TextView

        OpenCVLoader.initDebug(); // Loads required openCV libraries (uses dynamic linking to reduce file size)

        String filepath = "/sdcard/Download/Test2.png";  // File path of the test image

        Bitmap img = BitmapFactory.decodeFile(filepath);

        String inp = rearrange(initalise(image)); // initialise with the input image and rearrange to obtain string

        String test = "1+2";

        input.setText(inp);

        input.setText(test);

        Expression e = new Expression(test);
        String result  = e.calculate() + "";

        solution.setText(result);

    }

    public List<List<Double>> initalise(Bitmap inputimg){

        String weights = "/sdcard/Download/best (2).onnx"; //File path of the weights

        Net net = Dnn.readNetFromONNX(weights); // Read net from ONNX model
        return detect(inputimg,net);
    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.camresult_exit:
                Intent intent1 = new Intent(this, FrontPage.class);
                startActivity(intent1);
                break;
            case R.id.retry:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); // launch file explorer intent
                intent.setType("image/*"); // file must be an image
                launcher.launch(intent); // launch intent


        }

    }


    ActivityResultLauncher<Intent> launcher = registerForActivityResult( // file explorer launcher to choose img file
            // Obtains result from intent
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) { // when activity result is obtained
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // If the activity result code is Result_OK
                        Intent data = result.getData(); // Get intent object as data

                        if (data != null && data.getData() != null) {
                            Uri uri = data.getData(); // Get URI of the file
                            try {

                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                // Convert into input stream
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                // Read inputstream into bitmap file

                                String inp = rearrange(initalise(bitmap)); // initialise with the input image and rearrange to obtain string

                                input.setText(inp); // Set input TextView as the input

                                Expression e = new Expression(inp);
                                String answer  = e.calculate() + ""; // obtain solution to expression

                                solution.setText(answer); // set solution TextView as answer

                                inputStream.close(); // close inputstream after decoding it
                            } catch (IOException e) {
                                e.printStackTrace(); // In case of IO error, print stack trace instead of crashing
                            }
                        }
                    }
                }
            });

}