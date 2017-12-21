package edu.avans.dionb.cnc_app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.avans.dionb.cnc_app.Activities.Other.*;
import edu.avans.dionb.cnc_app.R;

public class TrailerToevoegen extends AppCompatActivity implements WebTaskListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_toevoegen);

        final Spinner gmpInput = (Spinner) findViewById(R.id.input_GMP);
        String[] arrayGMP = new String[] {"Nee", "Ja"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.string_spinner, R.id.spinner_gmp);
        adapter.addAll(arrayGMP);
        gmpInput.setAdapter(adapter);

        Button voegToe = (Button) findViewById(R.id.button_voegTraillerToe);
        voegToe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String traillerNummer = ((EditText) findViewById(R.id.input_trailernummer)).getText().toString();
                String gmp = gmpInput.getSelectedItem().toString();
                String kenteken = ((EditText) findViewById(R.id.input_kenteken)).getText().toString();
                String query = SQLquerys.TrailerToevoegenQuery(Integer.valueOf(traillerNummer), gmp, kenteken);
                VoerQueryUit vqu = new VoerQueryUit(TrailerToevoegen.this);
                String[] params = new String[] {"http://dion-bartelen.000webhostapp.com/query.php?id=" + query};
                vqu.execute(params);
            }
        });
    }

    @Override
    public void TaskDone(String message) {
        Toast.makeText(getApplicationContext(), "Post gelukt: " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void TaskError(String message) {
        Toast.makeText(getApplicationContext(), "Post mislukt: " + message, Toast.LENGTH_LONG).show();
    }
}
