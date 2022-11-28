package video.rental.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import video.rental.demo.domain.*;

class VideoTest {

	static final LocalDate UNUSED_DATE = null;

	@Test
	void should_not_allow_rent_if_customer_under_age() {
		// Given
		Video video = new Video("ANY_TITLE", MediaType.CD, new RegularPrice(), Rating.FIFTEEN, UNUSED_DATE);
		Customer shawn = new Customer(2, "Shawn", LocalDate.now().minusYears(13));

		// When
		boolean result = video.rentFor(shawn);

		// Then
		assertFalse(result);
	}

	@Test
	void should_create_rental_if_customer_of_legal_age() {
		// Given
		Video video = new Video("ANY_TITLE", MediaType.VHS, new NewReleasePrice(), Rating.EIGHTEEN, UNUSED_DATE);
		Customer james = new Customer(0, "James", LocalDate.now().minusYears(20));

		// When
		boolean result = video.rentFor(james);

		// Then
		assertAll("Valid rent", () -> assertTrue(result), () -> assertEquals(1, james.getRentalVideoCount()),
				() -> assertTrue(video.isRented()));
	}

}