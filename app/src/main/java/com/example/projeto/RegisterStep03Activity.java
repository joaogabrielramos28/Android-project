package com.example.projeto;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterStep03Activity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText state,city,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step03);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AnotherMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        progressAnimator.setDuration(1000);
        progressAnimator.start();

        state = findViewById(R.id.StateText);
        city = findViewById(R.id.CityText);
        address = findViewById(R.id.AddressText);


        View.OnFocusChangeListener hideKeyboardListener = (v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        };

        state.setOnFocusChangeListener(hideKeyboardListener);
        city.setOnFocusChangeListener(hideKeyboardListener);
        address.setOnFocusChangeListener(hideKeyboardListener);

    }


    public void onNextStep(View view){
        boolean stateSize,citySize,addressSize;
        stateSize = state.getText().toString().length() == 0;
        citySize = city.getText().toString().length() == 0;
        addressSize = address.getText().toString().length() == 0;

        if(stateSize || citySize || addressSize){
            Toast.makeText(this, "Preencha todos os campos para avançar", Toast.LENGTH_SHORT).show();
            return;
        }



        RegisterState registerState = (RegisterState) getIntent().getSerializableExtra("data");
        registerState.setState(state.getText().toString());
        registerState.setCity(city.getText().toString());
        registerState.setAddress(address.getText().toString());
        Intent intent = new Intent(RegisterStep03Activity.this, ResumeActivity.class);

        intent.putExtra("data",registerState);

        startActivity(intent);
    }




    public void goBack(View view){
        this.finish();
    }


    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

}