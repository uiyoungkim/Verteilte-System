package buchungsdienste;

import java.util.HashMap;
import java.util.Map;

public class FlugVerfuegbarkeit {
    private static final Map<String, Integer> verfuegbareFluege = new HashMap<>();

    static {
        // Beispielhafte Initialisierung
        verfuegbareFluege.put("LH123", 100); // Flugnummer als Schlüssel
        verfuegbareFluege.put("FliegtBeiSonne", 1);
        verfuegbareFluege.put("StürzNieAb" , 20);
    }

    // Methode zur Prüfung der Verfügbarkeit
    public static boolean checkVerfuegbarkeit(String flugNummer) {
        return verfuegbareFluege.containsKey(flugNummer) && verfuegbareFluege.get(flugNummer) > 0;
    }

    // Methode zur Verringerung der verfügbaren Plätze bei erfolgreicher Buchung
    public static void bucheFlug(String flugNummer) {
        System.out.println("Vor der Buchung verfügbar: " + verfuegbareFluege.get(flugNummer));
        if (verfuegbareFluege.containsKey(flugNummer) && verfuegbareFluege.get(flugNummer) > 0) {
            verfuegbareFluege.put(flugNummer, verfuegbareFluege.get(flugNummer) - 1);
        }
        System.out.println("Nach der Buchung verfügbar: " + verfuegbareFluege.get(flugNummer));
    }
}