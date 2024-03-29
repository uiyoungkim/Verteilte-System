package buchungsdienste;

import broker.BuchungsAnfrage;
import broker.BuchungsAntwort;
public interface HotelBuchungsService {
    BuchungsAntwort bookHotel (BuchungsAnfrage anfrage);
}
