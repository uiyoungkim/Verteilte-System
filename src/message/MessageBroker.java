package message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class MessageBroker {
    private static final int PORT = 7777;
    private static final String SERVER_ADDRESS = "localhost";
    private static final int HOTEL_SERVER_PORT = 4445;
    private static final int FLIGHT_SERVER_PORT = 4446;
    static ArrayList<String> uuidList = new ArrayList<>();
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("MessageBrokerServer gestartet und laufen auf Port " + PORT);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String request = new String(packet.getData(), 0, packet.getLength()).trim();
                if (uuidList.contains(request.split("\"uuid\":\"")[1].split("\"")[0])) {
                    System.out.println("Anfrage bereits bearbeitet: " + request);
                    continue;
                }
                uuidList.add(request.split("\"uuid\":\"")[1].split("\"")[0]);
                System.out.println("Anfrage erhalten: " + request);

                for (String uuid : uuidList) {
                    System.out.println("UUID: " + uuid);
                }

                // Extrahiere Hotel- und Fluganfrage
                String hotelRequest = request.substring(request.indexOf("\"Hotel\": "), request.indexOf("}, \"Flug\":") + 1);
                String flightRequest = request.substring(request.indexOf("\"Flug\":"));

                // Verwende Threads, um Anfragen parallel weiterzuleiten
                Thread hotelThread = new Thread(() -> weiterleiten(hotelRequest, SERVER_ADDRESS, HOTEL_SERVER_PORT));
                Thread flightThread = new Thread(() -> weiterleiten(flightRequest, SERVER_ADDRESS, FLIGHT_SERVER_PORT));

                hotelThread.start();
                flightThread.start();

                // Warte auf beendigung beide Threads
                try {
                    hotelThread.join();
                    flightThread.join();
                } catch (InterruptedException e) {
                    System.err.println("Thread wurde unterbrochen: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void weiterleiten(String daten, String adresse, int port) {
        try (DatagramSocket weiterleitSocket = new DatagramSocket()) {
            InetAddress inetAdresse = InetAddress.getByName(adresse);
            byte[] buffer = daten.getBytes();
            DatagramPacket weiterleitPacket = new DatagramPacket(buffer, buffer.length, inetAdresse, port);
            weiterleitSocket.send(weiterleitPacket);
            System.out.println("Anfrage weitergeleitet an " + adresse + ":" + port);

            // Warte auf Antwort
            buffer = new byte[1024];
            DatagramPacket antwortPacket = new DatagramPacket(buffer, buffer.length);
            weiterleitSocket.receive(antwortPacket);
            String antwort = new String(antwortPacket.getData(), 0, antwortPacket.getLength());
            System.out.println("Antwort erhalten von " + adresse + ":" + port + " - " + antwort);
        } catch (Exception e) {
            System.err.println("Fehler beim Weiterleiten der Anfrage: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
