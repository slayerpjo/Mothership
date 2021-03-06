package org.mothership.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates new threaded session for client
 * @author Nicholas Ross(nmr13)
 *
 */
public class Session extends Thread {
	private ServerSocket sSocket;
	private List<Connection> connectionList = new ArrayList<Connection>();
	
	/**
	 * Creates a new session and set up new Socket
	 */
	public Session() {
		try {
			sSocket = new ServerSocket(0); // new server socket on random port
		} catch (IOException e) {
			System.err.println("Session constructor" + e.getMessage());
		}
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			try {
				// accept incoming connection and add to connection list
				Connection newConnection = new Connection(sSocket.accept());
				newConnection.start();
				connectionList.add(newConnection);
			} catch (Exception e) {
				System.err.println("new connection " + e.getMessage());

			}
		}
	}	
	
	/**
	 * Returns the port number for this session.
	 * 
	 * @return int Sessions port number
	 */
	public int getPort() {
		return sSocket.getLocalPort();
	}
	
	/**
	 * Closes this Session
	 */
	public void close() {
		try {
			sSocket.close();
		} catch (Exception e) {
			System.err.println("Close Session: " + e.getMessage());
		}
	}
}

