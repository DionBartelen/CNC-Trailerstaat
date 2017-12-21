package edu.avans.dionb.cnc_app.Activities.Other;

/**
 * Created by dionb on 3-11-2017.
 */

public class SQLquerys {
    public static String TrailerStaatQuery() {
        return "";
    }

    public static String TrailerToevoegenQuery(int trailernummer, String gmp, String kenteken) {
        return "INSERT INTO Trailers VALUES (" + trailernummer + ", '" + gmp + "', '" + kenteken + "');";
    }
}
