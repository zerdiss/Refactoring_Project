package video.rental.demo.domain;

public class RegularPrice extends Price {
	public RegularPrice() {
		super(Video.REGULAR);
	}
	
	public double getCharge(int daysRented) {
		double charge = 2;

		if (daysRented > 2)
			charge += (daysRented - 2) * 1.5;

		return charge;
	}
}
