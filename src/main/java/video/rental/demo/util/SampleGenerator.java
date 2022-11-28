package video.rental.demo.util;

import java.time.LocalDate;

import video.rental.demo.domain.*;
import video.rental.demo.infrastructure.RepositoryCustomerImpl;
import video.rental.demo.infrastructure.RepositoryVideoImpl;

public class SampleGenerator {
	private RepositoryCustomerImpl repositoryCustomer;
	private RepositoryVideoImpl repositoryVideo;

	public SampleGenerator(RepositoryCustomerImpl repoCustomer, RepositoryVideoImpl repoVideo) {
		this.repositoryCustomer = repoCustomer;
		this.repositoryVideo = repoVideo;
	}

	public void generateSamples() {
		Customer james = new Customer(0, "James", LocalDate.parse("1975-05-15"));
		Customer brown = new Customer(1, "Brown", LocalDate.parse("2002-03-17"));
		Customer shawn = new Customer(2, "Shawn", LocalDate.parse("2010-11-11"));
		repositoryCustomer.saveCustomer(james);
		repositoryCustomer.saveCustomer(brown);
		repositoryCustomer.saveCustomer(shawn);

		Video v1 = new Video("V1", MediaType.CD, new RegularPrice(), Rating.FIFTEEN, LocalDate.of(2018, 1, 1));
		v1.setRented(true);
		Video v2 = new Video("V2", MediaType.DVD, new NewReleasePrice(), Rating.TWELVE, LocalDate.of(2018, 3, 1));
		v2.setRented(true);
		Video v3 = new Video("V3", MediaType.VHS, new NewReleasePrice(), Rating.EIGHTEEN, LocalDate.of(2018, 3, 1));

		repositoryVideo.saveVideo(v1);
		repositoryVideo.saveVideo(v2);
		repositoryVideo.saveVideo(v3);

		Rental r1 = new Rental(v1);
		Rental r2 = new Rental(v2);

		james.addRental(r1);
		james.addRental(r2);
		repositoryCustomer.saveCustomer(james);
	}

}
