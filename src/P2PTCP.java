/**
 * Created by cj on 12/11/15.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class P2PTCP {

	public static String host = "130.229.172.12";
    public static void main(String[] args) {

        Scanner scan;
        Thread st = null;
        Socket peerConnectionSocket = null;

        RsaKeyGenerator keGen = new RsaKeyGenerator(Integer.parseInt(args[1]));

        if (args[0].equals("server")) {
            try {

                if (!keGen.generateKey()){

                    throw new IOException("No key generated");
                }

                ServerSocket ss = new ServerSocket(Integer.parseInt(args[1]));
                System.out.println("Server address: " + ss.getLocalSocketAddress());
                System.out.println("Waiting for connection...");
                peerConnectionSocket = ss.accept();

                st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
                st.start();
                scan = new Scanner(peerConnectionSocket.getInputStream());
                String fromSocket;
                while ((fromSocket = scan.nextLine()) != null)
                    System.out.println(fromSocket);
            } catch (IOException e) {
                System.err.println("Server crash");
                e.printStackTrace();
            } finally {
                st.stop();
            }
        } else if (args[0].equals("client")) {
            try {
                peerConnectionSocket = new Socket(host, Integer.parseInt(args[1]));

                st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
                st.start();
                scan = new Scanner(peerConnectionSocket.getInputStream());
                String fromSocket;
                while ((fromSocket = scan.nextLine()) != null)
                    System.out.println(fromSocket);
            } catch (Exception e) {
                System.err.println("Client crash");
            } finally {
                st.stop();
            }
        }
    }
}

