package com.roshan.model;

public class Book {

	public String isbn;

	public String name;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Book(String isbn, String name) {
		super();
		this.isbn = isbn;
		this.name = name;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

}
