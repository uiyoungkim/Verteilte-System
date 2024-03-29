package buchungsdienste.implementierungen;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HotelBuchungsServiceServer {
    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(4445)) {
            System.out.println("Server gestartet, warte auf Anfragen..."); // Server-Startmeldung
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Warte auf eine Anfrage
                System.out.println("Anfrage erhalten."); // Bestätigung, dass eine Anfrage empfangen wurde

                // Hier die empfangenen Daten extrahieren
                String received = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println("Empfangene Daten: " + received); // Zeige die empfangenen Daten

                // --> Verarbeiten
                String response = "id:1,erfolg:true,nachricht:Hotel gebucht";
                packet = new DatagramPacket(response.getBytes(), response.getBytes().length, packet.getAddress(), packet.getPort());

                socket.send(packet); // Sende die Antwort
                System.out.println("Antwort gesendet.");
            }
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
