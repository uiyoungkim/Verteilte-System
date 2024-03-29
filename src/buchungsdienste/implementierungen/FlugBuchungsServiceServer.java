package buchungsdienste.implementierungen;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class FlugBuchungsServiceServer {
    public static void main(String[] args) throws Exception{
        try (DatagramSocket socket = new DatagramSocket(4446)){
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                // -> verarbeiten
                String response = "Flug gebucht";
                packet = new DatagramPacket(response.getBytes(), response.getBytes().length, packet.getAddress(), packet.getPort());
                socket.send(packet);
            }
        }

    }
}
