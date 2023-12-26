package com.example.advancedcalculatorapp; // package name

import android.content.Intent; // Imports for handling android widgets
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import org.mariuszgromada.math.mxparser.Expression; // used to parse expression string to obtain result

public class GeneralCalc extends AppCompatActivity implements View.OnClickListener { // implements OnClickListener as interface

    TextView inp;// Input textview
    TextView sol; // Solution textview

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Displays the activity and determines widget functionality
        super.onCreate(savedInstanceState); // ensures the onCreate method is run along with the rest of the code
        setContentView(R.layout.activity_gencalc); // Sets the content to be viewed as the front page activity- location: res/layout/activity_gencalc

        // Buttons for the keyboard
        MaterialButton one = findViewById(R.id.button_gen_1);
        one.setOnClickListener(this);
        MaterialButton two = findViewById(R.id.button_gen_2);
        two.setOnClickListener(this);
        MaterialButton three = findViewById(R.id.button_gen_3);
        three.setOnClickListener(this);
        MaterialButton four = findViewById(R.id.button_gen_4);
        four.setOnClickListener(this);
        MaterialButton five = findViewById(R.id.button_gen_5);
        five.setOnClickListener(this);
        MaterialButton six = findViewById(R.id.button_gen_6);
        six.setOnClickListener(this);
        MaterialButton seven = findViewById(R.id.button_gen_7);
        seven.setOnClickListener(this);
        MaterialButton eight = findViewById(R.id.button_gen_8);
        eight.setOnClickListener(this);
        MaterialButton nine = findViewById(R.id.button_gen_9);
        nine.setOnClickListener(this);
        MaterialButton zero = findViewById(R.id.button_gen_0);
        zero.setOnClickListener(this);
        MaterialButton clearSingle = findViewById(R.id.button_gen_C);
        clearSingle.setOnClickListener(this);
        MaterialButton leftpar = findViewById(R.id.button_gen_lp);
        leftpar.setOnClickListener(this);
        MaterialButton rightpar = findViewById(R.id.button_gen_rp);
        rightpar.setOnClickListener(this);
        MaterialButton clearAll = findViewById(R.id.button_gen_clear);
        clearAll.setOnClickListener(this);
        MaterialButton add = findViewById(R.id.button_gen_add);
        add.setOnClickListener(this);
        MaterialButton sub = findViewById(R.id.button_gen_sub);
        sub.setOnClickListener(this);
        MaterialButton div = findViewById(R.id.button_gen_div);
        div.setOnClickListener(this);
        MaterialButton prod = findViewById(R.id.button_gen_prod);
        prod.setOnClickListener(this);
        MaterialButton equals = findViewById(R.id.button_gen_equals);
        equals.setOnClickListener(this);
        MaterialButton decimal = findViewById(R.id.button_gen_decimal);
        decimal.setOnClickListener(this);


        MaterialButton exit = findViewById(R.id.button_gen_exit); //Exit button
        exit.setOnClickListener(this);

        inp = findViewById(R.id.input); // assign the TexViews for the input and solution
        sol = findViewById(R.id.solution);

        inp.setMovementMethod(new ScrollingMovementMethod());
        // Creates a scroller and sets this as the MovementMethod for the input TextView

    }

    @Override
    public void onClick(View view) {  // called when a button is clicked

        MaterialButton button = (MaterialButton) view; //creates a new button instance
        String text = button.getText().toString(); // converts the android:text attribute to String
        String expression = inp.getText().toString(); // converts the contents of the input TextView to string- main expression string

        switch (view.getId()){
            case R.id.button_gen_exit:
                Intent intent = new Intent(this,FrontPage.class);
                startActivity(intent); // using Intent to change current activity to the FrontPage
                break;
            case R.id.button_gen_C:
                expression = expression.substring(0,expression.length()-1); // removes a character from the expression string
                break;
            case R.id.button_gen_clear:
                expression = ""; // clears the expression string
                break;
            case R.id.button_gen_equals:
                Expression e = new Expression(expression); // instantiates a new Expression object with parameter expression
                double result = e.calculate(); // calls the calculate method- which parses over the expression object and returns a result
                String resultString = result+"";// concatenating to string so the solution Textview can be changed
                sol.setText(resultString); // changes the solution textview string to the result
                break;
            default:
                expression = expression + text; // if none of the above operations are used, defaults to adding the text to the expression
                break;

        }

        inp.setText(expression); // changes the input TextView to the current expression on every click
    }
}
