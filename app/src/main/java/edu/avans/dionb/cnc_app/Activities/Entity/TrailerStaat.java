package edu.avans.dionb.cnc_app.Activities.Entity;

import android.app.PendingIntent;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dionb on 22-12-2017.
 */

public class TrailerStaat implements Comparable<TrailerStaat>{

    private Integer nummer;
    private String grid;
    private String gmp;
    private String opmerking;
    private String datum;

    public TrailerStaat(Integer nummer, String grid, String gmp, String opmerking, String datum) {
        this.nummer = nummer;
        this.grid = grid;
        this.gmp = gmp;
        this.opmerking = opmerking;
        this.datum = datum;
    }

    public Integer getNummer() {
        return nummer;
    }

    public void setNummer(Integer nummer) {
        this.nummer = nummer;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getGmp() {
        return gmp;
    }

    public void setGmp(String gmp) {
        this.gmp = gmp;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @Override
    public int compareTo(@NonNull TrailerStaat o) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            Date dateOther = sdf.parse(o.datum);
            Date dateThis = sdf.parse(datum);
            if(dateThis.before(dateOther)) {
                return 1;
            }
            return - 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
