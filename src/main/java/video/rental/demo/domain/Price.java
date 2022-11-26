package video.rental.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PRICE_CODE", uniqueConstraints = {@UniqueConstraint(columnNames = {"_priceCode"})})
public abstract class Price {
	@Id
	private int _priceCode;

	public Price(int price) {
		_priceCode = price;
	}
	
	public int priceCode() {
		return _priceCode;
	}
	
	abstract double getCharge(int dayRented);
}
