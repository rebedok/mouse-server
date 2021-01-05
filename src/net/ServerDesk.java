package net;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDesk {
    private int port = 6789;
    private Socket socket = null;
    private int speedX, speedY;
    private int wheel;
    private Point location;
    private Robot robot;
    private boolean RKMPress;
    private boolean LKMPress;

    public ServerDesk() {
        while (true) {
            RKMPress = true;
            LKMPress = true;
            try {
                robot = new Robot();
                ServerSocket serverSocket = new ServerSocket(port);
                socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                commandExec(in);
            } catch (IOException e) {
                socketClose();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    void commandExec(DataInputStream in) throws IOException {
        String s;
        while (true) {
            s = in.readUTF();
            if (s.contains("#")) {
                moveMouse(s);
            } else if (s.contains("@")) {
                pressMouse(s);
            } else {
                wheelMouse(s);
            }
        }
    }

    void socketClose () {
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        socket = null;
    }

    void pressMouse(String s) {
        String[] code = s.split("@");
        for (String c: code) {
            if(c.equals("LKMPress") && LKMPress) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                LKMPress = false;
            } else if(c.equals("RKMPress") && RKMPress) {
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                RKMPress = false;
            } else if(c.equals("LKMRelease")) {
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                LKMPress = true;
            } else if(c.equals("RKMRelease")) {
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                RKMPress = true;
            }
        }
    }

    void wheelMouse(String s) {
        wheel = (int)Float.parseFloat(s);
        robot.mouseWheel(wheel);
    }

    void moveMouse (String s) {
        String[] speed = s.split("#");
        location = MouseInfo.getPointerInfo().getLocation();
        speedX = (int)(Float.parseFloat(speed[0]) + location.getX());
        speedY = (int)(Float.parseFloat(speed[1]) + location.getY());
        robot.mouseMove(speedX, speedY);
    }
}
