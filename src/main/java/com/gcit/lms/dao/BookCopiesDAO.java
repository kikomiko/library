package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;

public class BookCopiesDAO extends BaseDAO implements ResultSetExtractor<List<BookCopies>> {

	public void addBookCopies(Integer bookId, Integer libraryBranchId, Integer noOfCopies)
			throws SQLException, ClassNotFoundException {
		template.update("insert into tbl_book_copies (bookId,branchId,noOfCopies) values (?,?,?)",
				new Object[] { bookId, libraryBranchId, noOfCopies });
	}

	public void updateBookCopies(Integer bookId, Integer libraryBranchId) throws ClassNotFoundException, SQLException {
		template.update("update tbl_book_copies set noOfCopies = noOfCopies - 1 where bookId = ? and branchId=?",
				new Object[] { bookId, libraryBranchId });
	}

	public void incrementBookCopies(Integer bookId, Integer libraryBranchId)
			throws ClassNotFoundException, SQLException {
		template.update("update tbl_book_copies set noOfCopies = noOfCopies + 1 where bookId = ? and branchId=?",
				new Object[] { bookId, libraryBranchId });
	}

	public void deleteBookCopies(Integer bookId, Integer libraryBranchId) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_book_copies where bookId= ? AND branchId=? ",
				new Object[] { bookId, libraryBranchId });
	}

	public void deleteAllFromBranch(Integer libraryBranchId) throws ClassNotFoundException, SQLException {
		template.update("delete from tbl_book_copies where branchId=? ", new Object[] { libraryBranchId });
	}

	public List<BookCopies> readAllBookCopies() throws ClassNotFoundException, SQLException {
		return template.query("select * from tbl_book_copies", this);
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bc = new ArrayList();
		while (rs.next()) {
			BookCopies b = new BookCopies();
			b.setBook(bdao.readBookByPk(rs.getInt("bookId")));
			b.setLibraryBranch(pdao.readLibraryBranchByPk(rs.getInt("branchId")));
			b.setNoOfCopies(rs.getInt("noOfCopies"));
			bc.add(b);

		}
		return bc;
	}

}
