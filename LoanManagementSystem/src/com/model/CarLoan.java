package com.model;

public class CarLoan extends Loan{

	private String carModel;
	private int carValue;
	
	public CarLoan() {
		super();
	}

	public CarLoan(String carModel, int carValue) {
		super();
		this.carModel = carModel;
		this.carValue = carValue;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public int getCarValue() {
		return carValue;
	}

	public void setCarValue(int carValue) {
		this.carValue = carValue;
	}

	@Override
	public String toString() {
		return "CarLoan [carModel=" + carModel + ", carValue=" + carValue + "]";
	}

	
}
