package broker;

import buchungsdienste.FlugBuchungsService;
import buchungsdienste.HotelBuchungsService;

public class ReiseBroker {
    private HotelBuchungsService hotelService;
    private FlugBuchungsService flugService;

    public ReiseBroker(HotelBuchungsService hotelService, FlugBuchungsService flugService){
        this.hotelService = hotelService;
        this.flugService = flugService;
    }

    public void verarbeiteteBuchungsAnfrage(BuchungsAnfrage anfrage){
        //Verarbeite die Hotelbuchung
        BuchungsAntwort hotelAntwort = hotelService.bookHotel(anfrage);
        if (!hotelAntwort.isSuccess()){
            System.out.println("Hotelbuchung fehlgeschlagen: " + hotelAntwort.getMessage());
            return;
        }

        // Verarbeite die Flugbuchung
        BuchungsAntwort flugAntwort = flugService.bookflight(anfrage);
        if (!flugAntwort.isSuccess()) {
            System.out.println("Flugbuchung fehlgeschlagen: " + flugAntwort.getMessage());
            return;
        }

        System.out.println("Reise erfolgreich gebucht: Juhu Ihre Hotel und Flug wurden erfolgreich gebucht");

    }

}
