package com.example.advancedcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle; // Utility class, required to pass data between activities
import android.text.method.ScrollingMovementMethod; // For Horizontal scrolling
import android.view.View; // To display content on screen
import android.widget.EditText; // To allow for an editable TextView
import android.widget.TextView; // For TextView widget
import android.widget.ViewFlipper; // For ViewFlipper widget

import com.google.android.material.button.MaterialButton; // Button functionality

import org.mariuszgromada.math.mxparser.Expression;  // For parsing math expressions

public class AlevelCalc extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    EditText input;// Input EditText
    TextView solution; // Solution textview
    String expression;

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alevel_calc);

        viewFlipper = findViewById(R.id.viewflipper);

        // Buttons for the keyboard
        MaterialButton one = findViewById(R.id.button_alevel_one);
        one.setOnClickListener(this);
        MaterialButton two = findViewById(R.id.button_alevel_two);
        two.setOnClickListener(this);
        MaterialButton three = findViewById(R.id.button_alevel_three);
        three.setOnClickListener(this);
        MaterialButton four = findViewById(R.id.button_alevel_four);
        four.setOnClickListener(this);
        MaterialButton five = findViewById(R.id.button_alevel_five);
        five.setOnClickListener(this);
        MaterialButton six = findViewById(R.id.button_alevel_six);
        six.setOnClickListener(this);
        MaterialButton seven = findViewById(R.id.button_alevel_seven);
        seven.setOnClickListener(this);
        MaterialButton eight = findViewById(R.id.button_alevel_eight);
        eight.setOnClickListener(this);
        MaterialButton nine = findViewById(R.id.button_alevel_nine);
        nine.setOnClickListener(this);
        MaterialButton zero = findViewById(R.id.button_alevel_zero);
        zero.setOnClickListener(this);

        MaterialButton clearSingle = findViewById(R.id.button_alevel_C);
        clearSingle.setOnClickListener(this);
        clearSingle.setOnLongClickListener(this); // Sets a long click functionality to clear the text completely

        MaterialButton leftpar = findViewById(R.id.button_alevel_leftpar);
        leftpar.setOnClickListener(this);
        MaterialButton rightpar = findViewById(R.id.button_alevel_rightpar);
        rightpar.setOnClickListener(this);
        MaterialButton add = findViewById(R.id.button_alevel_add);
        add.setOnClickListener(this);
        MaterialButton sub = findViewById(R.id.button_alevel_sub);
        sub.setOnClickListener(this);
        MaterialButton div = findViewById(R.id.button_alevel_div);
        div.setOnClickListener(this);
        MaterialButton prod = findViewById(R.id.button_alevel_prod);
        prod.setOnClickListener(this);
        MaterialButton equals = findViewById(R.id.button_alevel_equals);
        equals.setOnClickListener(this);
        MaterialButton decimal = findViewById(R.id.button_alevel_decimal);
        decimal.setOnClickListener(this);

        //Buttons for switching between inverse/hyperbolic functions
        MaterialButton inv1 = findViewById(R.id.button_alevel_inv);
        inv1.setOnClickListener(this);
        MaterialButton inv2 = findViewById(R.id.button_alevel_inv1);
        inv2.setOnClickListener(this);
        MaterialButton inv3 = findViewById(R.id.button_alevel_inv2);
        inv3.setOnClickListener(this);
        MaterialButton inv4 = findViewById(R.id.button_alevel_inv3);
        inv4.setOnClickListener(this);
        MaterialButton hyp = findViewById(R.id.button_alevel_hyp);
        hyp.setOnClickListener(this);
        MaterialButton hyp1 = findViewById(R.id.button_alevel_hyp1);
        hyp1.setOnClickListener(this);
        MaterialButton hyp2 = findViewById(R.id.button_alevel_hyp2);
        hyp2.setOnClickListener(this);
        MaterialButton hyp3 = findViewById(R.id.button_alevel_hyp3);
        hyp3.setOnClickListener(this);


        MaterialButton sin = findViewById(R.id.button_alevel_sin);
        sin.setOnClickListener(this);
        MaterialButton cos = findViewById(R.id.button_alevel_cos);
        cos.setOnClickListener(this);
        MaterialButton tan = findViewById(R.id.button_alevel_tan);
        tan.setOnClickListener(this);
        MaterialButton pow = findViewById(R.id.button_alevel_pow);
        pow.setOnClickListener(this);
        MaterialButton arcsin = findViewById(R.id.button_alevel_arcsin);
        arcsin.setOnClickListener(this);
        MaterialButton arccos = findViewById(R.id.button_alevel_arccos);
        arccos.setOnClickListener(this);
        MaterialButton arctan = findViewById(R.id.button_alevel_arctan);
        arctan.setOnClickListener(this);
        MaterialButton log = findViewById(R.id.button_alevel_log);
        log.setOnClickListener(this);
        MaterialButton pi = findViewById(R.id.button_alevel_pi);
        pi.setOnClickListener(this);
        MaterialButton sinh = findViewById(R.id.button_alevel_sinh);
        sinh.setOnClickListener(this);
        MaterialButton cosh = findViewById(R.id.button_alevel_cosh);
        cosh.setOnClickListener(this);
        MaterialButton tanh = findViewById(R.id.button_alevel_tanh);
        tanh.setOnClickListener(this);
        MaterialButton arsinh = findViewById(R.id.button_alevel_arsinh);
        arsinh.setOnClickListener(this);
        MaterialButton arcosh = findViewById(R.id.button_alevel_arcosh);
        arcosh.setOnClickListener(this);
        MaterialButton artanh = findViewById(R.id.button_alevel_artanh);
        artanh.setOnClickListener(this);
        MaterialButton derivative = findViewById(R.id.button_alevel_derivative);
        derivative.setOnClickListener(this);
        MaterialButton integral = findViewById(R.id.button_alevel_integral);
        integral.setOnClickListener(this);
        MaterialButton e = findViewById(R.id.button_alevel_e);
        e.setOnClickListener(this);
        MaterialButton x = findViewById(R.id.button_alevel_x);
        x.setOnClickListener(this);
        MaterialButton ln = findViewById(R.id.button_alevel_ln);
        ln.setOnClickListener(this);
        MaterialButton Matrix = findViewById(R.id.button_alevel_Mat);
        Matrix.setOnClickListener(this);

        //Extra added buttons

        MaterialButton x1 = findViewById(R.id.button_alevel_x1);
        x1.setOnClickListener(this);
        MaterialButton x2 = findViewById(R.id.button_alevel_x2);
        x2.setOnClickListener(this);
        MaterialButton x3 = findViewById(R.id.button_alevel_x3);
        x3.setOnClickListener(this);
        MaterialButton ln1 = findViewById(R.id.button_alevel_ln1);
        ln1.setOnClickListener(this);
        MaterialButton ln2 = findViewById(R.id.button_alevel_ln2);
        ln2.setOnClickListener(this);
        MaterialButton integral1 = findViewById(R.id.button_alevel_integral1);
        integral1.setOnClickListener(this);
        MaterialButton integral2 = findViewById(R.id.button_alevel_integral2);
        integral2.setOnClickListener(this);



        MaterialButton exit = findViewById(R.id.button_alevel_exit); //Exit button
        exit.setOnClickListener(this);

        input = findViewById(R.id.input_alevel); // assign the TexViews for the input and solution
        solution = findViewById(R.id.solution_alevel);

        input.setMovementMethod(new ScrollingMovementMethod());
        // Creates a scroller and sets this as the MovementMethod for the input TextView

    }

    @Override
    public void onClick(View view) {

        MaterialButton button = (MaterialButton) view; //creates a new button instance
        String text = button.getText().toString(); // converts the android:text attribute to String
        String expression = input.getText().toString(); // converts the contents of the input TextView to string- main expression string

        switch (view.getId()){
            case R.id.button_alevel_exit:
                Intent intent = new Intent(this,FrontPage.class);
                startActivity(intent); // using Intent to change current activity to the FrontPage
                break;
            case R.id.button_alevel_C:
                if(expression.length() > 0) {
                    expression = expression.substring(0, expression.length() - 1);
                }// removes a character from the expression string
                break;
            case R.id.button_alevel_equals:
                Expression e = new Expression(expression); // instantiates a new Expression object with parameter expression
                double result = e.calculate(); // calls the calculate method- which parses over the expression object and returns a result
                String resultString = result+"";// concatenating to string so the solution Textview can be changed
                solution.setText(resultString); // changes the solution textview string to the result
                break;
            case R.id.button_alevel_sin:
                expression = expression + "sin(";
                break;
            case R.id.button_alevel_cos:
                expression = expression + "cos(";
                break;
            case R.id.button_alevel_tan:
                expression = expression + "tan(";
                break;
            case R.id.button_alevel_arcsin:
                expression = expression + "arcsin(";
                break;
            case R.id.button_alevel_arccos:
                expression = expression + "arccos(";
                break;
            case R.id.button_alevel_arctan:
                expression = expression + "arctan(";
                break;
            case R.id.button_alevel_sinh:
                expression = expression + "sinh(";
                break;
            case R.id.button_alevel_cosh:
                expression = expression + "cosh(";
                break;
            case R.id.button_alevel_tanh:
                expression = expression + "tanh(";
                break;
            case R.id.button_alevel_arsinh:
                expression = expression + "arsinh(";
                break;
            case R.id.button_alevel_arcosh:
                expression = expression + "arcosh(";
                break;
            case R.id.button_alevel_artanh:
                expression = expression + "artanh(";
                break;
            case R.id.button_alevel_pi:
                expression = expression + "\u03c0"; //unicode symbol for pi
                break;
            case R.id.button_alevel_log:
                expression = expression + "log10("; // log base 10
                break;
            case R.id.button_alevel_ln:
                expression = expression + "ln(";
                break;
            case R.id.button_alevel_ln1:
                expression = expression + "ln("; // log base e
                break;
            case R.id.button_alevel_ln2:
                expression = expression + "ln(";
                break;
            case R.id.button_alevel_x:
                expression = expression + "x";
                break;
            case R.id.button_alevel_x1:
                expression = expression + "x";
                break;
            case R.id.button_alevel_x2:
                expression = expression + "x";
                break;
            case R.id.button_alevel_x3:
                expression = expression + "x";
                break;
            case R.id.button_alevel_pow:
                expression = expression + "^";
                break;
            case R.id.button_alevel_e:
                expression = expression + "e";
                break;
            case R.id.button_alevel_derivative:
                expression = expression + "\u2202(";
                break;
            case R.id.button_alevel_integral:
                expression = expression + "\u222b(";
                break;
            case R.id.button_alevel_integral1:
                expression = expression + "\u222b(";
                break;
            case R.id.button_alevel_integral2:
                expression = expression + "\u222b(";
                break;
            case R.id.button_alevel_Mat:
                Intent mat = new Intent(this,MatrixCalc.class);
                startActivity(mat);
                break;
            case R.id.button_alevel_inv:
                viewFlipper.showNext();
                break;
            case R.id.button_alevel_inv1:
                viewFlipper.showPrevious();
                break;
            case R.id.button_alevel_inv2:
                viewFlipper.showNext();
                break;
            case R.id.button_alevel_inv3:
                viewFlipper.showPrevious();
                break;
            case R.id.button_alevel_hyp:
            case R.id.button_alevel_hyp1:
                viewFlipper.showNext(); // hyperbolic button skips two views
                viewFlipper.showNext();
                break;
            case R.id.button_alevel_hyp2:
            case R.id.button_alevel_hyp3:
                viewFlipper.showPrevious();
                viewFlipper.showPrevious();
                break;
            default:
                expression = expression + text; // if none of the above operations are used, defaults to adding the text to the expression
                // inputdisplay = inputdisplay + text;
                break;
        }
        input.setText(expression); // changes the input TextView to the current expression on every click
    }

    @Override
    public boolean onLongClick(View view) {

        expression = "";
        input.setText(expression); // clears all input text

        return true;
    }
}