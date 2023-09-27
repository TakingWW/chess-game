package com.joao.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {
    private ServerSocket serverSocket;
    private int port = 8080;
    private boolean running = false;

    public SocketServer() {
	System.out.println("INFO: Start server on port: " + port);
	startServer();

	try {
	    Thread.sleep(60000);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	stopServer();
    }

    public void startServer() {
	try {
	    serverSocket = new ServerSocket(port);
	    this.start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void stopServer() {
	running = false;
	this.interrupt();
    }

    @Override
    public void run() {
	running = true;
	while(running) {
	    try {
		System.out.println("INFO: Listennig for a connection");
		Socket socket = serverSocket.accept();
		RequestHandler requestHandler = new RequestHandler(socket);
		requestHandler.start();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
}
