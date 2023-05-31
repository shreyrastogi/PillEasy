package com.mc2022.template.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.R;
import com.mc2022.template.Utilities.SnackBarUtility;

public class LoginActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "login_check";
    EditText nameuser, passworduser, emailuser;
    Button btnLogin;
    SQLiteHelper myDB;
    TextInputLayout outlinedTextField1, outlinedTextField2, outlinedTextField3;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailuser = (EditText) findViewById(R.id.emaillogin);
        nameuser = (EditText) findViewById(R.id.usernamelogin);
        passworduser = (EditText) findViewById(R.id.passwordlogin);
        btnLogin = (Button) findViewById(R.id.loginBtn);
        myDB = new SQLiteHelper(this);

        sp = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sp.edit();

        btnLogin.setOnClickListener(view -> {
            String em = emailuser.getText().toString();
            String user = nameuser.getText().toString();
            String pass = passworduser.getText().toString();
            if (user.equals("") || pass.equals("") || em.equals("")) {
                SnackBarUtility.ShowSnackbar("All fields are required", findViewById(R.id.login_layout));
            } else {
                if (myDB.checkemailnamepassword(em, user, pass)) {
                    SnackBarUtility.ShowSnackbar("Login Successful", findViewById(R.id.login_layout));
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("Status", "1");
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    SnackBarUtility.ShowSnackbar("Invalid credentials", findViewById(R.id.login_layout));
                }
            }
        });

        outlinedTextField1 = findViewById(R.id.loginemailoutlinedTextField5);
        outlinedTextField2 = findViewById(R.id.loginusernameoutlinedTextField6);
        outlinedTextField3 = findViewById(R.id.loginpasswordoutlinedTextField7);

        emailuser.setOnFocusChangeListener((view, b) -> {
            if (emailuser.getText().toString().isEmpty()) {
                outlinedTextField1.setErrorEnabled(true);
                outlinedTextField1.setError("*Required");
            } else {
                outlinedTextField1.setErrorEnabled(false);
            }
        });

        nameuser.setOnFocusChangeListener((view, b) -> {
            if (nameuser.getText().toString().isEmpty()) {
                outlinedTextField2.setErrorEnabled(true);
                outlinedTextField2.setError("*Required");
            } else {
                outlinedTextField2.setErrorEnabled(false);
            }
        });

        passworduser.setOnFocusChangeListener((view, b) -> {
            if (passworduser.getText().toString().isEmpty()) {
                outlinedTextField3.setErrorEnabled(true);
                outlinedTextField3.setError("*Required");
            } else {
                outlinedTextField3.setErrorEnabled(false);
            }
        });

        emailuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (emailuser.getText().toString().isEmpty()) {
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

        nameuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (nameuser.getText().toString().isEmpty()) {
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

        passworduser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (passworduser.getText().toString().isEmpty()) {
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

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outstate) {
        super.onSaveInstanceState(outstate);
        EditText editText = (EditText) findViewById(R.id.emaillogin);
        CharSequence writtenData = editText.getText();
        outstate.putCharSequence("mysavedata", writtenData);
        EditText editText1 = (EditText) findViewById(R.id.usernamelogin);
        CharSequence writtenData1 = editText1.getText();
        outstate.putCharSequence("mydata", writtenData1);
        EditText editText2 = (EditText) findViewById(R.id.passwordlogin);
        CharSequence writtenData2 = editText2.getText();
        outstate.putCharSequence("mydata1", writtenData2);
//        EditText editText3=(EditText) findViewById(R.id.repassword);
//        CharSequence writtenData3=editText3.getText();
//        outstate.putCharSequence("mydata2",writtenData3);
//        EditText editText4=(EditText) findViewById(R.id.height);
//        CharSequence writtenData4=editText4.getText();
//        outstate.putCharSequence("mydata3",writtenData4);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CharSequence storeddata = savedInstanceState.getCharSequence("mysavedata");
        EditText myedittext = (EditText) findViewById(R.id.emaillogin);
        myedittext.setText(storeddata);
        CharSequence storeddata1 = savedInstanceState.getCharSequence("mydata");
        EditText myedittext1 = (EditText) findViewById(R.id.usernamelogin);
        myedittext1.setText(storeddata1);
        CharSequence storeddata2 = savedInstanceState.getCharSequence("mydata1");
        EditText myedittext2 = (EditText) findViewById(R.id.passwordlogin);
        myedittext2.setText(storeddata2);
//        CharSequence storeddata3=savedInstanceState.getCharSequence("mydata2");
//        EditText myedittext3=(EditText)  findViewById(R.id.repassword);
//        myedittext1.setText(storeddata3);
//        CharSequence storeddata4=savedInstanceState.getCharSequence("mydata3");
//        EditText myedittext4=(EditText)  findViewById(R.id.height);
//        myedittext1.setText(storeddata4);
    }
}