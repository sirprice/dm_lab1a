/**
 * Created by cj on 12/11/15.
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class P2PTCP {
	public static void main(String[] args) {
		Scanner scan; Thread st=null;
		Socket peerConnectionSocket=null;

		RsaKeyGenerator keGen = new RsaKeyGenerator(Integer.parseInt(args[2]));

		if(args[0].equals("server")){
			try{
				ServerSocket ss = new ServerSocket(Integer.parseInt(args[1]));
				System.out.println("Waiting for connection...");
				peerConnectionSocket = ss.accept();

				st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
				st.start();
				scan = new Scanner (peerConnectionSocket.getInputStream());
				String fromSocket;
				while((fromSocket = scan.nextLine())!=null)
					System.out.println(fromSocket);
			}catch(IOException e) {System.err.println("Server crash");}
			finally {st.stop();
			}
		}

		else if(args[0].equals("client")) {
			try{
				peerConnectionSocket = new Socket("localhost", Integer.parseInt(args[1]));

				st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
				st.start();
				scan = new Scanner (peerConnectionSocket.getInputStream());
				String fromSocket;
				while((fromSocket = scan.nextLine())!=null)
					System.out.println(fromSocket);
			}
			catch(Exception e) {System.err.println("Client crash");}
			finally{st.stop();
			}
		}
	}
}

