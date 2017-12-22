package edu.avans.dionb.cnc_app.Activities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.avans.dionb.cnc_app.Activities.Entity.Trailer;
import edu.avans.dionb.cnc_app.Activities.Entity.TrailerStaat;
import edu.avans.dionb.cnc_app.Activities.TrailerToevoegen;
import edu.avans.dionb.cnc_app.R;

/**
 * Created by dionb on 22-12-2017.
 */

public class StaatRecyclerViewAdapter extends RecyclerView.Adapter<StaatRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<TrailerStaat> allTrailers;
    RecyclerView.OnClickListener listener;

    public StaatRecyclerViewAdapter(Context context, List<TrailerStaat> trailers,  RecyclerView.OnClickListener listener) {
        this.context = context;
        allTrailers = trailers;
        this.listener = listener;
    }

    @Override
    public StaatRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_staat_row, parent, false);
        StaatRecyclerViewAdapter.ViewHolder vh = new StaatRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StaatRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setNummer(allTrailers.get(position).getNummer() + "");
        holder.setGrid(allTrailers.get(position).getGrid());
        holder.setGMP(allTrailers.get(position).getGmp());
        holder.setOpmerking(allTrailers.get(position).getOpmerking());
        holder.setDatum(allTrailers.get(position).getDatum());
        if(allTrailers.get(position).getGrid().equals("Leeg") || allTrailers.get(position).getGmp().equals("Leeg") || !allTrailers.get(position).getOpmerking().equals("")) {
            holder.setBGTError();
        }
    }

    @Override
    public int getItemCount() {
        return allTrailers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        Integer nummerValue;
        String datumValue;

        TextView datum;
        TextView nummer;
        TextView grid;
        TextView gmp;
        TextView opmerking;
        CardView cv;

        public ViewHolder(final View itemView) {
            super(itemView);
            nummer = (TextView) itemView.findViewById(R.id.staatoverview_nummer);
            grid = (TextView) itemView.findViewById(R.id.staatoverview_grid);
            gmp = (TextView) itemView.findViewById(R.id.staatoverview_gmp);
            datum = (TextView) itemView.findViewById(R.id.staatoverview_datum);
            opmerking = (TextView) itemView.findViewById(R.id.staatoverview_opmerking);
            cv = (CardView) itemView.findViewById(R.id.trailerstaat_cardview);
            itemView.setOnClickListener(listener);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Trailer " + nummerValue).child("Staat " + datumValue).removeValue();
                    return true;
                }
            });
        }

        public void setNummer(String nummer) {
            nummerValue = Integer.valueOf(nummer);
            this.nummer.setText("Trailer: " + nummer);
        }

        public void setGrid(String grid) {
            this.grid.setText("Grid: " + grid);
        }

        public void setGMP(String gmp) {
            this.gmp.setText("GMP: " + gmp);
        }

        public void setDatum(String datum) {
            datumValue = datum;
            this.datum.setText(datum);
        }

        public void setOpmerking(String opmerking) {
            this.opmerking.setText("Opmerking: " + opmerking);
        }

        public void setBGTError() {
            cv.setCardBackgroundColor(Color.argb(200, 255, 0, 0));
        }


    }
}
