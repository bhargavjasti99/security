package com.mycompany.support;

import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

public class SocketConnection {
	private Socket socket = null;

	private Properties properties = null;

	private FileInputStream fileInputStream = null;

	private ObjectOutputStream objectOutputStream = null;

	public ObjectOutputStream SocketSend(String portStr, String system) {
		try {
			int port = Integer.parseInt(portStr);
			socket = new Socket(system, port);
			objectOutputStream = new ObjectOutputStream(socket
					.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectOutputStream;
	}
}
