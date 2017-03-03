package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO {
	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
		save("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)", new Object[] {
				publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone() });
	}

	public Integer addPublisherWithID(Publisher publisher) throws ClassNotFoundException, SQLException {
		return saveWithID("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (? , ?, ?)",
				new Object[] { publisher.getPublisherAddress(), publisher.getPublisherPhone() });
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPublisherAddress(), publisher.getPublisherPhone(),
						publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("delete * from tbl_publisher where publisherId= ? ", new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAllPublisher() throws ClassNotFoundException, SQLException {
		return readAll("select * from tbl_publisher", null);
	}

	public Publisher readPublisherByPk(Integer publisherId) throws ClassNotFoundException, SQLException {
		List<Publisher> publishers = readAll("select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId });
		if (publishers != null && !publishers.isEmpty()) {
			return publishers.get(0);
		}
		return null;
	}

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Publisher> publishers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setBooks(bdao.readAllFirstLevel("SELECT * FROM tbl_book WHERE pubId=?",
					new Object[] { rs.getInt("publisherId") }));
			publishers.add(p);
		}
		return publishers;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
	}

}
