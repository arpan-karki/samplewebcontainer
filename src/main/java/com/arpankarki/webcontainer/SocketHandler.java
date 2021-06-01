package com.arpankarki.webcontainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;

import com.arpankarki.webcontainer.servlet.HttpServlet;
import com.arpankarki.webcontainer.servlet.Request;
import com.arpankarki.webcontainer.servlet.Response;

public class SocketHandler extends Thread {

	private Socket socket;
	private BufferedReader inBufferedReader;
	private PrintWriter outPrintWriter;
	private String line;
	private Map<String, HttpServlet> handlers;
	private Request request;

	public SocketHandler(Socket socket, Map<String, HttpServlet> handlers) {
		this.socket = socket;
		this.handlers = handlers;
	}

	@Override
	public void run() {

		try {
			inBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outPrintWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

			request = new Request(inBufferedReader);
			if (!request.parse()) {
				outPrintWriter.println("HTTP/1.1 500 INTERNAL SERVER ERROR");
				outPrintWriter.println("Content-Type: text/html");
				outPrintWriter.println();
				outPrintWriter.println("<html><body>Current Time :" + "Cannot Process Request at time "
						+ LocalDateTime.now() + "</body></html>");
				outPrintWriter.flush();
			} else {
				HttpServlet httpServlet = handlers.get(request.getPath());
				if (httpServlet == null) {
					outPrintWriter.println("HTTP/1.1 404 Not Found");
					outPrintWriter.println("Content-Type: text/html");
					outPrintWriter.println();
					outPrintWriter.println("<html><body>Current Time :" + "No Handler for Request" + LocalDateTime.now()
							+ "</body></html>");
					outPrintWriter.flush();
				} else {
					Response response = new Response(socket.getOutputStream());
					PrintWriter outPrintWriter = response.getOutPrintWriter();
					outPrintWriter.println("HTTP/1.1 200 Success");
					outPrintWriter.println("Content-Type: text/html");
					outPrintWriter.println();
					httpServlet.service(request, response);
					outPrintWriter.flush();
				}
			}

			/*
			 * System.out.println(request.getMethod() + " : " + request.getPath());
			 * System.out.println("*** Request Parameters *** ");
			 * request.getRequestParameters().forEach((key, value) -> {
			 * System.out.println(key + " : " + value);
			 * 
			 * }); System.out.println("*** Request Headers *** ");
			 * request.getHeaders().forEach((key, value) -> { System.out.println(key + " : "
			 * + value); });
			 * 
			 * /* while (!line.isEmpty()) { System.out.println(line); line =
			 * inBufferedReader.readLine(); } System.out.println(socket.getPort());
			 * System.out.println("********************");
			 * 
			 * System.out.println();
			 */

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception during creation of input and output streams");
		} finally {
			try {
				socket.close();
				inBufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outPrintWriter.close();

		}

	}

}
