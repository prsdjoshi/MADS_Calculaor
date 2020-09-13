package com.example.calcmads.calc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.calcmads.R;
import com.example.calcmads.data.model.HistoryModel;
import com.example.calcmads.ui.login.LoginActivity;
import com.example.calcmads.utils.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Stack;

import static com.example.calcmads.MyApplication.historyModelArrayList;
import static com.example.calcmads.MyApplication.sharedPreferences;

public class CalcActivity extends AppCompatActivity {

    private TextView screen;
    private String display = "";
    public static EditText inputtext;
    public static TextView displaytext;
    private String currentOperator = "";
    private String result = "";
    private DatabaseReference mDatabase;
    private DatabaseReference historylistEndPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        initToolbar();
        ImageButton deletevar = (ImageButton) findViewById(R.id.butdelet);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        historylistEndPoint = mDatabase.child("Historylist");

        deletevar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletenumber();
            }
        });

        screen = (TextView) findViewById(R.id.input_box);
        screen.setText(display);
        inputtext = findViewById(R.id.input_box);
        displaytext = findViewById(R.id.result_box);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calc MADS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_login);
        if (sharedPreferences.getBoolean("islogin", false)) {
            item.setTitle("Logout");
        } else {
            item.setTitle("Login");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_history) {
            startActivity(new Intent(CalcActivity.this, HistoryActivity.class));
        } else if (item.getItemId() == R.id.action_login) {
            sharedPreferences.edit().putBoolean("islogin", false).commit();
            finish();
            startActivity(new Intent(CalcActivity.this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void appendToLast(String str) {
        this.inputtext.getText().append(str);
    }

    public void onClickNumber(View v) {
        Button b = (Button) v;
        display += b.getText();
        appendToLast(display);
        display = "";
    }

    public void onClickOperator(View v) {
        Button b = (Button) v;
        display += b.getText();
        if (endsWithOperatore()) {
            replace(display);
            currentOperator = b.getText().toString();
            display = "";
        } else {
            appendToLast(display);
            currentOperator = b.getText().toString();
            display = "";
        }

    }

    public void onClearButton(View v) {
        inputtext.getText().clear();
        displaytext.setText("");
    }

    public void deletenumber() {
        if (!this.inputtext.getText().toString().isEmpty())
            this.inputtext.getText().delete(getinput().length() - 1, getinput().length());
    }

    private String getinput() {
        return this.inputtext.getText().toString();
    }

    private boolean endsWithOperatore() {
        return getinput().endsWith("+") || getinput().endsWith("-") || getinput().endsWith("/") || getinput().endsWith("x");
    }

    private void replace(String str) {
        inputtext.getText().replace(getinput().length() - 1, getinput().length(), str);
    }

    public void equalresult(View v) {
        String input = getinput();

        if (!endsWithOperatore()) {

            if (input.contains("x")) {
                input = input.replaceAll(" x ", " * ");
            }

            if (input.contains("\u00F7")) {
                input = input.replaceAll(" \u00F7 ", " / ");
            }
            //   Expression expression = new ExpressionBuilder(input).build();
            Log.d("Exp: ", input);
            // Log.d("Exp result: ", String.valueOf(expression.evaluate()));
            MadsRuleEvalute madsRuleEvalute = new MadsRuleEvalute();
            float result = madsRuleEvalute.evaluate(input);
            if (result != -1) {
                displaytext.setText(result + "");
                saveHistory(input, result);
            }
//            double result = expression.evaluate();
//            displaytext.setText(String.valueOf(result));
        } else displaytext.setText("");

        System.out.println(result);
    }

    public void saveHistory(String input, float result) {
        if (historyModelArrayList.size() < 10) {
            HistoryModel historyModel = new HistoryModel(input, result);
            historyModelArrayList.add(historyModel);
            if (sharedPreferences.getBoolean("islogin", false)) {
                String key = historylistEndPoint.push().getKey();
                historyModel.setId(key);
                historylistEndPoint.child(key).setValue(historyModel);
            }

        } else {
            if (sharedPreferences.getBoolean("islogin", false)) {
                historylistEndPoint.child(historyModelArrayList.get(0).getId()).removeValue();
            }
            historyModelArrayList.remove(0);
            historyModelArrayList.add(new HistoryModel(input, result));
        }
    }

}
