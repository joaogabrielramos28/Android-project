package com.example.projeto;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class ReservationsFragment extends Fragment implements OnItemClickListenerReservation {
    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private ArrayList<Reservation> items;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ReservationsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        recyclerView = view.findViewById(R.id.recylcer_view_reservations);
        items = new ArrayList<Reservation>();
        progressBar = view.findViewById(R.id.homeProgressBar);
        mAuth = FirebaseAuth.getInstance();
        db.collection("reservations").whereEqualTo("userId", mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Task<DocumentSnapshot>> restaurantTasks = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Reservation reservation = new Reservation(
                                        document.get("userId").toString(),
                                        document.get("day").toString(),
                                        document.get("hour").toString(),
                                        document.getDocumentReference("restaurant"),
                                        document.getId()
                                );
                                items.add(reservation);

                                DocumentReference restaurantRef = reservation.getRestaurantReference();
                                if (restaurantRef != null) {
                                    Task<DocumentSnapshot> restaurantTask = restaurantRef.get();
                                    restaurantTasks.add(restaurantTask);

                                    restaurantTask.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {
                                                Restaurant restaurant = new Restaurant(
                                                        documentSnapshot.get("name").toString(),
                                                        documentSnapshot.get("address").toString(),
                                                        documentSnapshot.get("photo").toString(),
                                                        Double.parseDouble(documentSnapshot.get("rating").toString()),
                                                        documentSnapshot.get("description").toString(),
                                                        documentSnapshot.get("category").toString(),
                                                        documentSnapshot.get("descriptionDetails").toString(),
                                                        documentSnapshot.getGeoPoint("location").getLatitude(),
                                                        documentSnapshot.getGeoPoint("location").getLongitude(),
                                                        documentSnapshot.getId()
                                                );
                                                reservation.setRestaurant(restaurant);
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                                }
                            }
                            Tasks.whenAllComplete(restaurantTasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                                @Override
                                public void onComplete(@NonNull Task<List<Task<?>>> task) {
                                    adapter = new ReservationAdapter(getActivity(), items,ReservationsFragment.this);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(adapter);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Ocorreu um erro ao listar suas reservas", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return view;
    }

    @Override
    public void onItemClick(Reservation reservation) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                db.collection("reservations").document(reservation.getId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Integer pos = items.indexOf(reservation);
                                items.removeIf(item -> item.getId().equals(reservation.getId()));
                                adapter.notifyItemRemoved(pos);
                            }
                        });
            }
        });
    }
}
