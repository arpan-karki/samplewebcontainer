package com.arpankarki.webcontainer.servlet;

public abstract class HttpServlet {

	public void init() {
		System.out.println("HTTP Servlet init default implementation");
	}

	public void service(Request request, Response response) {
		// TO DO : Request Response Object as Params
		System.out.println(this);
		String method = request.getMethod();
		if ("GET".equals(method)) 
			this.doGet(request, response);
		else if ("POST".equals(method))
			this.doPost(request, response);
		else
			throw new RuntimeException("Method Not Supported!");
	}
	
	public void destroy() {
		System.out.println("Destroying the method ");
	}

	public void doGet(Request request, Response response) {
		System.out.println("Default Do get");
	}

	public void doPost(Request request, Response response) {
		System.out.println("Default Do Post ");
	}
}
