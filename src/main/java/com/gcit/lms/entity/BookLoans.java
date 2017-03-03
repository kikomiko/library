package com.gcit.lms.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class BookLoans implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5232482499894565730L;
	private Book book;
	private LibraryBranch libraryBranch;
	private Borrower borrower;
	private Timestamp dateOut;
	private Timestamp dueDate;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LibraryBranch getLibraryBranch() {
		return libraryBranch;
	}

	public void setLibraryBranch(LibraryBranch libraryBranch) {
		this.libraryBranch = libraryBranch;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	private Timestamp dateIn;

	public Timestamp getDateOut() {
		return dateOut;
	}

	public void setDateOut(Timestamp dateOut) {
		this.dateOut = dateOut;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getDateIn() {
		return dateIn;
	}

	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}
}
