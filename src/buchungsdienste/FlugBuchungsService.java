package buchungsdienste;

import broker.BuchungsAnfrage;
import broker.BuchungsAntwort;
public interface FlugBuchungsService {
    BuchungsAntwort bookflight (BuchungsAnfrage anfrage);
}
