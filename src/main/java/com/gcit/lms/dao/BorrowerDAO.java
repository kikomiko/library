package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO {

	public BorrowerDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void addBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("insert into tbl_borrower (name,address,phone) values (?,?,?)", new Object[] { borrower.getBorrowerName(),
				borrower.getBorrowerAddress(), borrower.getBorrowerPhone() });
	}

	public Integer addBorrowerWithId(Borrower borrower) throws SQLException, ClassNotFoundException {
		return saveWithID("insert into tbl_borrower (name,address,phone) values (?,?,?)", new Object[] {
				borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone() });
	}

	public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("update tbl_borrower set name=?,address=?,phone=? where cardNo = ?",
				new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone(),
						borrower.getCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("delete * from tbl_borrower where cardNo=?", new Object[] { borrower.getCardNo() });
	}

	public Borrower readBorrowerByPk(Integer borrowerId) throws ClassNotFoundException, SQLException {
		List<Borrower> borrowers = readAll("select * from tbl_borrower where cardNo = ?", new Object[] { borrowerId });
		if (borrowers != null && !borrowers.isEmpty()) {
			return borrowers.get(0);
		}
		return null;
	}

	public List<Borrower> readAllBorrower() throws ClassNotFoundException, SQLException {
		return readAll("select * from tbl_borrower", null);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Borrower> borrowerList = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setBorrowerName(rs.getString("name"));
			borrower.setBorrowerAddress(rs.getString("address"));
			borrower.setBorrowerPhone(rs.getString("phone"));
			borrowerList.add(borrower);
		}

		return borrowerList;
	}

	@Override
	public <T> List<T> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
