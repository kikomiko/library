package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

public class AdminService {
	@Autowired
	AuthorDAO adao;

	@Autowired
	BookDAO bdao;

	public void addAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.addAuthor(author);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	
	public void udpateAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.updateAuthor(author);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void deleteAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.deleteAuthor(author);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	
	public void deleteBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.deleteBook(book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	public List<Author> readAuthors() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAllAuthors();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}
	public List<Genre> readGenres() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			return gdao.readAllGenres();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}
	public List<Author> readAuthors(Integer pageNo, String authorName) throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			if(authorName!=null){
				return adao.readAllAuthorsByName(authorName);
			}else{
				return adao.readAllAuthors(pageNo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null)
				conn.close();
		}
		return null;
	}
	public List<Book> readBooks(Integer pageNo, String bookName) throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			if(pageNo!=null && bookName!=null){
				return bdao.readBooksByName(pageNo, bookName);
			}else if(bookName!=null){
				return bdao.readAllBooksByName(bookName);
			}else if(pageNo!=null){
				return bdao.readAllBooks(pageNo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null)
				conn.close();
		}
		return null;
	}

	public void addBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			Integer bookId = bdao.addBookWithID(book);
			if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
				for (Author a : book.getAuthors()) {
					bdao.addBookAuthors(a.getAuthorId(),bookId);
				}
			}
			if(book.getGenres() != null && !book.getGenres().isEmpty()){
				for(Genre g : book.getGenres()){
					bdao.addBookGenres(g.getGenreId(), bookId);
				}
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void updateBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.updateBook(book);
			bdao.deleteBookAuthors(book.getBookId());
			bdao.deleteBookGenres(book.getBookId());

			if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
				for (Author a : book.getAuthors()) {
					bdao.addBookAuthors(a.getAuthorId(),book.getBookId());
				}
			}
			if(book.getGenres() != null && !book.getGenres().isEmpty()){
				for(Genre g : book.getGenres()){
					bdao.addBookGenres(g.getGenreId(), book.getBookId());
				}
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public List<Book> readBooks() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.readAllBooks();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}

	public List<Publisher> readPublishers() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.readAllPublisher();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}

	public void updatePublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.updatePublisher(publisher);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void addPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.addPublisher(publisher);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void addLibraryBranch(LibraryBranch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.addLibraryBranch(branch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void updateLibraryBranch(LibraryBranch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.updateLibraryBranch(branch.getBranchId(),branch.getBranchName(),branch.getBranchAddress());
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void deleteLibraryBranch(LibraryBranch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			lbdao.deleteLibraryBranch(branch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void updateDueDate(Book book, LibraryBranch branch, Borrower borrower, Timestamp dateOut, Timestamp dueDate,
			Timestamp newDueDate) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bldao = new BookLoansDAO(conn);
			bldao.updateDueDate(book, branch, borrower, dateOut, dueDate, newDueDate);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void addBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO adao = new BorrowerDAO(conn);
			adao.addBorrower(borrower);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	public Integer getAuthorsCount() throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.getAuthorsCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null)
				conn.close();
		}
		return null;
	}
	public Integer getBooksCount() throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.getBookCount();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null)
				conn.close();
		}
		return null;
	}
	public Integer getCount(String name) throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bdao = new BookDAO(conn);
			return bdao.getCount("tbl_book", name);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null)
				conn.close();
		}
		return null;
	}
}
