package com.example.projeto;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
public class HomeFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private ArrayList<Restaurant> items;
    private ProgressBar progressBar;
    public HomeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recylcer_view);
        items = new ArrayList<Restaurant>();
        progressBar = view.findViewById(R.id.homeProgressBar);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                db.collection("restaurants").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        Log.d("dados", String.valueOf(document.getGeoPoint("location").getLongitude()));
                                        Log.d("dados", String.valueOf(document.getGeoPoint("location").getLatitude()));
                                        Restaurant restaurant = new Restaurant(
                                                document.get("name").toString(),
                                                document.get("address").toString(),
                                                document.get("photo").toString(),
                                                Double.parseDouble(document.get("rating").toString()),
                                                document.get("description").toString(),
                                                document.get("category").toString(),
                                                document.get("descriptionDetails").toString(),
                                                document.getGeoPoint("location").getLatitude(),
                                                document.getGeoPoint("location").getLongitude()

                                        );
                                        items.add(restaurant);
                                    }
                                    adapter = new RestaurantAdapter(getActivity(), items, HomeFragment.this);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(adapter);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
            }
        });
        return view;
    }

    @Override
    public void onItemClick(Restaurant restaurant) {
        Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
        intent.putExtra("restaurant", restaurant);
        startActivity(intent);
    }
}