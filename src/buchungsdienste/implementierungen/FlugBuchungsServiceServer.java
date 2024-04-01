package buchungsdienste.implementierungen;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import buchungsdienste.FlugVerfuegbarkeit;

public class FlugBuchungsServiceServer {
    public static void main(String[] args) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(4446)) {
            System.out.println("Flugbuchungsserver gestartet. Warte auf Anfragen...");
            byte[] buffer = new byte[1024];
            while (true) {
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
            System.out.println("Empfangene Fluganfrage: " + received);

            // Extrahiere die Airline oder Flugnummer aus der Anfrage
            String airline = received.split("\"airline\":\"")[1].split("\"")[0];
            String response;

            if (FlugVerfuegbarkeit.checkVerfuegbarkeit(airline)) {
                FlugVerfuegbarkeit.bucheFlug(airline);
                response = String.format("{\"id\":\"%s\", \"erfolg\":true, \"nachricht\":\"Flug mit %s gebucht.\"}",
                        airline, airline);
            } else {
                response = String.format("{\"id\":\"\", \"erfolg\":false, \"nachricht\":\"Keine Verfügbarkeit für Flug mit %s.\"}",
                        airline);
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
