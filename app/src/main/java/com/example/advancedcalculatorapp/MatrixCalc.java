package com.example.advancedcalculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle; // Utility class, required to pass data between activities
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton; // Button functionality
import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.apache.commons.math3.linear.MatrixUtils; // Classes for matrix operations
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;

public class MatrixCalc extends AppCompatActivity implements View.OnClickListener {

    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_calc);

        input = findViewById(R.id.matinput);


        MaterialButton process = findViewById(R.id.process);
        process.setOnClickListener(this);

        MaterialButton exit = findViewById(R.id.Matrix_calc_exit);
        exit.setOnClickListener(this);


    }

    public double det(@NonNull ArrayList<ArrayList<Double>> mat){
        double result = 0;

        int rows = mat.size(); // Assuming a square matrix is input as param

        if (rows ==1){
            return mat.get(0).get(0); // If it is a 1x1 matrix, returns single element
        }

        if (rows ==2){
            return mat.get(0).get(0)*mat.get(1).get(1) - mat.get(0).get(1)*mat.get(1).get(0);
        } // if it is a 2x2 matrix

        for (int i=0; i < rows; i++){
            ArrayList<ArrayList<Double>> l = mat; // copy by value

            l.remove(0); // remove the first row

            for (int j=0; j<l.size(); j++){
                l.get(j).remove(i); // remove the chosen indices

                result = result + l.get(0).get(i)*Math.pow(-1,i%2) * det(l);
            } // performs recursion on the sub matrix obtained

        }
        return result;
    }

    public ArrayList<ArrayList<Double>> parse(@NonNull String s){ // Parses a matrix string to a 2-d arraylist of Doubles

        ArrayList<Double> elems = new ArrayList<>(); // container for elements
        ArrayList<ArrayList<Double>> result = new ArrayList<>(); // result array
        int rows=0;
        int cols=1; // Initialise with one column

        for (int i=0; i<s.length(); i++){
            switch (s.charAt(i)){
                case ',':
                case ' ':
                    break; // ignores spaces and commas
                case '/':
                    ++cols; //adds a new column
                    break;
                default:
                    elems.add((double) Character.digit(s.charAt(i),10)); // converts digit to double and adds to elems

            }
        }
        rows = elems.size()/cols; // obtain the row number

        for (int i=0; i<cols; i++){ // initalises the columns
            ArrayList<Double> emptyarr = new ArrayList<>();
            result.add(emptyarr);
        }

        for (int i=0; i< cols;i++){
            for (int j=0;j<rows; j++){ // adds each element of the container to the result
                result.get(i).add(elems.get(cols*i + j));
            }
        }
        return result;
    }

    public double[][] convert(ArrayList<ArrayList<Double>> arrlist){
        // converts 2-d arraylist of Double wrapper objects to double[][]

        double[][] result = new double[arrlist.size()][arrlist.get(0).size()];
        // Initialises result with same dimensions as input

        for (int i=0; i < arrlist.size(); i++){
            double[] temp = new double[arrlist.get(i).size()]; // temporary container for row elements

            for (int j=0; j< arrlist.get(i).size();j++){ // adds correct elements

                temp[j] = arrlist.get(i).get(j);

            }
        result[i] = temp; // adds row to given index
        }
        return result;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.Matrix_calc_exit){
            Intent intent = new Intent(this, FrontPage.class);
            startActivity(intent);
        }

        ExpandableTextView expTv = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view1).findViewById(R.id.expand_text_view1);
        // Create new expandable textviews on click

        try { // Catches error in the case of invalid inputs- avoids crashes

            String inputstring = input.getText().toString();

            ArrayList<ArrayList<Double>> arr = parse(inputstring); // parses input string to 2-d arraylist

            RealMatrix rm = MatrixUtils.createRealMatrix(convert(arr)); // Creats a new real matrix from the converted double[][] arr
            RealMatrix inverse = MatrixUtils.inverse(rm);
            RealMatrix transpose = rm.transpose(); // calculates inverse and transpose

            EigenDecomposition ed = new EigenDecomposition(rm);

            double[] realeigen = ed.getRealEigenvalues(); //obtains real and imaginary parts of eigenvals
            double[] imageigen = ed.getImagEigenvalues();
            String eigenvals = "EigenValues:" + "\n" + "\n";

            for (int i=0;i<realeigen.length;i++){ // converts eigenvalues into a + bi form
                eigenvals = eigenvals + realeigen[i] + " ";

                if (imageigen[i] != 0){
                    eigenvals = eigenvals + imageigen[i] + "i";
                }
                eigenvals = eigenvals + ",";
            }


            double determinant = det(arr);
            String d = determinant + ""; // convert double to string

            String inv = Arrays.deepToString(inverse.getData()); // converts inverse/transpose to String format
            String trans = Arrays.deepToString(transpose.getData());

            // Sets expandable textview text
            expTv.setText("Determinant value:" + "\n" + "\n" + d + "\n" + "Inverse Matrix:" + inv);

            expTv1.setText("Transpose Matrix:" + "\n" + "\n" + trans + "\n" + eigenvals.substring(0,eigenvals.length()-1) );
            // remove extra colon from eigenvals
        }
        catch (Exception e){
            System.out.println(e);
            input.setText("Enter a valid matrix");
        }

    }
}