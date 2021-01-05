package net;

import java.io.IOException;
import java.net.*;

public class Search {
    private int port = 7000;
    private InetAddress multicastAddress = null;
    private int sizeBuffer = 1024;
    private byte[] buffer = new byte[sizeBuffer];
    private String address;

    public Search() {
        try {
            address = Inet4Address.getLocalHost().getHostAddress();
            System.out.println(address);
            multicastAddress = InetAddress.getByName("228.5.6.7");
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(multicastAddress);
            byte[] b = address.getBytes();
            DatagramPacket dp = new DatagramPacket(
                    b, b.length, multicastAddress, port);
            socket.send(dp);
            receiveString(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void receiveString(final MulticastSocket socket) throws IOException {
        byte[] b = address.getBytes();
        DatagramPacket dp = new DatagramPacket(
                b, b.length, multicastAddress, port);
        socket.send(dp);
        while (true) {
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            String line = getMyString(reply);
            sendString(socket, line);
        }
    }

    public void sendString(MulticastSocket socket, String line) throws IOException {
        if (line.equals("who is here")) {
            byte[] b = address.getBytes();
            DatagramPacket dp = new DatagramPacket(
                    b, b.length, multicastAddress, port);
            socket.send(dp);
        }
    }

    public String getAddress() {
        return address;
    }

    public String getMyString(DatagramPacket packet) {
        return new String(packet.getData(), 0, packet.getLength());
    }
}
