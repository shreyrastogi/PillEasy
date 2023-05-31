package com.mc2022.template.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.R;
import com.mc2022.template.Utilities.SnackBarUtility;

public class SignUpActivity extends AppCompatActivity {

    EditText email;
    EditText name;
    EditText password;
    EditText repassword;
    Button btnSignUp, btnSignIn;
    SQLiteHelper myDB;
    TextInputLayout outlinedTextField1, outlinedTextField2, outlinedTextField3, outlinedTextField4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = (EditText) findViewById(R.id.signupemail);
        name = (EditText) findViewById(R.id.signupusername);
        password = (EditText) findViewById(R.id.passwordsignup);
        repassword = (EditText) findViewById(R.id.signuprepassword);
        btnSignUp = (Button) findViewById(R.id.btnRegister);
        btnSignIn = (Button) findViewById(R.id.btnLoginPage);
        myDB = new SQLiteHelper(this);


        btnSignUp.setOnClickListener(view -> {
            String em = email.getText().toString();
            String nam = name.getText().toString();
            String pass = password.getText().toString();
            String repass = repassword.getText().toString();
            if (em.equals("") || pass.equals("") || nam.equals("") || repass.equals("")) {
                SnackBarUtility.ShowSnackbar("All fields are required", findViewById(R.id.signup_layout));
            } else {
                if (pass.equals(repass)) {
                    if (!myDB.checkusername(nam)) {
                        if (myDB.insertData(em, nam, pass)) {
                            SnackBarUtility.ShowSnackbar("Registration Successful", findViewById(R.id.signup_layout));
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            SnackBarUtility.ShowSnackbar("Registration Failed", findViewById(R.id.signup_layout));
                        }
                    } else {
                        SnackBarUtility.ShowSnackbar("User already exists.\n Please Sign In", findViewById(R.id.signup_layout));
                    }

                } else {
                    SnackBarUtility.ShowSnackbar("Password not matching", findViewById(R.id.signup_layout));
                }
            }
        });
        btnSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });


        outlinedTextField1 = findViewById(R.id.signupemail_outlinedTextField1);
        outlinedTextField2 = findViewById(R.id.signupusernameoutlinedTextField2);
        outlinedTextField3 = findViewById(R.id.signuppasswordoutlinedTextField3);
        outlinedTextField4 = findViewById(R.id.signuprepasswordoutlinedTextField4);

        email.setOnFocusChangeListener((view, b) -> {
            if (email.getText().toString().isEmpty()) {
                outlinedTextField1.setErrorEnabled(true);
                outlinedTextField1.setError("*Required");
            } else {
                outlinedTextField1.setErrorEnabled(false);
            }
        });

        name.setOnFocusChangeListener((view, b) -> {
            if (name.getText().toString().isEmpty()) {
                outlinedTextField2.setErrorEnabled(true);
                outlinedTextField2.setError("*Required");
            } else {
                outlinedTextField2.setErrorEnabled(false);
            }
        });

        password.setOnFocusChangeListener((view, b) -> {
            if (password.getText().toString().isEmpty()) {
                outlinedTextField3.setErrorEnabled(true);
                outlinedTextField3.setError("*Required");
            } else {
                outlinedTextField3.setErrorEnabled(false);
            }
        });

        repassword.setOnFocusChangeListener((view, b) -> {
            if (repassword.getText().toString().isEmpty()) {
                outlinedTextField4.setErrorEnabled(true);
                outlinedTextField4.setError("*Required");
            } else {
                outlinedTextField4.setErrorEnabled(false);
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (name.getText().toString().isEmpty()) {
                    outlinedTextField2.setErrorEnabled(true);
                    outlinedTextField2.setError("*Required");
                } else {
                    outlinedTextField2.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (password.getText().toString().isEmpty()) {
                    outlinedTextField3.setErrorEnabled(true);
                    outlinedTextField3.setError("*Required");
                } else {
                    outlinedTextField3.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        repassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (repassword.getText().toString().isEmpty()) {
                    outlinedTextField4.setErrorEnabled(true);
                    outlinedTextField4.setError("*Required");
                } else {
                    outlinedTextField4.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (email.getText().toString().isEmpty()) {
                    outlinedTextField1.setErrorEnabled(true);
                    outlinedTextField1.setError("*Required");
                } else {
                    outlinedTextField1.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outstate) {
//        super.onSaveInstanceState(outstate);
//        EditText editText = (EditText) findViewById(R.id.signupemail);
//        CharSequence writtenData = editText.getText();
//        outstate.putCharSequence("mysavedata", writtenData);
//        EditText editText1 = (EditText) findViewById(R.id.signupusername);
//        CharSequence writtenData1 = editText1.getText();
//        outstate.putCharSequence("mydata", writtenData1);
//        EditText editText2 = (EditText) findViewById(R.id.passwordsignup);
//        CharSequence writtenData2 = editText2.getText();
//        outstate.putCharSequence("mydata1", writtenData2);
//        EditText editText3 = (EditText) findViewById(R.id.signuprepassword);
//        CharSequence writtenData3 = editText3.getText();
//        outstate.putCharSequence("mydata2", writtenData3);
////        EditText editText4=(EditText) findViewById(R.id.height);
////        CharSequence writtenData4=editText4.getText();
////        outstate.putCharSequence("mydata3",writtenData4);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        CharSequence storeddata = savedInstanceState.getCharSequence("mysavedata");
//        EditText myedittext = (EditText) findViewById(R.id.signupemail);
//        myedittext.setText(storeddata);
//        CharSequence storeddata1 = savedInstanceState.getCharSequence("mydata");
//        EditText myedittext1 = (EditText) findViewById(R.id.signupusername);
//        myedittext1.setText(storeddata1);
//        CharSequence storeddata2 = savedInstanceState.getCharSequence("mydata1");
//        EditText myedittext2 = (EditText) findViewById(R.id.passwordlogin);
//        myedittext2.setText(storeddata2);
//        CharSequence storeddata3 = savedInstanceState.getCharSequence("mydata2");
//        EditText myedittext3 = (EditText) findViewById(R.id.signuprepassword);
//        myedittext3.setText(storeddata3);
////        CharSequence storeddata4=savedInstanceState.getCharSequence("mydata3");
////        EditText myedittext4=(EditText)  findViewById(R.id.height);
////        myedittext1.setText(storeddata4);
//
//    }

}