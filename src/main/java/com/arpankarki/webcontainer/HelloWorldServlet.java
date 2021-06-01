package com.arpankarki.webcontainer;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import com.arpankarki.webcontainer.servlet.HttpServlet;
import com.arpankarki.webcontainer.servlet.Request;
import com.arpankarki.webcontainer.servlet.Response;

public class HelloWorldServlet extends HttpServlet {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		System.out.println("Hello World Servlet Initialized");
	}

	@Override
	public void doGet(Request request, Response response) {

		System.out.println("Hello Servlet Do Get is called");
		PrintWriter outPrintWriter = response.getOutPrintWriter();
		outPrintWriter.println("<html><body>");
		outPrintWriter.println("Hell From HelloWorldServlet doGET()");
		outPrintWriter.println("</body></html>");

	}

	@Override
	public void doPost(Request request, Response response) {
		// TODO Auto-generated method stub
		System.out.println("Hello World do Post Implementation");
	}

	@Override
	public void destroy() {
		System.out.println("Destroying HelloWorld  Servlet ");
	}
}
