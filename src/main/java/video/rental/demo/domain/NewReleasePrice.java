package video.rental.demo.domain;

public class NewReleasePrice extends Price {
	public NewReleasePrice() {
		super(Video.NEW_RELEASE);
	}
	
	public double getCharge(int daysRented) {
		return (double) (daysRented * 3);
	}
}
