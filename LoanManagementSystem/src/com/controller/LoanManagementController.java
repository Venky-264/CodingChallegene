package com.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.enums.LoanStatus;
import com.enums.LoanType;
import com.exceptions.InvalidCustomerException;
import com.exceptions.InvalidLoanException;
import com.model.Loan;
import com.service.LoanManagementService;

public class LoanManagementController {

	public static void main(String[] args) {

		LoanManagementService loanManagementService = new LoanManagementService();

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("press 1.for apply loan ");
			System.out.println("press 2.to calculate interest for a loan");
			System.out.println("press 3.for displaying loan status");
			System.out.println("press 4. for calculating the EMI");
			System.out.println("press 5. for Loan Repayment");
			System.out.println("press 6. to display all loans");
			System.out.println("press 7. to dsiplay loan by Id");
			System.out.println("press 0. to exit...");

			int choice = sc.nextInt();

			if (choice == 0) {
				System.out.println("EXITING....");
				break;
			}
			switch (choice) {
			case 1:

				System.out.println("enter customerId");
				int customerId = sc.nextInt();

				try {
					loanManagementService.validateCustomer(customerId);
					System.out.println("enter amount");
					double principalAmount = sc.nextDouble();
					System.out.println("enter rate of interest");
					int interestRate = sc.nextInt();

					System.out.println("enter loan term/loan tenure");
					int loanTerm = sc.nextInt();

					sc.nextLine();
					System.out.println("enter loan type----HomeLoan/CarLoan");

					String loantype = sc.nextLine();
					if (!(loantype.toUpperCase().equals("HOMELOAN") || loantype.toUpperCase().equals("CARLOAN"))) {
						System.out.println("Invalid loan type");
						continue;
					}
					LoanType loanType = LoanType.valueOf(loantype.toUpperCase());
					LoanStatus loanStatus = LoanStatus.valueOf("PENDING");

					Loan loan = new Loan(customerId, principalAmount, interestRate, loanTerm, loanType, loanStatus);

					int result = loanManagementService.addLoan(loan);

					if (result == 1)
						System.out.println("Loan Under Pending");
					else
						System.out.println("Loan aborted");

				} catch (ClassNotFoundException | SQLException | InvalidCustomerException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 2:
				System.out.println("enter the loanId");
				int loanIdForInterest = sc.nextInt();
				try {
					List<Loan> loans = loanManagementService.getAllLoans();
					loanManagementService.validateLoanId(loans, loanIdForInterest);
					Loan loan = loanManagementService.getLoan(loans, loanIdForInterest);
					double interest=loanManagementService.getInterest(loan);
					System.out.println("interest is "+interest);

				} catch (ClassNotFoundException | SQLException | InvalidLoanException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("enter the loanId");
				int loanIdForStatus = sc.nextInt();
				
				try {
					List<Loan> loansList= loanManagementService.getAllLoans();
					loanManagementService.validateLoanId(loansList, loanIdForStatus);
					Loan loanForStatus = loanManagementService.getLoan(loansList, loanIdForStatus);
					String status=loanManagementService.getStatus(loanForStatus,loanIdForStatus);
					System.out.println(status);
					
				} catch (ClassNotFoundException | SQLException | InvalidLoanException e1) {
					System.out.println(e1.getMessage());
				}
				
				break;
			case 4:
				
				System.out.println("enter the loanId");
				int loanIdForEmi = sc.nextInt();
				
				try {
					List<Loan> loansList= loanManagementService.getAllLoans();
					loanManagementService.validateLoanId(loansList, loanIdForEmi);
					Loan loanForEMi = loanManagementService.getLoan(loansList, loanIdForEmi);
					double EMI=loanManagementService.calculateEmi(loanForEMi);
					System.out.println(EMI);
					
				} catch (ClassNotFoundException | SQLException | InvalidLoanException e1) {
					System.out.println(e1.getMessage());
				}
				
				
				break;
			case 5:
				
				System.out.println("enter the loanId");
				int loanIdForRepayment = sc.nextInt();
				
				
				try {
					List<Loan> loansList= loanManagementService.getAllLoans();
					loanManagementService.validateLoanId(loansList, loanIdForRepayment);
					System.out.println("enter the amount to pay");
					double amount=sc.nextDouble();
					Loan loanForRepayment = loanManagementService.getLoan(loansList, loanIdForRepayment);
					double EMI=loanManagementService.calculateEmi(loanForRepayment);
					
					if(amount<EMI) {
						System.out.println("payment Rejected");
						continue;
					}
					
					loanManagementService.updateAmount(loanForRepayment,amount,loanIdForRepayment);
					System.out.println("EMI paid successfully");
					
				} catch (ClassNotFoundException | SQLException | InvalidLoanException e1) {
					System.out.println(e1.getMessage());
				}
				
				break;
			case 6:

				try {
					List<Loan> loans = loanManagementService.getAllLoans();
					System.out.println("all loans");
					System.out.println(
							"loanId    CustomerId     Amount    rateOfInterest     loanTerm    loanType   loanStatus");
					for (Loan loan : loans) {
						System.out.println(loan.getLoanId() + "        " + loan.getCustomerId() + "          "
								+ loan.getPrincipalAmount() + "              " + loan.getInterestRate() + "          "
								+ loan.getLoanTerm() + "          " + loan.getLoanType() + "         "
								+ loan.getLoanStatus());

					}
				} catch (ClassNotFoundException | SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				System.out.println("enter the loanId");
				int loanId = sc.nextInt();
				try {
					List<Loan> loans = loanManagementService.getAllLoans();
					loanManagementService.validateLoanId(loans, loanId);
					Loan loan = loanManagementService.getLoan(loans, loanId);
					System.out.println(loan);

				} catch (ClassNotFoundException | SQLException | InvalidLoanException e) {
					System.out.println(e.getMessage());
				}

				break;
			default:
				System.out.println("invalid option");
			}

			System.out.println("\n");

		}
		sc.close();

	}

}
