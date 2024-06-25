package com.example.projeto;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResumeActivity extends AppCompatActivity {



    private TextView name,email,age,cardNumber,cardName,cardExpires,cardCode,state,city,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resume);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RegisterState registerState = (RegisterState) getIntent().getSerializableExtra("data");

        name = findViewById(R.id.NameValue);
        email = findViewById(R.id.EmailValue);
        age = findViewById(R.id.AgeValue);

        cardNumber = findViewById(R.id.NumberValue);
        cardName = findViewById(R.id.CardNameValue);
        cardExpires = findViewById(R.id.CardExpiresValue);
        cardCode = findViewById(R.id.CardCodeValue);

        state = findViewById(R.id.StateValue);
        city = findViewById(R.id.CityValue);
        address = findViewById(R.id.AddressValue);



        name.setText(registerState.getName());
        email.setText(registerState.getEmail());
        age.setText(registerState.getAge().toString());

        cardNumber.setText(registerState.getCardNumber());
        cardName.setText(registerState.getCardName());
        cardExpires.setText(registerState.getCardExpires());
        cardCode.setText(registerState.getCardCode());

        state.setText(registerState.getState());
        city.setText(registerState.getCity());
        address.setText(registerState.getAddress());










    }
}