package video.rental.demo.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Rental {

	@Id
	@GeneratedValue
	private int id;

	private int status; // 0 for Rented, 1 for Returned
	private LocalDateTime rentDate;
	private LocalDateTime returnDate;

	@OneToOne(fetch = FetchType.EAGER)
	private Video video;

	public Rental() { // for hibernate
	}

	public Rental(Video video) {
		this.video = video;
		this.status = 0;
		this.rentDate = LocalDateTime.now();
	}

	public Rental(Rental another) {
		this.id = another.id;
		this.status = another.status;
		this.rentDate = another.rentDate;
		this.returnDate = another.returnDate;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public int getStatus() {
		return status;
	}

	public Video returnVideo() {
		if (status == 0) {
			setReturnDate(LocalDateTime.now());
		}
		return video;
	}

	public LocalDateTime getRentDate() {
		return rentDate;
	}

	public void setRentDate(LocalDateTime rentDate) {
		this.rentDate = rentDate;
	}

	public LocalDateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDateTime returnDate) {
		if (returnDate.isBefore(this.rentDate))
			throw new IllegalArgumentException("Returned date cannot precede the rented date");
		this.status = 1;
		this.returnDate = returnDate;
	}

	public int getDaysRentedLimit() {
		return video.getDaysRentedLimit();
	}

	public int getDaysRented() {
		LocalDateTime end = (getStatus() == 1) ? getReturnDate() : LocalDateTime.now();
		return (int) (ChronoUnit.HOURS.between(getRentDate(), end) / 24) + 1;
	}

	public String getRentalInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tTitle: " + video.getTitle() + ", " + "Price Code: " + video.getPriceCode());
		return builder.toString();
	}

	public String getRentalInfoDetail() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tTitle: " + video.getTitle() + ", " + "Price Code: " + video.getPriceCode() + ", "
				+ "Return Status: " + status + "\n");
		return builder.toString();
	}
}
