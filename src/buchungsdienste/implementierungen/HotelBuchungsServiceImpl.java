package buchungsdienste.implementierungen;

import buchungsdienste.HotelBuchungsService;
import broker.BuchungsAnfrage;
import broker.BuchungsAntwort;

public class HotelBuchungsServiceImpl implements HotelBuchungsService {

    @Override
    public BuchungsAntwort bookHotel(BuchungsAnfrage anfrage) {
        return new BuchungsAntwort(anfrage.getId(), true, "Hotel erfolgreich gebucht!");
    }
}
