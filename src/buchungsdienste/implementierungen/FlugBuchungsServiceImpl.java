package buchungsdienste.implementierungen;

import buchungsdienste.FlugBuchungsService;
import broker.BuchungsAnfrage;
import broker.BuchungsAntwort;
public class FlugBuchungsServiceImpl implements FlugBuchungsService {
    @Override
    public BuchungsAntwort bookflight(BuchungsAnfrage anfrage) {
        return new BuchungsAntwort(anfrage.getId(),true,"Flug erfolgreich gebucht!");
    }
}
