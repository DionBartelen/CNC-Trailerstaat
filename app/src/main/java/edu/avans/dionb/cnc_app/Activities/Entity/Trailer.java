package edu.avans.dionb.cnc_app.Activities.Entity;

/**
 * Created by dionb on 21-12-2017.
 */

public class Trailer {
    public enum GMP {
        Ja, Nee
    }

    private Integer trailerNummer;
    private String kenteken;
    private GMP gmp;

    public Trailer(Integer trailerNummer, String kenteken, GMP gmp) {
        this.trailerNummer = trailerNummer;
        this.kenteken = kenteken;
        this.gmp = gmp;
    }

    public Integer getTrailerNummer() {
        return trailerNummer;
    }

    public void setTrailerNummer(Integer trailerNummer) {
        this.trailerNummer = trailerNummer;
    }

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public GMP getGmp() {
        return gmp;
    }

    public void setGmp(GMP gmp) {
        this.gmp = gmp;
    }
}
