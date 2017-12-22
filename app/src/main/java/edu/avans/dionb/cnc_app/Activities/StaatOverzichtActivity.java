package edu.avans.dionb.cnc_app.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import java.util.Objects;

import edu.avans.dionb.cnc_app.Activities.Adapter.RecyclerViewAdapter;
import edu.avans.dionb.cnc_app.Activities.Adapter.StaatRecyclerViewAdapter;
import edu.avans.dionb.cnc_app.Activities.Entity.Trailer;
import edu.avans.dionb.cnc_app.Activities.Entity.TrailerStaat;
import edu.avans.dionb.cnc_app.R;

public class StaatOverzichtActivity extends AppCompatActivity implements RecyclerView.OnClickListener {

    RecyclerView recyclerView;
    List<TrailerStaat> allStati;
    StaatRecyclerViewAdapter adapter;
    Integer nummer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staat_overzicht);

        nummer = getIntent().getExtras().getInt("nummer");

        recyclerView = (RecyclerView) findViewById(R.id.staatoverzicht_recycler);
        allStati = new ArrayList<>();
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StaatRecyclerViewAdapter(getApplicationContext(), allStati, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getData();
    }

    public void getData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allStati.clear();
                Map<String, Object> allItems = (HashMap<String, Object>)dataSnapshot.getValue();
                if(allItems != null && nummer != null) {
                    for (Map.Entry<String, Object> entry : allItems.entrySet()) {
                        HashMap<String, Object> trailer = (HashMap<String, Object>) entry.getValue();
                        for(Map.Entry<String, Object> trailerEntry : trailer.entrySet()) {
                            if(trailerEntry.getKey().contains("Staat")) {
                                HashMap<String, Object> staat = (HashMap<String, Object>) trailerEntry.getValue();
                                if (Objects.equals(Integer.valueOf(String.valueOf((long) trailer.get("trailerNummer"))), nummer)) {
                                    allStati.add(new TrailerStaat(nummer, (String)staat.get("grid"), (String)staat.get("gmp"), (String)staat.get("opmerking"), (String)staat.get("datum")));
                                }
                            }
                        }
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
        int itemPosition = recyclerView.getChildLayoutPosition(v);
        TrailerStaat clickedTrailerStaat = allStati.get(itemPosition);
        Intent i = new Intent(this, TraillerStaatInvullen.class);
        i.putExtra("nummer", clickedTrailerStaat.getNummer());
        i.putExtra("grid", clickedTrailerStaat.getGrid());
        i.putExtra("gmp", clickedTrailerStaat.getGmp());
        i.putExtra("opmerking", clickedTrailerStaat.getOpmerking());
        i.putExtra("datum", clickedTrailerStaat.getDatum());
        i.putExtra("shouldSave", true);
        startActivity(i);

    }
}
