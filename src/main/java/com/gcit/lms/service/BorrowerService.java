package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

public class BorrowerService {
	ConnectionUtil util = new ConnectionUtil();

	public Borrower readBorrowerByPK(Integer borrowerId) throws SQLException {
		Connection conn = null;
		try {
			Borrower borrower = new Borrower();
			conn = util.getConnection();
			BorrowerDAO adao = new BorrowerDAO(conn);
			borrower = adao.readBorrowerByPk(borrowerId);
			conn.commit();
			return borrower;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	
	public List<LibraryBranch> readBranches() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			return lbdao.readAllBranches();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}
	
	public List<BookCopies> getBookCopies() throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bkcdao = new BookCopiesDAO(conn);
			return bkcdao.readAllBookCopies();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}
	public List<BookLoans> getBookLoans() throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bkcdao = new BookLoansDAO(conn);
			return bkcdao.readAllLoans();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
		return null;
	}
	public void addBookLoann(Integer bookId, Integer branchId, Integer borrowerId, Integer noOfCopies) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bdao = new BookLoansDAO(conn);
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			Date date = new Date();
			Date dueDate = new Date();
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			Calendar c = Calendar.getInstance();
			c.setTime(dueDate);
			c.add(Calendar.DATE, 10);
			dueDate = c.getTime();
			java.sql.Timestamp dueSqlDate = new java.sql.Timestamp(dueDate.getTime());
			bdao.addBookLoan(bookId, branchId, borrowerId, sqlDate,dueSqlDate);
			noOfCopies = noOfCopies - 1;
			bcdao.updateBookCopies(bookId, branchId);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	public void bookCheckInn(Integer bookId, Integer branchId, Integer borrowerId,java.sql.Date dueDate) throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO bdao = new BookLoansDAO(conn);
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);

			Date date = new Date();
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			bdao.bookLoanCheckIn(bookId, branchId, borrowerId, dueDate, sqlDate);

			bcdao.incrementBookCopies(bookId, branchId);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

}
