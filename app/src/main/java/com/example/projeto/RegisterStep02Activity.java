package com.example.projeto;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterStep02Activity extends AppCompatActivity {
    private EditText cardNumber,cardName,cardExpire,cardCode;
    private TextView cardNumberText,cardNameText,cardExpireText,cardCodeText;
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














    }






}