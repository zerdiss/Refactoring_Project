package video.rental.demo.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer {
	@Id
	private int code;
	private String name;
	private LocalDate dateOfBirth;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Rental> rentals = new ArrayList<>();

	public Customer() { // for hibernate
	}

	public Customer(int code, String name, LocalDate dateOfBirth) {
		this.code = code;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	// copy ctor
	public Customer(Customer another) {
		this.code = another.code;
		this.name = another.name;
		this.dateOfBirth = another.dateOfBirth;
		this.rentals = new ArrayList<>(another.rentals);
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public Boolean addRental(Rental rental) {
		return rentals.add(rental);
	}

	public void clearRentals() {
		rentals.clear();
	}

	public Video returnVideo(String videoTitle) {
		for (Rental rental : rentals) {
			if (findRentedVideo(videoTitle, rental)) {
				Video video = rental.returnVideo();
				video.setRented(false);
				return video;
			}
		}
		return null;
	}

	private boolean findRentedVideo(String videoTitle, Rental rental) {
		return rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented();
	}

	public String getCustomerRentalInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append("Id: " + getCode() + ", " + "Name: " + getName() + ", " + "Rentals: " + rentals.size() + "\n");
		for (Rental rental : rentals) {
			builder.append(rental.getRentalInfo());
		}
		return builder.toString();
	}

	public String getCustomerRentalDetailInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: " + getCode() + ", " + "Name: " + getName() + ", " + "Rentals: " + rentals.size() + "\n");
		for (Rental rental : rentals) {
			builder.append(rental.getRentalInfoDetail());
		}
		return builder.toString();
	}

	public int getRentalVideoCount() {
		return rentals.size();
	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {
			double eachCharge = 0;
			int eachPoint = 0;
			int daysRented = each.getDaysRented();

			eachCharge += each.getVideo().getCharge(daysRented);

			eachPoint++;
			if (each.getVideo().getVideoSpecialPoint())
				eachPoint++;

			if (daysRented > each.getDaysRentedLimit())
				eachPoint -= Math.min(eachPoint, each.getVideo().getLateReturnPointPenalty());

			result += "\tTitle: " + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: "
					+ eachCharge + "\tPoint: " + eachPoint + "\n";

			totalCharge += eachCharge;
			totalPoint += eachPoint;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point: " + totalPoint + "\n";

		if (totalPoint >= 10) {
			System.out.println("Congratulations! You earned two free coupons");
		} else if (totalPoint >= 5) {
			System.out.println("Congratulations! You earned one free coupon");
		}

		return result;
	}

	private int getAge() {
		// parse customer date of birth
		Calendar calDateOfBirth = Calendar.getInstance();
		try {
			calDateOfBirth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(getDateOfBirth().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// get current date
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(new java.util.Date());

		// calculate age different in years and months
		int ageYr = (calNow.get(Calendar.YEAR) - calDateOfBirth.get(Calendar.YEAR));
		int ageMo = (calNow.get(Calendar.MONTH) - calDateOfBirth.get(Calendar.MONTH));

		// decrement age in years if month difference is negative
		if (ageMo < 0) {
			ageYr--;
		}
		int age = ageYr;
		return age;
	}

	public boolean isUnderAge(int checkAge) {
		return getAge() < checkAge;
	}

}
