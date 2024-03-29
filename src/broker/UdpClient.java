package broker;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {

    public static void main(String[] args) {
        String anfrageDaten = "Hotelbuchungsanfrage";
        String serverAdresse = "localhost";
        int serverPort = 4445;

        UdpClient client = new UdpClient();
        client.sendeAnfrage(anfrageDaten, serverAdresse, serverPort);
    }

    public void sendeAnfrage(String anfrageDaten, String serverAdresse, int serverPort) {
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] buffer = anfrageDaten.getBytes();
            InetAddress adresse = InetAddress.getByName(serverAdresse);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, serverPort);

            System.out.println("Sende Anfrage: " + anfrageDaten);
            socket.send(packet);

            // Bereite darauf vor, die Antwort zu empfangen
            byte[] empfangBuffer = new byte[1024];
            packet = new DatagramPacket(empfangBuffer, empfangBuffer.length);
            socket.receive(packet);
            String empfangeneDaten = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Antwort empfangen: " + empfangeneDaten);

        } catch (Exception e) {
            System.err.println("Fehler beim Senden oder Empfangen des Pakets: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
