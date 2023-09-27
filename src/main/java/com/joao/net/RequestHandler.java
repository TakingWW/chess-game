package com.joao.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler extends Thread {
    private Socket socket;

    public RequestHandler(Socket socket) {
	this.socket = socket;
    }

    @Override
    public void run() {
	try {
	    System.out.println("INFO: Received a connection");

	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    PrintWriter out = new PrintWriter(socket.getOutputStream());

	    out.println("Echo: Server 1.0");
	    out.flush();

	    String line = in.readLine();
	    out.println("cagando na sua cabeça");
	    in.close();
	    out.close();
	    socket.close();

	    System.out.println("INFO: Connection closed");
	    
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
