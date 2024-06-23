package com.example.projeto;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    private LinearLayout containerLayout;
    private Button previousButton;
    private Button nextButton;
    private LinearLayout stepIndicatorsLayout;
    private int currentStep = 0;
    private View[] steps;
    private TextView[] stepIndicators;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AnotherMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View step01 = inflater.inflate(R.layout.activity_step01,containerLayout,false);




        containerLayout = findViewById(R.id.container_layout);
        previousButton = findViewById(R.id.previous_button);
        nextButton = findViewById(R.id.next_button);
        stepIndicatorsLayout = findViewById(R.id.step_indicators_layout);
        steps = new View[]{
                LayoutInflater.from(this).inflate(R.layout.activity_step01, containerLayout, false),
                LayoutInflater.from(this).inflate(R.layout.activity_step02, containerLayout, false),
                LayoutInflater.from(this).inflate(R.layout.activity_loggedhome, containerLayout, false)
        };
        initializeStepIndicators();
        showCurrentStep();
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep > 0) {
                    currentStep--;
                    showCurrentStep();
                }
            }
        });

        containerLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("oi");
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });





        stepIndicatorsLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });





        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep < steps.length - 1) {
                    currentStep++;
                    showCurrentStep();
                } else {
                    submitForm();
                }
            }
        });
    }
    private void addArrowIndicator(LinearLayout stepIndicatorsLayout) {
        ImageView arrow = new ImageView(this );
        arrow.setImageResource(R.drawable.icon_caret_right);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                48,48
        );
        params.gravity = Gravity.CENTER_VERTICAL;
        arrow.setLayoutParams(params);
        stepIndicatorsLayout.addView(arrow);
    }

    private void showCurrentStep() {
        containerLayout.removeAllViews();
        containerLayout.addView(steps[currentStep]);
        previousButton.setVisibility(currentStep > 0 ? View.VISIBLE : View.INVISIBLE);
        nextButton.setText(currentStep < steps.length - 1 ? "Próximo" : "Enviar");

        updateStepIndicators();
    }

    private void updateStepIndicators() {
        for (int i = 0; i < stepIndicators.length; i++) {
            if (i == currentStep) {
                stepIndicators[i].setBackgroundResource(R.drawable.circle_green);
            } else {
                stepIndicators[i].setBackgroundResource(R.drawable.circle_gray);
            }
        }
    }

    private void submitForm() {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }

    private void initializeStepIndicators() {
        stepIndicators = new TextView[steps.length];
        for (int i = 0; i < steps.length; i++) {
            TextView stepIndicator = new TextView(this);
            stepIndicator.setText(String.valueOf(i + 1));
            stepIndicator.setTextColor(Color.WHITE);
            stepIndicator.setTextSize(18);
            stepIndicator.setBackgroundResource(R.drawable.circle_gray);
            stepIndicator.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 0, 10, 0);
            stepIndicator.setLayoutParams(params);
            stepIndicatorsLayout.addView(stepIndicator);
            stepIndicators[i] = stepIndicator;

            if (i < steps.length - 1) {
                addArrowIndicator(stepIndicatorsLayout);
            }
        }
    }

}

