package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button dot ;
    TextView historyNum ;
    String savedValueNumber ="";
    String savedOperator ="";
    boolean onEqualClick = false ;
    TextView result  ;
    boolean clickOperator = false ;
    String history ;
    String lastChar ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.textResult) ;
        dot = findViewById(R.id.dot) ;
        historyNum = findViewById(R.id.history);
    }
    @SuppressLint("SuspiciousIndentation")
    public void onClickNumber(View view){
        historyNum.append(((Button) view).getText().toString());
        if(onEqualClick)
        {
            result.setText(((Button) view).getText().toString());
            if(((Button) view).getText().toString().equals(".")) {
                view.setEnabled(false);
            }
            onEqualClick = false ;
        }
        else {
            result.append(((Button) view).getText().toString());
            if(((Button) view).getText().toString().equals("."))
                view.setEnabled(false);
        }
    }
    public void onClickOperation(View view){
        history = historyNum.getText().toString();
        lastChar = history.isEmpty() ? "" : String.valueOf(history.charAt(history.length() - 1));
        if(lastChar.equals(".")){
            return;
        }
        if(result.getText().toString().isEmpty())
            return;

        if(savedOperator.isEmpty())
        {
            savedValueNumber = result.getText().toString();
        }else {
            String rhs = result.getText().toString();
            savedValueNumber = calculate(savedValueNumber,savedOperator,rhs);
        }
        savedOperator = ((Button) view).getText().toString();
        result.setText("");
        historyNum.append(((Button) view).getText().toString());
        clickOperator = true ;
        dot.setEnabled(true);
    }
    public void clickEqual(View view)
    {
        history = historyNum.getText().toString();
        lastChar = history.isEmpty() ? "" : String.valueOf(history.charAt(history.length() - 1));
        if(lastChar.equals("+") || lastChar.equals("-") || lastChar.equals("*") || lastChar.equals("/") || lastChar.equals(".") ||
           lastChar.equals("%") || lastChar.equals("^")){
            return;
        }
        if(savedValueNumber.equals("")) {
            return;
        }
        if(!result.getText().toString().equals("") && !clickOperator) {
            dot.setEnabled(true);
            result.setText(result.getText().toString());
            onEqualClick = true;
        }
        else{
            dot.setEnabled(true);
            String RHS = result.getText().toString();
            savedValueNumber = calculate(savedValueNumber, savedOperator, RHS);
            result.setText(savedValueNumber);
            savedValueNumber = "";
            savedOperator = "";
            onEqualClick = true;
            clickOperator = false ;
        }
        historyNum.setText(savedValueNumber);
    }
    public void onClickClear(View view)
    {
         result.setText("");
         historyNum.setText("");
         savedOperator = "";
         savedValueNumber = "";
    }
    public void onClickDelete(View view) {
        if (!result.getText().toString().equals("")) {
            result.setText(result.getText().toString().substring(0, result.getText().length() - 1));
        }
        if (!historyNum.getText().toString().equals("")) {
            historyNum.setText(historyNum.getText().toString().substring(0, historyNum.getText().length() - 1));
        }
    }


    public String calculate(String lhs , String op , String rhs)
    {
        double num1 = Double.parseDouble(lhs);
        double num2 = Double.parseDouble(rhs);
        double res = 0 ;
        if(op.equals("+"))
        {
            res = num1 + num2 ;
        } else if (op.equals("-")) {
            res = num1 - num2 ;
        }
        else if (op.equals("/")) {
            res = num1 / num2 ;
        }
        else if (op.equals("*")) {
            res = num1 * num2 ;
        } else if (op.equals("%")) {
            res = num1 % num2 ;
        }
        else if (op.equals("^")) {
            int result = 1 ;
            for(int i = 0 ; i < num2 ; i++ ){
                result *= num1 ;
            }
            res = result ;
        }
        return ""+res;
    }
}