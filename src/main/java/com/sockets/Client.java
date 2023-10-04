package com.sockets;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected with server on " + socket.getInetAddress().getHostAddress());

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String userInputLine;

            while ((userInputLine = userInput.readLine()) != null) {

                out.println(userInputLine);

                if (userInputLine.equals("0 / 0 =")) {
                    break;
                }

                String serverResponse = in.readLine();
                System.out.println(serverResponse);


            }

            socket.close();
            out.close();
            in.close();
            userInput.close();
            System.out.println("Closing Connection and Stopping Client");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        System.out.println("Starting Client");
        Client client = new Client("10.173.204.158", 10102);
    }
}
