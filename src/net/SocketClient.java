package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import static objects.Util.*;


public class SocketClient implements Runnable{
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private boolean done;

	@Override
	public void run() {
		try {
			client = new Socket("localhost", 8080);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			InputHandler inHandler = new InputHandler();
			Thread thread = new Thread(inHandler);
			thread.start();

			String inMessage;
			while ((inMessage = in.readLine()) != null) {
				System.out.println(inMessage);
			}
		} catch (IOException e) {
			shutdown();
		}
	}

	public void shutdown() {
		done = true;

		try {
			in.close();
			out.close();
			if (!client.isClosed()) {
				client.close();
			}
		} catch (IOException e) {}
	}

	class InputHandler implements Runnable {
		@Override
		public void run() {
			try {
				BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));

				while (!done) {
					String message = inReader.readLine();

					if (message.equals("/quit")) {
						inReader.close();
						shutdown();
					} else {
						out.println(message);
					}
				}
			} catch (IOException e) {
				shutdown();
			}
		}
	}
}
