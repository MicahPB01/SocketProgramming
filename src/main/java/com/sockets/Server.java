package com.sockets;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket;
    private ServerSocket server;
    private BufferedReader in;
    private PrintWriter out;

    public Server(String ipAddress, int port) {
        try {
            InetAddress bindAddress = InetAddress.getByName(ipAddress);
            server = new ServerSocket(port, 0, bindAddress);
            InetAddress serverAddress = server.getInetAddress();
            System.out.println("Server Started on IP: " + serverAddress.getHostAddress() + " Port: " + port);
            System.out.println("Waiting...");

            socket = server.accept();
            System.out.println("Connected by client on " + socket.getInetAddress().getHostAddress());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String line = "";
            while (true) {
                try {
                    line = in.readLine();
                    System.out.println("Received Question: " + line);

                    if (line.equals("0 / 0 =")) {
                        break;
                    }

                    String result = performAction(line);

                    out.println("Result: " + result);

                } catch (IOException i) {
                    System.out.println(i);
                }
            }

            System.out.println("Closing Connection and Stopping Server");
            socket.close();
            in.close();
            out.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Server server = new Server("10.173.204.158", 10102);
    }

    private String performAction(String input) {
        String[] parts = input.split(" ");

        if (parts.length != 4 || !parts[3].contains("=")) {
            return "Input error. Re-type the math question";
        }

        float firstNumber = Float.parseFloat(parts[0]);
        String action = parts[1];
        float secondNumber = Float.parseFloat(parts[2]);
        float finalNumber = 0;

        switch (action) {
            case "+":
                finalNumber = firstNumber + secondNumber;
                break;
            case "-":
                finalNumber = firstNumber - secondNumber;
                break;
            case "*":
                finalNumber = firstNumber * secondNumber;
                break;
            case "/":
                finalNumber = firstNumber / secondNumber;
                break;
        }


        if(finalNumber == (int) finalNumber)   {
            System.out.println("Returning as Int: " + finalNumber);
            return String.valueOf((int) finalNumber);
        }

        System.out.println("Returning: " + finalNumber);
        return Float.toString(finalNumber);
    }
}
