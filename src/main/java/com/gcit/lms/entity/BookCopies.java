package com.gcit.lms.entity;

import java.io.Serializable;

public class BookCopies implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6749826560915615574L;
	private Book book;
	private LibraryBranch libraryBranch;

	public LibraryBranch getLibraryBranch() {
		return libraryBranch;
	}

	public void setLibraryBranch(LibraryBranch libraryBranch) {
		this.libraryBranch = libraryBranch;
	}

	private int noOfCopies;

	public Book getBook() {
		return book;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + noOfCopies;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopies other = (BookCopies) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (noOfCopies != other.noOfCopies)
			return false;
		return true;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}
