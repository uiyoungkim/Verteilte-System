package buchungsdienste.implementierungen;

import buchungsdienste.HotelBuchungsService;
import broker.BuchungsAnfrage;
import broker.BuchungsAntwort;
import buchungsdienste.HotelVerfuegbarkeit;

public class HotelBuchungsServiceImpl implements HotelBuchungsService {

    @Override
    public BuchungsAntwort bookHotel(BuchungsAnfrage anfrage) {
        // Prüfe die Verfügbarkeit des angefragten Hotels
        if (HotelVerfuegbarkeit.checkVerfuegbarkeit(anfrage.getHotelName())) {
            // Buche das Zimmer, wenn verfügbar
            HotelVerfuegbarkeit.bookRoom(anfrage.getHotelName());
            return new BuchungsAntwort(anfrage.getId(), true, "Hotel erfolgreich gebucht!");
        } else {
            // Antwort im Falle, dass keine Zimmer verfügbar sind
            return new BuchungsAntwort(anfrage.getId(), false, "Hotelbuchung fehlgeschlagen: Keine Zimmer verfügbar.");
        }
    }
}