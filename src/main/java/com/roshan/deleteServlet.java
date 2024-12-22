package com.roshan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/book/*")
public class deleteServlet extends HttpServlet {

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/json");

		Connection con = null;
		PreparedStatement ps = null;

		try {
			String pathInfo = req.getPathInfo();
			if (pathInfo == null || pathInfo.length() <= 1) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

			String isbn = pathInfo.substring(1);
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mydb";
			String username = "root";
			String password = "java";
			String query = "delete from book  where isbn=?";

			con = DriverManager.getConnection(url, username, password);
			ps = con.prepareStatement(query);
			ps.setString(1, isbn);

			int row = ps.executeUpdate();
			System.out.println(row);
			if (row > 0) {
				resp.setStatus(HttpServletResponse.SC_ACCEPTED);
				
			} else {
				resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			}

		} catch (Exception e) {
			
			e.printStackTrace(); 
		    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    resp.getWriter().write("{\"message\":\"An error occurred: " + e.getMessage() + "\"}");
		}
		finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}

	}
}
