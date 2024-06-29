package com.example.projeto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResumeActivity extends AppCompatActivity {
    private TextView name, email, age, cardNumber, cardName, cardExpires, cardCode, state, city, address;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;


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

        mAuth = FirebaseAuth.getInstance();

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


    public void SignUp(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd=ProgressDialog.show(ResumeActivity.this,"Carregando","Por favor aguarde",false);
                SharedPreferences sharedPref = ResumeActivity.this.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();

                RegisterState registerState = (RegisterState) getIntent().getSerializableExtra("data");
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("name", registerState.getName());
                userMap.put("age", registerState.getAge());
                userMap.put("state", registerState.getState());
                userMap.put("city", registerState.getCity());
                userMap.put("address", registerState.getAddress());
                userMap.put("cardCode", registerState.getCardCode());
                userMap.put("cardNumber", registerState.getCardNumber());
                userMap.put("cardExpires", registerState.getCardExpires());
                userMap.put("cardName", registerState.getCardName());

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                mAuth.createUserWithEmailAndPassword(registerState.getEmail(), registerState.getPassword())
                        .addOnCompleteListener(ResumeActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        userMap.put("id", user.getUid());

                                        db.collection("users")
                                                .add(userMap)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(ResumeActivity.this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(ResumeActivity.this, LoginActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        pd.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Trate falhas no Firestore
                                                        Toast.makeText(ResumeActivity.this, "Erro ao salvar dados do usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        pd.dismiss();
                                                    }
                                                });
                                    }
                                } else {
                                    // Trate falhas na criação do usuário
                                    Toast.makeText(ResumeActivity.this, "Erro ao criar usuário: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }
                            }
                        });
            }
        });
    }

}