package com.arpankarki.webcontainer.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {

	private BufferedReader inputBufferedReader;
	private String method;
	private String path;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> requestParameters = new HashMap<String, String>();

	public Request(BufferedReader inputBufferedReader) {
		this.inputBufferedReader = inputBufferedReader;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(Map<String, String> requestParameters) {
		this.requestParameters = requestParameters;
	}

	private void parseRequestParams(String queryString) {

		for (String pair : queryString.split("&")) {
			String[] keyValue = pair.split("=");
			requestParameters.put(keyValue[0], keyValue[1]);
		}

	}

	public boolean parse() throws IOException {
		String line = inputBufferedReader.readLine();
		String firstLineArray[] = line.split(" ");
		if (firstLineArray.length != 3)
			return false;
		method = firstLineArray[0];

		String url = firstLineArray[1];
		int queryStringIndex = url.indexOf("?");
		if (url.indexOf("?") > -1) {
			path = url.substring(0, queryStringIndex);
			parseRequestParams(url.substring(queryStringIndex + 1));
		} else {
			path = url;
		}
		line = inputBufferedReader.readLine();
		while (!line.isEmpty()) {

			String[] headerPair = line.split(":");
			headers.put(headerPair[0], headerPair[1]);
			line = inputBufferedReader.readLine();
		}

		if ("POST".equals(method)) {
			StringBuilder body = new StringBuilder();
			while (inputBufferedReader.ready()) {
				body.append((char) inputBufferedReader.read());
			}
			parseRequestParams(body.toString());
		}

		return true;
	}

}
