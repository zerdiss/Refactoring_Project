package video.rental.demo.domain;

public abstract class Price {
	private int _priceCode;

	public Price(int price) {
		_priceCode = price;
	}
	
	public int priceCode() {
		return _priceCode;
	}
	
	abstract double getCharge(int dayRented);
}
