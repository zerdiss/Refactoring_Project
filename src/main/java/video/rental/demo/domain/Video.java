package video.rental.demo.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "VIDEO", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Video {
    @Id
    private String title;
    private Rating videoRating;

    private Price priceCode;

    public static final int REGULAR = 1;
    public static final int NEW_RELEASE = 2;
    public static final int CHILDREN = 3;

    private VideoType videoType;
    public static final int VHS = 1;
    public static final int CD = 2;
    public static final int DVD = 3;

    private LocalDate registeredDate;
    private boolean rented;

    public Video() {
    } // for hibernate

    public Video(String title, int videoType, Price price, Rating videoRating, LocalDate registeredDate) {
        this.title = title;
        this.videoType = VideoType.of(videoType);
        this.priceCode = price;
        this.videoRating = videoRating;
        this.registeredDate = registeredDate;
        this.rented = false;
    }

    public Video(Video another) {
        this.title = another.title;
        this.videoRating = another.videoRating;
        this.priceCode = another.priceCode;
        this.videoType = another.videoType;
        this.registeredDate = another.registeredDate;
        this.rented = another.rented;
    }

    public int getLateReturnPointPenalty() {
        return videoType.getLateReturnPointPenalty();
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
        return videoType.getVideoType();
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
        List<Rental> customerRentals = customer.getRentals();
        customerRentals.add(rental);
        customer.setRentals(customerRentals);
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
        return videoType.getDaysRentedLimit();
    }
}
