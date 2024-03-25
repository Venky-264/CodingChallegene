package com.model;

public class HomeLoan extends Loan{
	
	private String propertyAdress;
	private int propertyValue;
	
	
	
	public HomeLoan() {
		super();
	}



	public HomeLoan(String propertyAdress, int propertyValue) {
		super();
		this.propertyAdress = propertyAdress;
		this.propertyValue = propertyValue;
	}



	public String getPropertyAdress() {
		return propertyAdress;
	}



	public void setPropertyAdress(String propertyAdress) {
		this.propertyAdress = propertyAdress;
	}



	public int getPropertyValue() {
		return propertyValue;
	}



	public void setPropertyValue(int propertyValue) {
		this.propertyValue = propertyValue;
	}



	@Override
	public String toString() {
		return "HomeLoan [propertyAdress=" + propertyAdress + ", propertyValue=" + propertyValue + "]";
	}
	
	

}
