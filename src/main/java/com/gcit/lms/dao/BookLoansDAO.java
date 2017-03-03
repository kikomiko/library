package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

public class BookLoansDAO extends BaseDAO {

	public BookLoansDAO(Connection conn) {
		super(conn);

	}

	public void addBookLoan(Integer bookId, Integer branchId, Integer borrowerId, Timestamp dateOut, Timestamp dueDate)
			throws SQLException, ClassNotFoundException {
		save("insert into tbl_book_loans (bookId,branchId,cardNo,dateOut,dueDate) values (?,?,?,?,?)",
				new Object[] { bookId, branchId, borrowerId, dateOut, dueDate });
	}

	public void bookLoanCheckIn(Integer bookId, Integer branchId, Integer borrowerId,
			java.sql.Date dueDate,Timestamp dateIn) throws SQLException, ClassNotFoundException {
		save("UPDATE `library`.`tbl_book_loans` SET `dateIn`=? WHERE date(dueDate) = ? and`cardNo`=? and`bookId`=? and`branchId`=?",

				new Object[] {dateIn,dueDate, borrowerId,bookId,branchId });
	}

	public void updateDueDate(Book book, LibraryBranch branch, Borrower borrower, Timestamp dateOut, Timestamp dueDate,
			Timestamp newDueDate) throws SQLException, ClassNotFoundException {
		save("update tbl_book_loans set dueDate=? where bookId = ? and branchId=? and cardNo=? and dateOut = ? and dueDate = ?",
				new Object[] { newDueDate, book.getBookId(), branch.getBranchId(), borrower.getCardNo(), dateOut,
						dueDate });
	}

	public void updateDateIn(Book book, LibraryBranch branch, Borrower borrower, Timestamp dateOut, Timestamp dueDate,
			Timestamp dateIn) throws ClassNotFoundException, SQLException {
		save("update tbl_book_loans set dateIn=? where bookId = ? and branchId=? and cardNo=? and dateOut = ? and dueDate = ?",
				new Object[] { dateIn, book.getBookId(), branch.getBranchId(), borrower.getCardNo(), dateOut,
						dueDate });
	}

	public List<BookLoans> readAllLoans() throws ClassNotFoundException, SQLException {
		return readAll("select * from tbl_book_loans", null);
	}

	public List<BookLoans> readBookLoansByBranchId(Integer branchId) throws ClassNotFoundException, SQLException {
		List<BookLoans> bookLoans = readAll("select * from tbl_author where authorId = ?", new Object[] { branchId });
		if (bookLoans != null && !bookLoans.isEmpty()) {
			return bookLoans;
		}
		return null;
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<BookLoans> bl = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BorrowerDAO brdao = new BorrowerDAO(conn);
		while (rs.next()) {
			BookLoans bkloan = new BookLoans();
			bkloan.setBook(bdao.readBookByPk(rs.getInt("bookId")));
			bkloan.setLibraryBranch(lbdao.readLibraryBranchByPk(rs.getInt("branchId")));
			bkloan.setBorrower(brdao.readBorrowerByPk(rs.getInt("cardNo")));
			bkloan.setDateOut(rs.getTimestamp("dateOut"));
			bkloan.setDueDate(rs.getTimestamp("dueDate"));
			if (rs.getTimestamp("dateIn") != null) {
				bkloan.setDateIn(rs.getTimestamp("dateIn"));
			}
			bl.add(bkloan);
		}
		return bl;
	}

	@Override
	public List<BookLoans> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<BookLoans> bl = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BorrowerDAO brdao = new BorrowerDAO(conn);
		while (rs.next()) {
			BookLoans bkloan = new BookLoans();
			bkloan.setBook(bdao.readBookByPk(rs.getInt("bookId")));
			// bkloan.setLibraryBranch(lbdao.readLibraryBranchByPk(rs.getInt("branchId")));
			bkloan.setBorrower(brdao.readBorrowerByPk(rs.getInt("cardNo")));
			bkloan.setDateOut(rs.getTimestamp("dateOut"));
			bkloan.setDueDate(rs.getTimestamp("dueDate"));
			if (rs.getTimestamp("dateIn") != null) {
				bkloan.setDateIn(rs.getTimestamp("dateIn"));
			}
			bl.add(bkloan);
		}
		return bl;
	}

}
