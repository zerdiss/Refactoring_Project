package video.rental.demo.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "VIDEO", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Video {
	@Id
	private String title;
	private Rating videoRating;

	private Price priceCode;

	public static final int REGULAR = 1;
	public static final int NEW_RELEASE = 2;
	public static final int CHILDREN = 3;


	private MediaType mediaType;

	private LocalDate registeredDate;
	private boolean rented;

	public Video() {
	} // for hibernate

	public Video(String title, int videoType, Price price, Rating videoRating, LocalDate registeredDate) {
		this.title = title;
		this.mediaType = MediaType.of(videoType);
		this.priceCode = price;
		this.videoRating = videoRating;
		this.registeredDate = registeredDate;
		this.rented = false;
	}

	public Video(Video another) {
		this.title = another.title;
		this.videoRating = another.videoRating;
		this.priceCode = another.priceCode;
		this.mediaType = another.mediaType;
		this.registeredDate = another.registeredDate;
		this.rented = another.rented;
	}

	public int getLateReturnPointPenalty() {
		return mediaType.getLateReturnPointPenalty();
	}

	public int getPriceCode() {
		return priceCode.priceCode();
	}

	public String getTitle() {
		return title;
	}

	public Rating getVideoRating() {
		return videoRating;
	}

	public boolean isRented() {
		return rented;
	}

	public void setRented(boolean rented) {
		this.rented = rented;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public int getVideoType() {
		return mediaType.getVideoType();
	}

	public boolean rentFor(Customer customer) {

		int checkAge = 0;
		switch (videoRating) {
		case TWELVE:
			checkAge = 12;
			break;
		case FIFTEEN:
			checkAge = 15;
			break;
		case EIGHTEEN:
			checkAge = 18;
			break;
		default:
			return false;
		}

		if (customer.isUnderAge(checkAge))
			return false;

		setRented(true);
		Rental rental = new Rental(this);
		customer.addRental(rental);
		return true;
	}

	public double getCharge(int daysRented) {
		return priceCode.getCharge(daysRented);
	}

	public boolean getVideoSpecialPoint() {
		if (getPriceCode() == Video.NEW_RELEASE)
			return true;
		return false;
	}

	int getDaysRentedLimit() {
		return mediaType.getDaysRentedLimit();
	}
}
