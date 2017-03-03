package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO {

	public LibraryBranchDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void addLibraryBranch(LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException {
		save("insert into tbl_library_branch (branchName,branchAddress) values (?,?)",
				new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress() });
	}

	public void updateLibraryBranch(Integer branchId, String branchName, String branchAddress) throws SQLException, ClassNotFoundException {
		save("UPDATE `library`.`tbl_library_branch` SET `branchName`=?, `branchAddress`=? WHERE `branchId`=?",
				new Object[] { branchName,branchAddress,branchId });
	}

	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws SQLException, ClassNotFoundException {
		save("delete * from tbl_library_branch where branchId = ?", new Object[] { libraryBranch.getBranchId() });
	}

	public List<LibraryBranch> readAllBranches() throws ClassNotFoundException, SQLException {
		return readAll("select * from tbl_library_branch", null);
	}

	public List<LibraryBranch> readAllBranchesByName(String branchName) throws ClassNotFoundException, SQLException {
		return readAll("select * from tbl_library_branch where branchName like ?",
				new Object[] { "%" + branchName + "%" });
	}

	public LibraryBranch readLibraryBranchByPk(Integer branchId) throws ClassNotFoundException, SQLException {
		List<LibraryBranch> libraryBranches = readAll("select * from tbl_library_branch where branchId = ?",
				new Object[] { branchId });
		if (libraryBranches != null && !libraryBranches.isEmpty()) {
			return libraryBranches.get(0);
		}
		return null;
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<LibraryBranch> libraryBranches = new ArrayList<>();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BookLoansDAO bldao = new BookLoansDAO(conn);
		while (rs.next()) {
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));
			lb.setBookCopies(bcdao.readAllFirstLevel(
					"SELECT * FROM library.tbl_book_copies\r\n" + "INNER JOIN tbl_book\r\n"
							+ "ON tbl_book.bookId = tbl_book_copies.bookId WHERE branchId = ?",
					new Object[] { rs.getInt("branchId") }));
			lb.setBookLoans(bldao.readAllFirstLevel("SELECT * from tbl_book_loans WHERE branchId=?",
					new Object[] { rs.getInt("branchId") }));

			libraryBranches.add(lb);
		}

		return libraryBranches;
	}

	@Override
	public List<LibraryBranch> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<LibraryBranch> libraryBranches = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));
			libraryBranches.add(lb);
		}
		return libraryBranches;
	}

}
