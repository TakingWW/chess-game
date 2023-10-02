package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import static objects.Util.*;


public class SocketClient {
    private String server = "localhost";
    private String path = "/";

    public SocketClient() {
	try {
	    Socket socket = new Socket(server, 8080);
	    PrintStream out = new PrintStream(socket.getOutputStream());
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out.println("GET " + path + " HTTP/1.0");
	    out.println();

	    String line = in.readLine();
	    while(line != null) {
		print(line);
		line= in.readLine();
	    }
	    in.close();
	    out.close();
	    socket.close();
	} catch (Exception e) {
	    e.printStackTrace();
        }
    }
}
