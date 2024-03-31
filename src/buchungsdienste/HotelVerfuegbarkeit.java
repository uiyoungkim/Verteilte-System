package buchungsdienste;

import java.util.HashMap;
import java.util.Map;

public class HotelVerfuegbarkeit {
    // Eine einfache In-Memory-Datenstruktur zur Repräsentation der Verfügbarkeit
    private static final Map<String, Integer> verfuegbareZimmer = new HashMap<>();

    static {
        verfuegbareZimmer.put("DHBW", 10);
        verfuegbareZimmer.put("UrlaubInDenBergen", 5);
        verfuegbareZimmer.put("FluchtAnDieSee", 1);
    }

    // Methode zur Prüfung der Verfügbarkeit
    public static boolean checkVerfuegbarkeit(String hotelName) {
        return verfuegbareZimmer.containsKey(hotelName) && verfuegbareZimmer.get(hotelName) > 0;
    }

    // Methode zur Verringerung der verfügbaren Zimmer bei erfolgreicher Buchung
    public static void bookRoom(String hotelName) {
        System.out.println("Vor der Buchung verfügbar: " + verfuegbareZimmer.get(hotelName));
        if (verfuegbareZimmer.containsKey(hotelName) && verfuegbareZimmer.get(hotelName) > 0) {
            verfuegbareZimmer.put(hotelName, verfuegbareZimmer.get(hotelName) - 1);
        }
        System.out.println("Nach der Buchung verfügbar: " + verfuegbareZimmer.get(hotelName));
    }

}
