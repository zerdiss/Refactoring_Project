package video.rental.demo.domain;

import java.io.Serializable;

public abstract class Price implements Serializable {
	private int _priceCode;

	public Price(int price) {
		_priceCode = price;
	}
	
	public int priceCode() {
		return _priceCode;
	}
	
	abstract double getCharge(int dayRented);
}
