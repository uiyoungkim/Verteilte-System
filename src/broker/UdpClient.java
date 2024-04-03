package broker;

import java.net.*;
import java.io.*;

public class UdpClient {
    public static void main(String[] args) {
        String hotel = "";
        int hotelzimmer = 0;
        int hoteldauer = 0;
        String hotelankunft = "";
        String flug = "";
        int flugplaetze = 0;
        int reisedauer = 0;
        String abflug = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Geben Sie bitte Ihre Daten an. Benutzen Sie dafür bitte folgenden Argumente: -hotel String -hotelzimmer int -hoteldauer int -hotelankunft Date -flug String -flugplaetze int -reisedauer int -abflug Date : ");
        try {
            String eingabe = reader.readLine();
            boolean valid = false;
            while (!valid) {
                String[] eingabeArray = eingabe.split("-");
                for (int i = 1; i < eingabeArray.length; i++) {
                    String[] parameter = eingabeArray[i].trim().split(" ", 2);
                    switch (parameter[0]) {
                        case "hotel":
                            hotel = parameter[1];
                            break;
                        case "hotelzimmer":
                            hotelzimmer = Integer.parseInt(parameter[1].trim());
                            break;
                        case "hoteldauer":
                            hoteldauer = Integer.parseInt(parameter[1].trim());
                            break;
                        case "hotelankunft":
                            hotelankunft = parameter[1];
                            break;
                        case "flug":
                            flug = parameter[1];
                            break;
                        case "flugplaetze":
                            flugplaetze = Integer.parseInt(parameter[1].trim());
                            break;
                        case "reisedauer":
                            reisedauer = Integer.parseInt(parameter[1].trim());
                            break;
                        case "abflug":
                            abflug = parameter[1];
                            break;
                        default:
                            System.out.println("Unrecognized argument: " + parameter[0]);
                            break;
                    }
                }
                if (!hotel.isEmpty() && hotelzimmer != 0 && hoteldauer != 0 && !hotelankunft.isEmpty() && !flug.isEmpty() && flugplaetze != 0 && reisedauer != 0 && !abflug.isEmpty()) {
                    valid = true;
                } else {
                    System.out.println("Bitte geben Sie alle Daten korrekt an.");
                    eingabe = reader.readLine();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String anfrageDaten = String.format("{\"Hotel\": {\"hotel\":\"%s\", \"hotelzimmer\":%d, \"hoteldauer\":%d, \"hotelankunft\":\"%s\"}, \"Flug\": {\"flug\":\"%s\", \"flugplaetze\":%d, \"reisedauer\":%d, \"abflug\":\"%s\"}}", hotel, hotelzimmer, hoteldauer, hotelankunft, flug, flugplaetze, reisedauer, abflug);

        String serverAdresse = "localhost";
        int serverPort = 7777; // Port des MessageBrokers
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
