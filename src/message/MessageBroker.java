package message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MessageBroker {
    private static final int PORT = 7777;
    private static final String SERVER_ADDRESS = "localhost";
    private static final int HOTEL_SERVER_PORT = 4445;
    private static final int FLIGHT_SERVER_PORT = 4446;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("MessageBrokerServer gestartet und lauscht auf Port " + PORT);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String request = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println("Anfrage erhalten: " + request);

                // Anfang von Hotel- und Fluganfrage finden.
                int hotelStart = request.indexOf("\"Hotel\":");
                int flugStart = request.indexOf("\"Flug\":");

                // Substrings für Hotel- und Fluganfragen extrahieren
                String hotelRequest = request.substring(hotelStart, flugStart - 2); // Bis zum Komma vor "Flug"
                String flightRequest = request.substring(flugStart); // Vom "Flug" bis zum Ende

                hotelRequest = "{" + hotelRequest + "}";
                flightRequest = "{" + flightRequest + "}";

                System.out.println("Hotel Request: " + hotelRequest);
                System.out.println("Flight Request: " + flightRequest);

                // Weiterleiten der Anfragen an jeweiligen Server
                weiterleiten(hotelRequest, SERVER_ADDRESS, HOTEL_SERVER_PORT, socket);
                weiterleiten(flightRequest, SERVER_ADDRESS, FLIGHT_SERVER_PORT, socket);
            }
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void weiterleiten(String daten, String adresse, int port, DatagramSocket socket) throws Exception {
        InetAddress inetAdresse = InetAddress.getByName(adresse);
        byte[] buffer = daten.getBytes();
        DatagramPacket weiterleitPacket = new DatagramPacket(buffer, buffer.length, inetAdresse, port);
        socket.send(weiterleitPacket);
        System.out.println("Anfrage weitergeleitet an " + adresse + ":" + port);
    }
}
