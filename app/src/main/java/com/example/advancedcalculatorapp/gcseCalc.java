package com.example.advancedcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.material.button.MaterialButton;

import org.mariuszgromada.math.mxparser.Expression; // used to parse expression string to obtain result

public class gcseCalc extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    TextView input;
    TextView solution;
    String expression;

    private ViewSwitcher switcher; // used to switch widgets / views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcse_calc);

        switcher = (ViewSwitcher) findViewById(R.id.viewswitcher);

        // Buttons for the keyboard
        MaterialButton one = findViewById(R.id.button_gcse_1);
        one.setOnClickListener(this);
        MaterialButton two = findViewById(R.id.button_gcse_2);
        two.setOnClickListener(this);
        MaterialButton three = findViewById(R.id.button_gcse_3);
        three.setOnClickListener(this);
        MaterialButton four = findViewById(R.id.button_gcse_4);
        four.setOnClickListener(this);
        MaterialButton five = findViewById(R.id.button_gcse_5);
        five.setOnClickListener(this);
        MaterialButton six = findViewById(R.id.button_gcse_6);
        six.setOnClickListener(this);
        MaterialButton seven = findViewById(R.id.button_gcse_7);
        seven.setOnClickListener(this);
        MaterialButton eight = findViewById(R.id.button_gcse_8);
        eight.setOnClickListener(this);
        MaterialButton nine = findViewById(R.id.button_gcse_9);
        nine.setOnClickListener(this);
        MaterialButton zero = findViewById(R.id.button_gcse_0);
        zero.setOnClickListener(this);

        MaterialButton clearSingle = findViewById(R.id.button_gcse_C);
        clearSingle.setOnClickListener(this);
        clearSingle.setOnLongClickListener(this); // Sets a long click functionality to clear the text completely

        MaterialButton leftpar = findViewById(R.id.button_gcse_lp);
        leftpar.setOnClickListener(this);
        MaterialButton rightpar = findViewById(R.id.button_gcse_rp);
        rightpar.setOnClickListener(this);
        MaterialButton add = findViewById(R.id.button_gcse_add);
        add.setOnClickListener(this);
        MaterialButton sub = findViewById(R.id.button_gcse_sub);
        sub.setOnClickListener(this);
        MaterialButton div = findViewById(R.id.button_gcse_div);
        div.setOnClickListener(this);
        MaterialButton prod = findViewById(R.id.button_gcse_prod);
        prod.setOnClickListener(this);
        MaterialButton equals = findViewById(R.id.button_gcse_equals);
        equals.setOnClickListener(this);
        MaterialButton decimal = findViewById(R.id.button_gcse_decimal);
        decimal.setOnClickListener(this);

        //Buttons for inverse functions
        MaterialButton inv1 = findViewById(R.id.button_gcse_inv1);
        inv1.setOnClickListener(this);
        MaterialButton inv2 = findViewById(R.id.button_gcse_inv2);
        inv2.setOnClickListener(this);

        // All extra gcse calculator functions
        MaterialButton sin = findViewById(R.id.button_gcse_sin);
        sin.setOnClickListener(this);
        MaterialButton cos = findViewById(R.id.button_gcse_cos);
        cos.setOnClickListener(this);
        MaterialButton tan = findViewById(R.id.button_gcse_tan);
        tan.setOnClickListener(this);
        MaterialButton pow = findViewById(R.id.button_gcse_pow);
        pow.setOnClickListener(this);
        MaterialButton sqrt = findViewById(R.id.button_gcse_sqrt);
        sqrt.setOnClickListener(this);
        MaterialButton arcsin = findViewById(R.id.button_gcse_arcsin);
        arcsin.setOnClickListener(this);
        MaterialButton arccos = findViewById(R.id.button_gcse_arccos);
        arccos.setOnClickListener(this);
        MaterialButton arctan = findViewById(R.id.button_gcse_arctan);
        arctan.setOnClickListener(this);
        MaterialButton log = findViewById(R.id.button_gcse_log);
        log.setOnClickListener(this);
        MaterialButton square = findViewById(R.id.button_gcse_square);
        square.setOnClickListener(this);
        MaterialButton pi = findViewById(R.id.button_gcse_pi);
        pi.setOnClickListener(this);


        MaterialButton exit = findViewById(R.id.button_gcse_exit); //Exit button
        exit.setOnClickListener(this);

        input = findViewById(R.id.input_gcse);
        solution = findViewById(R.id.solution_gcse);

        input.setMovementMethod(new ScrollingMovementMethod());
        // Creates a scroller and sets this as the MovementMethod for the input TextView

    }

    @Override
    public void onClick(View view) {

        MaterialButton button = (MaterialButton) view; //creates a new button instance
        String text = button.getText().toString();
        expression = input.getText().toString(); // converts the contents of the input TextView to string- main expression string

        switch (view.getId()){
            case R.id.button_gcse_exit:
                Intent intent = new Intent(this,FrontPage.class);
                startActivity(intent); // using Intent to change current activity to the FrontPage
                break;
            case R.id.button_gcse_C:
                if (expression.length() > 0) {
                    expression = expression.substring(0, expression.length() - 1); // removes a character from the expression string
                }
                break;
            case R.id.button_gcse_equals:
                Expression e = new Expression(expression); // instantiates a new Expression object with parameter expression
                double result = e.calculate(); // calls the calculate method- which parses over the expression object and returns a result
                String resultString = result+"";// concatenating to string so the solution Textview can be changed
                solution.setText(resultString); // changes the solution textview string to the result
                break;
            case R.id.button_gcse_inv1:
                switcher.showNext(); // changes view to one with the extra buttons
                break;
            case R.id.button_gcse_inv2:
                switcher.showPrevious(); // switches back view
                break;
            case R.id.button_gcse_sin:
                expression = expression + "sin(";
                break;
            case R.id.button_gcse_cos:
                expression = expression + "cos(";
                break;
            case R.id.button_gcse_tan:
                expression = expression + "tan(";
                break;
            case R.id.button_gcse_arcsin:
                expression = expression + "arcsin(";
                break;
            case R.id.button_gcse_arccos:
                expression = expression + "arccos(";
                break;
            case R.id.button_gcse_arctan:
                expression = expression + "arctan(";
                break;
            case R.id.button_gcse_pi:
                expression = expression + "\u03c0"; //unicode symbol for pi
                break;
            case R.id.button_gcse_sqrt:
                expression = expression + "\u221a" + "("; //unicode symbol for square root
                break;
            case R.id.button_gcse_log:
                expression = expression + "log10("; // log base 10
                break;
            case R.id.button_gcse_square:
                expression = expression + ")^2";
                break;
            default:
                expression = expression + text; // if none of the above operations are used, defaults to adding the text to the expression
                break;

        }
        input.setText(expression);

    }

    @Override
    public boolean onLongClick(View view){

        expression = "";
        input.setText(expression); // clears all input text
        return true;
    }
}