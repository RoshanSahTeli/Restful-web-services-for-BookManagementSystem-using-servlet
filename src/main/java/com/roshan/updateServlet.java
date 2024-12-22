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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshan.model.Book;

@WebServlet("/update/*")
public class updateServlet extends HttpServlet{
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String url="jdbc:mysql://localhost:3306/mydb";
		String username="root";
		String password="java";
		String query="update book set name=? where isbn=?";
		
		Connection con=null;
		PreparedStatement ps=null;
		
		try {
			ObjectMapper mapper= new ObjectMapper();
			Book book=mapper.readValue(req.getInputStream(), Book.class);
			
			String info=req.getPathInfo();
			if (info == null || info.length() <= 1) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			String isbn=info.substring(1);
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
			ps=con.prepareStatement(query);
			ps.setString(1, book.getName());
			ps.setString(2, isbn);
			
			int row=ps.executeUpdate();
			
			if(row>0) {
				resp.setStatus(HttpServletResponse.SC_CREATED);
			}
			else {
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
