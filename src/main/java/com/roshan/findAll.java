package com.roshan;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshan.model.Book;

@WebServlet("/books")
public class findAll extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/json");

		Connection  con = null;
		Statement st = null;

		String url = "jdbc:mysql://localhost:3306/mydb";
		String username = "root";
		String password = "java";
		String query="select * from book";
		List<Book> blist= new ArrayList<Book>();

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
			st=con.createStatement();	
			ResultSet rs=st.executeQuery(query);
			
			while(rs.next()) {
				String isbn=rs.getString("isbn");
				String name=rs.getString("name");
				Book b= new Book(isbn,name);
				blist.add(b);
			}
			ObjectMapper mapper= new ObjectMapper();
			String jsonResponse=mapper.writeValueAsString(blist);
			resp.getWriter().write(jsonResponse);	
			

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"message\":\"An error occurred: " + e.getMessage() + "\"}");
		}
		
		finally {
			try {
				con.close();
				st.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}
	}

}
