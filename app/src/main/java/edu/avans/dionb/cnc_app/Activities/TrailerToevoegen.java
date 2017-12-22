package edu.avans.dionb.cnc_app.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.avans.dionb.cnc_app.Activities.Entity.Trailer;
import edu.avans.dionb.cnc_app.R;

public class TrailerToevoegen extends AppCompatActivity  {

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_toevoegen);

        EditText nummerText = (EditText) findViewById(R.id.input_trailernummer);
        EditText kentekenText = (EditText) findViewById(R.id.input_kenteken);
        String[] arrayGMP = new String[] {"Nee", "Ja"};

        try {
            Integer nummer = getIntent().getExtras().getInt("nummer");
            nummerText.setText(nummer + "");
            String kenteken = getIntent().getExtras().getString("kenteken");
            kentekenText.setText(kenteken);
            String gmp = getIntent().getExtras().getString("gmp");
            if (gmp.equals("Ja")) {
                arrayGMP = new String[]{"Ja", "Nee"};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reference = FirebaseDatabase.getInstance().getReference();

        final Spinner gmpInput = (Spinner) findViewById(R.id.input_GMP);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.string_spinner, R.id.spinner_gmp);
        adapter.addAll(arrayGMP);
        gmpInput.setAdapter(adapter);

        Button voegToe = (Button) findViewById(R.id.button_voegTraillerToe);
        voegToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer traillerNummer = Integer.valueOf(((EditText) findViewById(R.id.input_trailernummer)).getText().toString());
                Trailer.GMP gmp = Trailer.GMP.valueOf(gmpInput.getSelectedItem().toString());
                String kenteken = ((EditText) findViewById(R.id.input_kenteken)).getText().toString();
                Trailer t = new Trailer(traillerNummer, kenteken, gmp);
                reference.child("Trailer " + t.getTrailerNummer()).setValue(t).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Trailer toegevoegd", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Trailer niet toegevoegd: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
