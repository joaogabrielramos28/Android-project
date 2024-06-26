package com.example.projeto;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoggedHomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loggedhome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AnotherMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(getString(R.string.preference_file_key), "");

        if(result.length() > 0){
            Intent intent = new Intent(LoggedHomeActivity.this,HomeActivity.class);
            startActivity(intent);
        }


    }


    public void goToLogin(View view){
        Intent intent = new Intent(LoggedHomeActivity.this, LoginActivity.class);

        startActivity(intent);
    }

    public void goToRegister(View view){
        Intent intent = new Intent(LoggedHomeActivity.this, RegisterStep01Activity.class);

        startActivity(intent);
    }

}