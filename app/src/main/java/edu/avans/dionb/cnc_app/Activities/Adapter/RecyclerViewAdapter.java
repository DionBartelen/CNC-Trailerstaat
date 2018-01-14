package edu.avans.dionb.cnc_app.Activities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.avans.dionb.cnc_app.Activities.Entity.Trailer;
import edu.avans.dionb.cnc_app.Activities.TrailerToevoegen;
import edu.avans.dionb.cnc_app.R;

/**
 * Created by dionb on 22-12-2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Trailer> allTrailers;
    RecyclerView.OnClickListener listener;

    public RecyclerViewAdapter(Context context, List<Trailer> trailers,  RecyclerView.OnClickListener listener) {
        this.context = context;
        allTrailers = trailers;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setNummer(allTrailers.get(position).getTrailerNummer() + "");
        holder.setKenteken(allTrailers.get(position).getKenteken());
        holder.setGMP(allTrailers.get(position).getGmp().toString());
    }

    @Override
    public int getItemCount() {
        return allTrailers.size();
    }

    public void setTrailers(List<Trailer> trailers) {
        this.allTrailers = trailers;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nummer;
        TextView kenteken;
        TextView gmp;

        public ViewHolder(View itemView) {
            super(itemView);
            nummer = (TextView) itemView.findViewById(R.id.recycler_row_trailernummer);
            kenteken = (TextView) itemView.findViewById(R.id.recycler_row_kenteken);
            gmp = (TextView) itemView.findViewById(R.id.recycler_row_gmp);
            itemView.setOnClickListener(listener);
            ImageButton delete = (ImageButton) itemView.findViewById(R.id.recycler_row_delete);
            ImageButton edit = (ImageButton) itemView.findViewById(R.id.recycler_row_edit);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    int itemPosition = getAdapterPosition();
                    Trailer clickedTrailer = allTrailers.get(itemPosition);
                    ref.child("Trailer " + clickedTrailer.getTrailerNummer()).removeValue();
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getAdapterPosition();
                    Trailer clickedTrailer = allTrailers.get(itemPosition);
                    Intent i = new Intent(context, TrailerToevoegen.class);
                    i.putExtra("nummer", clickedTrailer.getTrailerNummer());
                    i.putExtra("kenteken", clickedTrailer.getKenteken());
                    i.putExtra("gmp", clickedTrailer.getGmp().toString());
                    i.putExtra("saveOnExit", true);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Trailer " + clickedTrailer.getTrailerNummer()).removeValue();
                    context.startActivity(i);
                }
            });
        }

        public void setNummer(String nummer) {
            this.nummer.setText("Trailer: " + nummer);
        }

        public void setKenteken(String kenteken) {
            this.kenteken.setText(kenteken);
        }

        public void setGMP(String gmp) {
            this.gmp.setText("GMP: " + gmp);
        }
    }
}
