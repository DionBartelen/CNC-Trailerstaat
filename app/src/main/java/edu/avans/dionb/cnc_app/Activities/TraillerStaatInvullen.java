package edu.avans.dionb.cnc_app.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import edu.avans.dionb.cnc_app.Activities.Entity.Trailer;
import edu.avans.dionb.cnc_app.Activities.Entity.TrailerStaat;
import edu.avans.dionb.cnc_app.R;

public class TraillerStaatInvullen extends AppCompatActivity {

    Spinner nummerSpinner;
    Spinner gmpSpinner;
    Spinner gridSpinner;
    EditText opmerkingText;
    ArrayAdapter<Integer> nummerAdapter;
    ArrayAdapter<String> gmpAdapter;
    ArrayAdapter<String> gridAdapter;
    String datum = "";
    boolean shouldSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailler_staat_invullen);


        nummerSpinner = (Spinner) findViewById(R.id.traillerstaat_trailernummer);
        gmpSpinner = (Spinner) findViewById(R.id.trailerstaat_gmp);
        gridSpinner = (Spinner) findViewById(R.id.trailerstaat_grid);
        opmerkingText = (EditText) findViewById(R.id.trailerstaat_opmerking);
        Button saveButton = (Button) findViewById(R.id.trailerstaat_opslaanbutton);


        nummerAdapter = new ArrayAdapter<Integer>(this, R.layout.string_spinner, R.id.spinner_gmp);
        gmpAdapter = new ArrayAdapter<String>(this, R.layout.string_spinner, R.id.spinner_gmp);
        gridAdapter = new ArrayAdapter<String>(this, R.layout.string_spinner, R.id.spinner_gmp);

        String[] arrayGMP = new String[] {"Niet nodig", "Vol", "Gevuld", "Leeg"};
        gmpAdapter.addAll(arrayGMP);
        gmpSpinner.setAdapter(gmpAdapter);

        String[] arrayGrid = new String[] {"Vol", "Gevuld", "Leeg"};
        gridAdapter.addAll(arrayGrid);
        gridSpinner.setAdapter(gridAdapter);

        nummerSpinner.setAdapter(nummerAdapter);
        getData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStaat();
            }
        });

        try {
            nummerSpinner.setSelection(nummerAdapter.getPosition(getIntent().getExtras().getInt("nummer")));
            gridSpinner.setSelection(gridAdapter.getPosition(getIntent().getExtras().getString("grid")));
            gmpSpinner.setSelection(gmpAdapter.getPosition(getIntent().getExtras().getString("gmp")));
            opmerkingText.setText(getIntent().getExtras().getString("opmerking"));
            datum = getIntent().getExtras().getString("datum");
            shouldSave = getIntent().getExtras().getBoolean("shouldSave");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nummerAdapter.clear();
                Map<String, Object> allItems = (HashMap<String, Object>)dataSnapshot.getValue();
                if(allItems != null) {
                    for (Map.Entry<String, Object> entry : allItems.entrySet()) {
                        HashMap<String, Object> trailer = (HashMap<String, Object>) entry.getValue();
                        nummerAdapter.add(Integer.valueOf(String.valueOf((long)trailer.get("trailerNummer"))));
                    }
                }
                nummerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(shouldSave) {
            saveStaat();
        }
        super.onBackPressed();
    }

    public void saveStaat() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        if(datum.equals("")) {
            datum =Calendar.getInstance().getTime().toString();
        }
        TrailerStaat staat = new TrailerStaat((Integer)nummerSpinner.getSelectedItem(), (String)gridSpinner.getSelectedItem(), (String)gmpSpinner.getSelectedItem(), opmerkingText.getText().toString(), datum);
        ref.child("Trailer " + (Integer)nummerSpinner.getSelectedItem()).child("Staat " + datum).setValue(staat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Trailerstaat toegevoegd", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Trailerstaat niet toegevoegd: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
