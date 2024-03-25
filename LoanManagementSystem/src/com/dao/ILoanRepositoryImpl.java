package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.enums.LoanStatus;
import com.enums.LoanType;
import com.exceptions.InvalidCustomerException;
import com.model.Loan;
import com.utility.DBConnection;

public class ILoanRepositoryImpl implements ILoanRepository {

	@Override
	public void validateCustomer(int customerId) throws ClassNotFoundException, SQLException, InvalidCustomerException {

		Connection conn = DBConnection.getDBConn();

		String query = "select * from customer where customer_id=?";

		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, customerId);

		ResultSet result = pstmt.executeQuery();

		if (!result.next()) {
			DBConnection.dbClose();
			throw new InvalidCustomerException("Customer not found");
		}

		DBConnection.dbClose();
	}

	@Override
	public int addLoan(Loan loan) throws ClassNotFoundException, SQLException {

		Scanner sc = new Scanner(System.in);
		Connection conn = DBConnection.getDBConn();

		String query = "insert into loan(principal_amount,interest_rate,loan_term,loan_type,loan_status,customer_id) values(?,?,?,?,?,?)";

		double principalAmount = loan.getPrincipalAmount();
		int interestRate = loan.getInterestRate();
		int loanTerm = loan.getLoanTerm();
		String loanType = loan.getLoanType().toString().toUpperCase();
		String loanStatus = loan.getLoanStatus().toString().toUpperCase();
		int customerId = loan.getCustomerId();

		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setDouble(1, principalAmount);
		pstmt.setInt(2, interestRate);
		pstmt.setInt(3, loanTerm);
		pstmt.setString(4, loanType);
		pstmt.setString(5, loanStatus);
		pstmt.setInt(6, customerId);

		System.out.print("Loan confirmation Yes/No :");
		String confirmation = sc.next();

		if (confirmation.toUpperCase().equals("YES")) {
			pstmt.executeUpdate();
			DBConnection.dbClose();
			sc.close();
			return 1;

		}

		DBConnection.dbClose();
		sc.close();
		return 0;
	}

	@Override
	public List<Loan> getAllLoans() throws ClassNotFoundException, SQLException {

		Connection conn = DBConnection.getDBConn();

		String query = "select * from loan";

		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(query);

		List<Loan> loans = new ArrayList<>();

		while (result.next()) {
			int loanId = result.getInt("loan_id");
			double principalAmount = result.getDouble("principal_amount");
			int interestRate = result.getInt("interest_rate");
			int loanTerm = result.getInt("loan_term");
			LoanType loanType = LoanType.valueOf(result.getString("loan_type").toUpperCase());
			LoanStatus loanStatus = LoanStatus.valueOf(result.getString("loan_status").toUpperCase());
			int customerId = result.getInt("customer_id");

			Loan loan = new Loan(loanId, customerId, principalAmount, interestRate, loanTerm, loanType, loanStatus);

			loans.add(loan);
		}

		DBConnection.dbClose();
		return loans;
	}

	@Override
	public String getStatus(Loan loanForStatus,int loanIdForStatus) throws ClassNotFoundException, SQLException {

		Connection conn = DBConnection.getDBConn();

		String query = "select * from customer where customer_id=?";
		String query1="update loan set loan_status=? where loan_id=?";
		
		PreparedStatement pstmt=conn.prepareStatement(query);
		pstmt.setInt(1, loanForStatus.getCustomerId());
		
		ResultSet result = pstmt.executeQuery();
		int creditScore=0;
		
		while(result.next()) {
			creditScore=result.getInt("credit_score");
		}
		
		if(creditScore<650) {
			DBConnection.dbClose();
			return "REJECTED";
		}
		
		PreparedStatement pstmt1=conn.prepareStatement(query1);
		pstmt1.setString(1, "APPROVED");
		pstmt1.setInt(2, loanIdForStatus);
		
		pstmt1.executeUpdate();
		
		DBConnection.dbClose();
		return "APPROVED";
	}
	
	@Override
	public void updateAmount(Loan loanForRepayment,double amount, int loanIdForRepayment)
			throws ClassNotFoundException, SQLException {
		
		Connection conn = DBConnection.getDBConn();
		
		String query="update loan set principal_amount=principal_amount-? where loan_id=?";
		PreparedStatement pstmt=conn.prepareStatement(query);
		pstmt.setDouble(1, loanForRepayment.getPrincipalAmount()-amount);
		pstmt.setInt(2, loanIdForRepayment);
		
		pstmt.executeUpdate();
		
		DBConnection.dbClose();
	}
	
}
