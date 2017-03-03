package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;

public class LibrarianService {
	ConnectionUtil util = new ConnectionUtil();
	
	public void updateBookCopies(Integer bookId, Integer branchId,Integer copies) throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			bcdao.addBookCopies(bookId, branchId, copies);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null)
				conn.close();
		}
	}
	public void updateBranch(Integer branchId,String branchName, String branchAddress) throws SQLException{
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO pdao = new LibraryBranchDAO(conn);
			pdao.updateLibraryBranch(branchId, branchName, branchAddress);
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
