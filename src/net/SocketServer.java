package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;
import java.net.Socket;

import objects.player.Player;
import objects.player.Player1;
import static objects.Util.*;

public class SocketServer implements Runnable {
	private ArrayList<ConnectionHandler> connections;
	private ServerSocket server;
	private boolean done = false;;
	private static int numberOfConnections = 0;

	private ExecutorService pool;

	public SocketServer() {
		this.connections = new ArrayList<>();
	}

	@Override
	public void run() {
		try {
			server = new ServerSocket(8080);
			pool = Executors.newCachedThreadPool();

			System.out.println("Server initialized");

			while (!done) {
				if (connections.size() < 2) {
					Socket client = server.accept();
					ConnectionHandler handler = new ConnectionHandler(client, numberOfConnections++);

					connections.add(handler);
					pool.execute(handler);
				}
			}
		} catch (Exception e) {
			shutdown();
		}
	}

	public void broadcast(String message) {
		for (ConnectionHandler connection : connections) {
			if (connection != null) {
				connection.sendMessage(message);
			}
		}
	}

	public void shutdown() {
		try {
			done = true;
			if (!server.isClosed()) {
				server.close();
			}

			for (ConnectionHandler connection : connections) {
				connection.shutdown();
			}

		} catch (IOException ignore) {}
	}

	class ConnectionHandler implements Runnable {
		private Socket client;
		private BufferedReader in;
		private PrintWriter out;
		private Player player;
		private int id;

		public ConnectionHandler(Socket client, int id) {
			this.client = client;
			this.id = id;
		}

		@Override
		public void run() {
			try {
				out = new PrintWriter(client.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				if (id == 0) {
					out.println("Please enter a color: ");
					String color = in.readLine();
					this.player = new Player1(color);

				} else {
					while (connections.get(0).getPlayer() == null) {
						Thread.sleep(2);
					}

					this.player = new Player1(connections.get(0).getPlayer().getColor().print());
				}

				broadcast("me joined the chat");

			} catch (Exception e) {
				shutdown();
			}
		}

		public Player getPlayer() {
			return player;
		}

		public void sendMessage(String message) {
			out.println(message);
		}


		public void shutdown() {
			try {
				in.close();
				out.close();
				if (!client.isClosed()) {
					client.close();
				}

			} catch (IOException ignore) {

			}
		}
	}

	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		server.run();
	}
}
