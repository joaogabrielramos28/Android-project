package com.example.projeto;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterStep01Activity extends AppCompatActivity {
    private EditText email,age,name,password;
    private ProgressBar progressBar;
    public RegisterState formState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step01);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AnotherMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 33);
        progressAnimator.setDuration(1000);
        progressAnimator.start();

        email = findViewById(R.id.CityText);
        age = findViewById(R.id.AddressText);
        name = findViewById(R.id.StateText);
        password = findViewById(R.id.PasswordText);


        View.OnFocusChangeListener hideKeyboardListener = (v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        };

        email.setOnFocusChangeListener(hideKeyboardListener);
        name.setOnFocusChangeListener(hideKeyboardListener);
        age.setOnFocusChangeListener(hideKeyboardListener);
        password.setOnFocusChangeListener(hideKeyboardListener);


    }


    public void goBack(View view){
        this.finish();
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void onNextStep(View view){

        boolean nameSize,ageSize,emailSize,passwordSize;
        nameSize = name.getText().toString().length() == 0;
        ageSize = age.getText().toString().length() == 0;
        emailSize = email.getText().toString().length() == 0;
        passwordSize = password.getText().toString().length() == 0;



        if(nameSize || ageSize || emailSize || passwordSize){
            Toast.makeText(this, "Preencha todos os campos para avançar", Toast.LENGTH_SHORT).show();
            return;
        }
            formState = new RegisterState(name.getText().toString(),email.getText().toString(),Integer.parseInt(age.getText().toString()),password.getText().toString());
            Intent intent = new Intent(RegisterStep01Activity.this, RegisterStep02Activity.class);

            intent.putExtra("data",formState);

            startActivity(intent);



    }




}