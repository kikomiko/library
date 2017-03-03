package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Borrower implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 2808685318571824354L;
	private int cardNo;
	private String borrowerName;
	private String borrowerAddress;
	private String borrowerPhone;
	private List<BookLoans> bookLoans;

	public List<BookLoans> getBookLoans() {
		return bookLoans;
	}

	public void setBookLoans(List<BookLoans> bookLoans) {
		this.bookLoans = bookLoans;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((borrowerName == null) ? 0 : borrowerName.hashCode());
		result = prime * result + cardNo;
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
		Borrower other = (Borrower) obj;
		if (borrowerName == null) {
			if (other.borrowerName != null)
				return false;
		} else if (!borrowerName.equals(other.borrowerName))
			return false;
		if (cardNo != other.cardNo)
			return false;
		return true;
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerAddress() {
		return borrowerAddress;
	}

	public void setBorrowerAddress(String borrowerAddress) {
		this.borrowerAddress = borrowerAddress;
	}

	public String getBorrowerPhone() {
		return borrowerPhone;
	}

	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}

}
