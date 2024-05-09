package buchungsdienste.implementierungen;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import buchungsdienste.HotelVerfuegbarkeit;

public class HotelBuchungsServiceServer {
    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(4445)) {
            System.out.println("Server gestartet, warte auf Anfragen...");
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Blockiert, bis eine Anfrage empfangen wird
                System.out.println("Anfrage erhalten.");

                // Starte einen neuen Thread für jede Anfrage
                new Thread(() -> handleRequest(packet, socket)).start();
            }
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleRequest(DatagramPacket packet, DatagramSocket socket) {
        try {
            String received = new String(packet.getData(), 0, packet.getLength()).trim();
            System.out.println("Empfangene Daten: " + received);


            String hotelName = received.split("\"hotel\":\"")[1].split("\"")[0];


            String response;

            if (HotelVerfuegbarkeit.checkVerfuegbarkeit(hotelName)) {
                HotelVerfuegbarkeit.bookRoom(hotelName);
                response = String.format("{\"id\":\"%s\", \"erfolg\":true, \"nachricht\":\"Hotel '%s' gebucht.\"}",
                        "Buchungs-ID", hotelName);
            } else {
                response = String.format("{\"id\":\"\", \"erfolg\":false, \"nachricht\":\"Keine Verfügbarkeit im Hotel '%s'.\"}",
                        hotelName);

            }

            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, packet.getAddress(), packet.getPort());
            socket.send(responsePacket);
            System.out.println("Antwort gesendet: " + response);
        } catch (Exception e) {
            System.err.println("Fehler bei der Anfragebearbeitung: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
