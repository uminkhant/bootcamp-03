package com.jdc.mkt.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

@Entity
@Table(name = "author_tbl")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "a_name", nullable = false, length = 50)
	private String name;
	@OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
	private List<Book> books;

	public Author(String name) {
		super();
		this.name = name;
		books = new ArrayList<>();

	}

	public Author() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		book.setAuthor(this);
		this.books.add(book);
	}

}
