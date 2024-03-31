package buchungsdienste.implementierungen;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import buchungsdienste.HotelVerfuegbarkeit;

public class HotelBuchungsServiceServer {
    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(4445)) {
            System.out.println("Server gestartet, warte auf Anfragen...");
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                System.out.println("Anfrage erhalten.");

                String received = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println("Empfangene Daten: " + received);


                String hotelName = received.split("\"hotelName\":\"")[1].split("\"")[0];

                String response;

                // Prüfe die Verfügbarkeit und generiere die Antwort basierend darauf
                if (HotelVerfuegbarkeit.checkVerfuegbarkeit(hotelName)) {
                    // Angenommen, bucheZimmer() aktualisiert die Verfügbarkeit im HotelVerfuegbarkeit
                    HotelVerfuegbarkeit.bookRoom(hotelName);
                    response = String.format("{\"id\":\"%s\", \"erfolg\":true, \"nachricht\":\"Hotel '%s' gebucht.\"}",
                            "Buchungs-ID", hotelName);
                } else {
                    response = String.format("{\"id\":\"\", \"erfolg\":false, \"nachricht\":\"Keine Verfügbarkeit im Hotel '%s'.\"}",
                            hotelName);
                }

                byte[] responseBytes = response.getBytes();
                packet = new DatagramPacket(responseBytes, responseBytes.length, packet.getAddress(), packet.getPort());
                socket.send(packet);
                System.out.println("Antwort gesendet: " + response);
            }
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
