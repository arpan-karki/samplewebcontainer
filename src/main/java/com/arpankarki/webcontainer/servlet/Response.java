package com.arpankarki.webcontainer.servlet;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Response {

	private OutputStream outputStream;
	private PrintWriter outPrintWriter;

	public Response(OutputStream outputStream) {

		this.outputStream = outputStream;
		this.outPrintWriter = new PrintWriter(outputStream);
	}

	
	public OutputStream getOutputStream() {
		return outputStream;
	}


	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}


	public PrintWriter getOutPrintWriter() {
		return outPrintWriter;
	}


	public void setOutPrintWriter(PrintWriter outPrintWriter) {
		this.outPrintWriter = outPrintWriter;
	}


	public void createResponse() {
		outPrintWriter.println("HTTP/1.1 200 OK");
		outPrintWriter.println("Content-Type: text/html");
		outPrintWriter.println();
		outPrintWriter.println("<html><body>Current Time :" + LocalDateTime.now() + "</body></html>");
		outPrintWriter.flush();
	}



}
