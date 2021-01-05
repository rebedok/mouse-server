package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.*;

public class Controller {

    @FXML
    private Button button;
    @FXML
    private Text my_address;


    public void initialize() {
        try {
            String address = Inet4Address.getLocalHost().getHostAddress();
            my_address.setText("address: " + address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onClickMethod() {
        InetAddress multicastAddress = null;
        int port = 7000;
        try {
            String address = Inet4Address.getLocalHost().getHostAddress();
            System.out.println(address);
            multicastAddress = InetAddress.getByName("228.5.6.7");
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(multicastAddress);
            byte[] b = address.getBytes();
            DatagramPacket dp = new DatagramPacket(
                    b, b.length, multicastAddress, port);
            socket.send(dp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
