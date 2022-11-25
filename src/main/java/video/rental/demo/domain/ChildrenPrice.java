package video.rental.demo.domain;

public class ChildrenPrice extends Price {
	public ChildrenPrice() {
		super(Video.CHILDREN);
	}
	
	public double getCharge(int daysRented) {
		double charge = 1.5;

		if (daysRented > 3)
			charge += (daysRented - 3) * 1.5;

		return charge;
	}
}
