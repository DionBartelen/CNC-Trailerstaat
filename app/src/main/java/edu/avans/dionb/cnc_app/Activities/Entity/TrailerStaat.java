package edu.avans.dionb.cnc_app.Activities.Entity;

/**
 * Created by dionb on 22-12-2017.
 */

public class TrailerStaat {

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
}
