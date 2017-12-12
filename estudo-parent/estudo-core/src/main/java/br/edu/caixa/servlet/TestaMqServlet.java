package br.edu.caixa.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestaMqServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
		
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		writer.println("<html><body><h1>Teste Sevlet</h1></body></html>");
	}

	public static void main(String[] args) {
		
	}

}
