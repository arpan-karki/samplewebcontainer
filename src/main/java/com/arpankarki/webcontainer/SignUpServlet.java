package com.arpankarki.webcontainer;

import java.io.PrintWriter;

import com.arpankarki.webcontainer.servlet.HttpServlet;
import com.arpankarki.webcontainer.servlet.Request;
import com.arpankarki.webcontainer.servlet.Response;

public class SignUpServlet extends HttpServlet {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		System.out.println("Sign up Servlet Initialized");
	}

	@Override
	public void doGet(Request request, Response response) {

		System.out.println("Sign Up  Servlet Do Get is called");
		PrintWriter outPrintWriter = response.getOutPrintWriter();
		outPrintWriter.println("<html><body>");
		outPrintWriter.println("<form method = \"post\">");
		outPrintWriter.println(
				"First Name : <input type = \"text\" id = \"firstName\" name = \"firstName\" value = \"Arpan\">");
		outPrintWriter.println(" <br/>");
		outPrintWriter.println(
				"Last Name : <input type = \"text\" id = \"lastName\" name = \"lastName\" value = \"Karki\"> <br/>");
		outPrintWriter.println(" <br/>");
		outPrintWriter.println("<input type = \"submit\" value = \"Submit\" > <br/>");
		outPrintWriter.println("</form>");
		outPrintWriter.println("</body></html>");

	}

	@Override
	public void doPost(Request request, Response response) {
		PrintWriter outPrintWriter = response.getOutPrintWriter();
		outPrintWriter.println("<html><body>");
		outPrintWriter.println(" Hello ");
		StringBuilder stringBuilder = new StringBuilder();
		request.getRequestParameters().forEach((key, value) -> {
			stringBuilder.append(key + '\t' + "=" + '\t' + value + '\n');
		});
		System.out.println(stringBuilder.toString());
		System.out.println(request.getRequestParameters().toString());
		outPrintWriter.println(stringBuilder.toString());
		outPrintWriter.println("</body></html>");
	}

	@Override
	public void destroy() {
		System.out.println("Destroying SignUp Servlet ");
	}
	
	

}
