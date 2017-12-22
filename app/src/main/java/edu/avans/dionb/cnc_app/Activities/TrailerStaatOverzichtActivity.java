package edu.avans.dionb.cnc_app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.avans.dionb.cnc_app.Activities.Adapter.RecyclerViewAdapter;
import edu.avans.dionb.cnc_app.Activities.Entity.Trailer;
import edu.avans.dionb.cnc_app.R;

public class TrailerStaatOverzichtActivity extends AppCompatActivity implements RecyclerView.OnClickListener {

    RecyclerView listRecycler;
    List<Trailer> allTrailers;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_staat_overzicht);

        allTrailers = new ArrayList<>();
        listRecycler = (RecyclerView) findViewById(R.id.overzicht_recyclerview);
        listRecycler.setHasFixedSize(false);
        listRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(getApplicationContext(), allTrailers, this);
        listRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getData();
    }

    public void getData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allTrailers.clear();
                Map<String, Object> allItems = (HashMap<String, Object>)dataSnapshot.getValue();
                if(allItems != null) {
                    for (Map.Entry<String, Object> entry : allItems.entrySet()) {
                        HashMap<String, Object> trailer = (HashMap<String, Object>) entry.getValue();
                        allTrailers.add(new Trailer(Integer.valueOf(String.valueOf((long)trailer.get("trailerNummer"))), (String)trailer.get("kenteken"), Trailer.GMP.valueOf((String)trailer.get("gmp"))));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int itemPosition = listRecycler.getChildLayoutPosition(v);
        Trailer clickedTrailer = allTrailers.get(itemPosition);
        Toast.makeText(getApplicationContext(), "Op trailer geklikt", Toast.LENGTH_LONG).show();
    }
}
