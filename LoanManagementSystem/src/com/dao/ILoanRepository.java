package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.exceptions.InvalidCustomerException;
import com.model.Loan;

public interface ILoanRepository {

	public void validateCustomer(int customerId)  
			throws ClassNotFoundException, SQLException,InvalidCustomerException ;

	public int addLoan(Loan loan)
			throws ClassNotFoundException, SQLException;

	public List<Loan> getAllLoans() throws ClassNotFoundException, SQLException;

	public String getStatus(Loan loanForStatus,int loanIdForStatus)
			 throws ClassNotFoundException, SQLException ;

	public void updateAmount(Loan loanForRepayment,double amount, int loanIdForRepayment)
			throws ClassNotFoundException, SQLException ;

}
