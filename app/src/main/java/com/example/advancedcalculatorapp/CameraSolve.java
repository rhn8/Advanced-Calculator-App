package com.example.advancedcalculatorapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider; // imports for dealing with activity results

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast; //Utility classes

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat; // Opencv classes

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date; // Java utility classes

public class CameraSolve extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 , View.OnClickListener {
    // Implement button functionality and using the device camera for OpenCV
    public static String filepath; // filepath of saved image
    public static Bitmap bitmap; // Bitmap object of saved image

    Uri uri; // uri for the image file
    CameraBridgeViewBase cameraBridgeViewBase; // used for OpenCV cameraView
    BaseLoaderCallback baseLoaderCallback; // used for binding openCV libraries

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_solve); // Sets the content to be viewed as the camera_solve activity


        Intent pictureTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Create an image capture intent

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // Gives a unique timestamp for file creation to avoid collision
        filepath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + ""; // obtain a directory for the image
        String filename = "CameraSolve" + timestamp; // Each saved image file in the same format

        try { // If the imagefile can be created
            File imagefile = new File(filepath,filename); // create a temp file for the image

            uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider",imagefile);
            // Obtain the Uri for the image file

            pictureTakeIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // Save the captured image in the desired uri location.

            activityResultLauncher.launch(pictureTakeIntent);

        } catch (Exception e) { // If there is an error
            System.out.println(e);
        }

        cameraBridgeViewBase = (JavaCameraView)findViewById(R.id.CameraView); // Set the CameraView as cameraBridgeViewBase
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE); // Allows the user to see the camera view
        cameraBridgeViewBase.setCameraPermissionGranted(); // Checks if camera permissions has been granted
        cameraBridgeViewBase.setCvCameraViewListener(this);

        Button button = findViewById(R.id.Camerabutton);
        button.setOnClickListener(this);




        baseLoaderCallback = new BaseLoaderCallback(this) {
            @Override
            public void onManagerConnected(int status) {
                // Binds the openCV library to the activity and initialises the library if it is found
                super.onManagerConnected(status);

                switch (status) {

                    case BaseLoaderCallback.SUCCESS:
                        cameraBridgeViewBase.enableView(); //Enables the CameraView if library is found
                        break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }
            }
        };

    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Mat frame = inputFrame.rgba(); // Converts frame to RGBA colour space

        return frame;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()){
            Toast.makeText(getApplicationContext(),"There is a problem", Toast.LENGTH_SHORT).show();
        } //Displays error message if one is detected by the debugger

        else
        {
            baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS); // Library loading was a success
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraBridgeViewBase!=null){

            cameraBridgeViewBase.disableView(); // Stops the cameraView if paused
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase != null) {
            cameraBridgeViewBase.disableView(); // If the activity is stopped, stops cameraView
        }
    }

    @Override
    public void onClick(View view) {

        Intent CameraResult = new Intent(this,CameraResult.class); // Changes activity to display the camera result

        startActivity(CameraResult);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                                                                    // obtain resulting inputStream from activity
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData(); // obtain activityResult intent object

                        if (data != null ) { // if intent is not null
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                // Using the uri of the original image file- not intent.getData()

                                bitmap = BitmapFactory.decodeStream(inputStream); // convert to bitmap

                                bitmap = Bitmap.createScaledBitmap(bitmap,640,640,true);
                                // Resize to 640x640

                                inputStream.close(); // close inputStream


                            } catch (Exception e) {
                                System.out.println(e); // print exception if thrown

                            }
                        }
                    }
                }
            });
}




