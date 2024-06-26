package com.example.projeto;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterStep02Activity extends AppCompatActivity {
    private EditText cardNumber,cardName,cardExpire,cardCode;
    private TextView cardNumberText,cardNameText,cardExpireText,cardCodeText;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step02);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AnotherMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        progressBar = findViewById(R.id.progressBar);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 33, 66);
        progressAnimator.setDuration(1000);
        progressAnimator.start();


        cardCode = findViewById(R.id.CardCode);
        cardCodeText = findViewById(R.id.CardCodeText);


        cardName = findViewById(R.id.CardName);
        cardNameText = findViewById(R.id.CardNameText);


        cardNumber = findViewById(R.id.CardNumber);
        cardNumberText = findViewById(R.id.CardNumberText);

        cardExpire = findViewById(R.id.CardExpire);
        cardExpireText = findViewById(R.id.CardExpireText);

        TextWatcher maskTextWatcher = new MaskTextWatcher(cardNumber,cardNumberText);
        cardNumber.addTextChangedListener(maskTextWatcher);

        TextWatcher expiryDateTextWatcher = new ExpiryDateTextWatcher(cardExpire,cardExpireText);
        cardExpire.addTextChangedListener(expiryDateTextWatcher);


        cardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cardNameText != null) {
                    cardNameText.setText(s);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });
        cardCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cardCodeText != null) {
                    cardCodeText.setText(s);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        View.OnFocusChangeListener hideKeyboardListener = (v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        };

        cardNumber.setOnFocusChangeListener(hideKeyboardListener);
        cardName.setOnFocusChangeListener(hideKeyboardListener);
        cardExpire.setOnFocusChangeListener(hideKeyboardListener);
        cardCode.setOnFocusChangeListener(hideKeyboardListener);


    }


    public void onNextStep(View view){
        boolean cardCodeSize,cardExpireSize,cardNameSize,cardNumberSize;
        cardCodeSize = cardCode.getText().toString().length() == 0;
        cardExpireSize = cardExpire.getText().toString().length() ==0;
        cardNameSize = cardName.getText().toString().length() == 0;
        cardNumberSize = cardNumber.getText().toString().length() == 0;

        if(cardCodeSize || cardExpireSize || cardNameSize || cardNumberSize){
            Toast.makeText(this, "Preencha todos os campos para avançar", Toast.LENGTH_SHORT).show();
            return;
        }



        RegisterState registerState = (RegisterState) getIntent().getSerializableExtra("data");

        registerState.setCardCode(cardCode.getText().toString());
        registerState.setCardExpires(cardExpire.getText().toString());
        registerState.setCardName(cardName.getText().toString());
        registerState.setCardNumber(cardNumber.getText().toString());
        Intent intent = new Intent(RegisterStep02Activity.this, RegisterStep03Activity.class);


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