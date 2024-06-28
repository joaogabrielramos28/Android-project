package com.example.projeto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private TextView name,email;
    private Button deleteAcc;
    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name =  view.findViewById(R.id.nameText);
        email = view.findViewById(R.id.emailText);
        deleteAcc = view.findViewById(R.id.deleteAcc);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(getString(R.string.preference_file_key), "");
        Gson gson = new Gson();
        Map<String, Object> item = gson.fromJson(result,Map.class);

        name.setText(item.get("name").toString());
        email.setText(mAuth.getCurrentUser().getEmail());

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(getActivity(),LoggedHomeActivity.class);
                                startActivity(intent);
                                editor.clear().commit();


                            }
                        });

                    }
                });
            }
        });



        return view;


    }



}