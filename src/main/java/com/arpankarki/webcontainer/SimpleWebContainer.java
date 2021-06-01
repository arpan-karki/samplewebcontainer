package com.arpankarki.webcontainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Handler;

import javax.management.RuntimeErrorException;

import com.arpankarki.webcontainer.servlet.HttpServlet;

public class SimpleWebContainer {

	private final int port;
	private String inputFileName;
	private Map<String, HttpServlet> handlers = new HashMap<String, HttpServlet>();

	public SimpleWebContainer(int port, String inputFileName) {

		this.port = port;
		this.inputFileName = inputFileName;
	}

	public static void main(String[] args) throws IOException {
		SimpleWebContainer simpleWebContainer = new SimpleWebContainer(8080, "config.properties");
		simpleWebContainer.loadProperties();
		System.out.println(Runtime.getRuntime());
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				simpleWebContainer.handlers.forEach((url, servlet) -> {
					servlet.destroy();
				});
			}
		});

		simpleWebContainer.start();

	}

	private void loadProperties() throws IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputFileName);
		if (inputStream == null) {
			throw new RuntimeException("Unable to find Config File" + inputFileName);
		}
		Properties properties = new Properties();
		properties.load(inputStream);

		properties.forEach((key, value) -> {
			HttpServlet httpServlet = getServletInstance((String) value);
			httpServlet.init();
			handlers.put((String) key, httpServlet);

		});

	}

	private HttpServlet getServletInstance(String className) {
		try {

			return (HttpServlet) Class.forName(className).getDeclaredConstructor().newInstance();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void start() throws IOException {

		ServerSocket serverSocket = new ServerSocket(this.port);
		Socket socket;
		while (true) {
			try {
				Thread socketHandler = new SocketHandler(serverSocket.accept(), handlers);
				socketHandler.start();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error during thread creations ");
			}

		}
	}
}

//so once a connection is accepter the loop continues . Basically the
// server listens on the port for any new incoming requests and once a
// connection is established a socket is created and given by the accept
// method
