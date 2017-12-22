package edu.avans.dionb.cnc_app.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import edu.avans.dionb.cnc_app.R;

public class StartMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        ImageButton staatTraillerButton = (ImageButton) findViewById(R.id.imagebutton_invullen);
        staatTraillerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TraillerStaatInvullen.class);
                startActivity(i);
            }
        });

        ImageButton traillerToevoegen = (ImageButton) findViewById(R.id.imagebutton_trailertoevoegen);
        traillerToevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TrailerToevoegen.class);
                startActivity(i);
            }
        });

        ImageButton staatTrailersbekijken = (ImageButton) findViewById(R.id.imagebutton_trailerstaatbekijken);
        staatTrailersbekijken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TrailerStaatOverzichtActivity.class);
                startActivity(i);
            }
        });
    }
}
