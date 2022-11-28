package video.rental.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import video.rental.demo.domain.*;

class CustomerTest {

	static final LocalDate UNUSED_DATE = null;

	final Customer CUSTOMER = new Customer(0, "James", UNUSED_DATE);
	final Video VIDEO_1 = new Video("V1", MediaType.CD, new RegularPrice(), Rating.FIFTEEN, UNUSED_DATE);
	final Video VIDEO_2 = new Video("V2", MediaType.DVD, new ChildrenPrice(), Rating.TWELVE, UNUSED_DATE);
	final Video VIDEO_3 = new Video("V3", MediaType.VHS, new NewReleasePrice(), Rating.EIGHTEEN, UNUSED_DATE);

	@Test
	void setRentals_emptyList_clears_rentals() {
		// Given

		// When
		CUSTOMER.clearRentals();

		// Then
		assertEquals(0, CUSTOMER.getRentalVideoCount());
	}

	@Test
	void setRentals_nonEmptyList_sets_rentals() {
		// Given
		Rental RENTAL_1 = new Rental(VIDEO_1);
		Rental RENTAL_2 = new Rental(VIDEO_2);

		// When
		CUSTOMER.clearRentals();
		CUSTOMER.addRental(RENTAL_1);
		CUSTOMER.addRental(RENTAL_2);

		// Then
		assertEquals(2, CUSTOMER.getRentalVideoCount());
	}

	@Test
	@DisplayName("CD, REGULAR, daysRented(2) <= 3, do not exceed limit(3)")
	void getReport_test1() {
		// Given
		Rental rental = new Rental(VIDEO_1);
		rental.setReturnDate(rental.getRentDate().plusHours(24 + 23));

		CUSTOMER.clearRentals();
		CUSTOMER.addRental(rental);

		// When
		String report = CUSTOMER.getReport();

		// Then
		assertEquals("Customer Report for James\n\tTitle: V1\tDays rented: 2"
				+ "\tCharge: 2.0\tPoint: 1\nTotal charge: 2.0\tTotal Point: 1\n", report);
	}

	@Test
	@DisplayName("CD, REGULAR, daysRented(4) > 3, do exceed limit(3)")
	void getReport_test2() {
		// Given
		Rental rental = new Rental(VIDEO_1);
		rental.setReturnDate(rental.getRentDate().plusHours(24 * 3 + 23));
		CUSTOMER.clearRentals();
		CUSTOMER.addRental(rental);

		// When
		String report = CUSTOMER.getReport();

		// Then
		assertEquals("Customer Report for James\n\tTitle: V1"
				+ "\tDays rented: 4\tCharge: 5.0\tPoint: 0\nTotal charge: 5.0" + "\tTotal Point: 0\n", report);
	}

	@Test
	@DisplayName("VHS, NEW_RELEASE, do not exceed limit(5)")
	void getReport_test3() {
		// Given
		Rental rental = new Rental(VIDEO_3);
		rental.setReturnDate(rental.getRentDate().plusHours(24 + 23));
		CUSTOMER.clearRentals();
		CUSTOMER.addRental(rental);

		// When
		String report = CUSTOMER.getReport();

		// Then
		assertEquals("Customer Report for James\n\tTitle: V3\tDays rented: 2"
				+ "\tCharge: 6.0\tPoint: 2\nTotal charge: 6.0\tTotal Point: 2\n", report);
	}

	@Test
	@DisplayName("DVD, CHILDREN, daysRented(5) > 2, do exceed limit(2)")
	void getReport_test4() {
		// Given
		Rental rental = new Rental(VIDEO_2);
		rental.setReturnDate(rental.getRentDate().plusHours(24 * 4 + 23));
		CUSTOMER.clearRentals();
		CUSTOMER.addRental(rental);

		// When
		String report = CUSTOMER.getReport();

		// Then
		assertEquals("Customer Report for James\n\tTitle: V2\tDays rented: 5"
				+ "\tCharge: 4.5\tPoint: 0\nTotal charge: 4.5\tTotal Point: 0\n", report);
	}
}
