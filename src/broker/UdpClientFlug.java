package broker;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class UdpClientFlug {
    public void sendeFlugbuchungsanfrage(String airline) {
        // Port und Adresse des Flugbuchungsservers
        int port = 4446;
        String serverAdresse = "localhost";

        try (DatagramSocket socket = new DatagramSocket()) {
            String flugAnfrage = String.format("{\"typ\":\"Flugbuchung\", \"airline\":\"%s\"}", airline);
            byte[] data = flugAnfrage.getBytes();

            InetAddress adresse = InetAddress.getByName(serverAdresse);
            DatagramPacket packet = new DatagramPacket(data, data.length, adresse, port);

            socket.send(packet);
            System.out.println("Flugbuchungsanfrage gesendet: " + flugAnfrage);

            // Empfange Antwort vom Server
            packet = new DatagramPacket(new byte[1024], 1024);
            socket.receive(packet);
            String antwort = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Antwort erhalten: " + antwort);
        } catch (Exception e) {
            System.err.println("Fehler beim Senden der Flugbuchungsanfrage: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Hauptmethode zum Testen der Flugbuchungsanfrage
    public static void main(String[] args) {
        UdpClientFlug client = new UdpClientFlug();
        client.sendeFlugbuchungsanfrage("FliegtBeiSonne");
    }
}