package com.service;

import java.sql.SQLException;
import java.util.List;

import com.dao.ILoanRepository;
import com.dao.ILoanRepositoryImpl;
import com.exceptions.InvalidCustomerException;
import com.exceptions.InvalidLoanException;
import com.model.Loan;

public class LoanManagementService {
	
	ILoanRepository loanRepository=new ILoanRepositoryImpl();

	public void validateCustomer(int customerId) 
			throws ClassNotFoundException, SQLException, InvalidCustomerException {
		
		loanRepository.validateCustomer(customerId);
	}

	public int addLoan(Loan loan) throws ClassNotFoundException, SQLException {
		
		return loanRepository.addLoan(loan);
		
	}

	public List<Loan> getAllLoans() throws ClassNotFoundException, SQLException {
		
		return loanRepository.getAllLoans();
	}

	public void validateLoanId(List<Loan> loans,int loanId) throws InvalidLoanException {
		
		boolean validate=false;
		
		for(Loan loan:loans) {
			if(loan.getLoanId()==loanId)
				validate=true;
		}
		if(!validate)
			throw new InvalidLoanException("Invalid load Id");
	}

	public Loan getLoan(List<Loan> loans,int loanId) {
		Loan loan=null;
		for(Loan l:loans) {
			if(l.getLoanId()==loanId)
				loan=l;
		}
		return loan;
	}

	public double getInterest(Loan loan) {
		
		double amount=loan.getPrincipalAmount();
		int interestRate=loan.getInterestRate();
		int loanTerm=loan.getLoanTerm();
		
		return (amount*interestRate*loanTerm)/12;
	}

	public String getStatus(Loan loanForStatus,int loanIdForStatus) throws ClassNotFoundException, SQLException {
		
		return loanRepository.getStatus(loanForStatus,loanIdForStatus);
	}

	public double calculateEmi(Loan loanForEMi) {
		
		double amount=loanForEMi.getPrincipalAmount();
		int interest=loanForEMi.getInterestRate();
		int time=loanForEMi.getLoanTerm();
		double temp=Math.pow(1+interest, time);
		
		return (amount*interest*temp)/(temp-1);
	}

	public void updateAmount(Loan loanForRepayment,double amount, int loanIdForRepayment) throws ClassNotFoundException, SQLException {
		loanRepository.updateAmount(loanForRepayment,amount,loanIdForRepayment);
	}

}
