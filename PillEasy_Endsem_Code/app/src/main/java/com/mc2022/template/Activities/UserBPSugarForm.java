package com.mc2022.template.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.R;
import com.mc2022.template.Utilities.SnackBarUtility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserBPSugarForm extends AppCompatActivity {
    SQLiteHelper helper;
    private TextInputLayout weight, bp, sugar;
    private AppCompatEditText weight1, bp1, sugar1;
    private TextView dateMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bpsugar_form);

        helper = new SQLiteHelper(this);

        dateMain = findViewById(R.id.dateMain);

        weight = findViewById(R.id.weight);
        weight1 = findViewById(R.id.weight1);
        weight1.setOnFocusChangeListener((view, b) -> {
            if (weight1.getText().toString().isEmpty()) {
                weight.setErrorEnabled(true);
                weight.setError("*Required");
            } else {
                weight.setErrorEnabled(false);
            }
        });
        weight1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (weight1.getText().toString().isEmpty()) {
                    weight.setErrorEnabled(true);
                    weight.setError("*Required");
                } else {
                    weight.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        bp = findViewById(R.id.bp);
        bp1 = findViewById(R.id.bp1);
        bp1.setOnFocusChangeListener((view, b) -> {
            if (bp1.getText().toString().isEmpty()) {
                bp.setErrorEnabled(true);
                bp.setError("*Required");
            } else {
                bp.setErrorEnabled(false);
            }
        });


        bp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (bp1.getText().toString().isEmpty()) {
                    bp.setErrorEnabled(true);
                    bp.setError("*Required");
                } else {
                    bp.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sugar = findViewById(R.id.sugar);
        sugar1 = findViewById(R.id.sugar1);
        sugar1.setOnFocusChangeListener((view, b) -> {
            if (sugar1.getText().toString().isEmpty()) {
                sugar.setErrorEnabled(true);
                sugar.setError("*Required");
            } else {
                sugar.setErrorEnabled(false);
            }
        });


        sugar1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (sugar1.getText().toString().isEmpty()) {
                    sugar.setErrorEnabled(true);
                    sugar.setError("*Required");
                } else {
                    sugar.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Button saveButton = findViewById(R.id.saveButton);
        Button showButton = findViewById(R.id.showButton);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dates = sdf.format(new Date());
        dateMain.setText(dates);

        saveButton.setOnClickListener(view -> {

            if (weight.getEditText().getText().toString().isEmpty() || bp.getEditText().getText().toString().isEmpty() || sugar.getEditText().getText().toString().isEmpty() || dateMain.getText().toString().isEmpty()) {
                SnackBarUtility.ShowSnackbar("All fields are required", findViewById(R.id.bpsugarform_layout));
            } else {
                String dt = dateMain.getText().toString();
                Log.e("Date", dt);
                boolean usercheckresult = helper.checkDate(dt);
                Log.e("IfPresent", String.valueOf(usercheckresult));
                if (usercheckresult) {
                    SnackBarUtility.ShowSnackbar("Data for Today already Submitted!\nTry again Tomorrow.", findViewById(R.id.bpsugarform_layout));
                    //Toast.makeText(UserBPSugarForm.this, " Data for Today already Submitted! \n Try again Tomorrow.", Toast.LENGTH_SHORT).show();
                } else {
                    String bp_text = bp.getEditText().getText().toString();
                    String[] bp_text1 = bp_text.split("/");
                    Log.e("BP length", String.valueOf(bp_text1.length));
                    if (bp_text1.length != 2) {
                        SnackBarUtility.ShowSnackbar("Please provide BP value in proper format", findViewById(R.id.bpsugarform_layout));
                    } else {
                        processInsert(weight.getEditText().getText().toString(), bp.getEditText().getText().toString(), sugar.getEditText().getText().toString(), dateMain.getText().toString());
                        SnackBarUtility.ShowSnackbar("Successfully Inserted", findViewById(R.id.bpsugarform_layout));
                        Intent intent = new Intent(UserBPSugarForm.this, BPSugar_RecyclerView.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        showButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), BPSugar_RecyclerView.class)));
    }

    private void processInsert(String w, String b, String s, String d) {
        String res = new SQLiteHelper(this).addrecord(w, b, s, d);
        SnackBarUtility.ShowSnackbar(res, findViewById(R.id.bpsugarform_layout));
    }
}