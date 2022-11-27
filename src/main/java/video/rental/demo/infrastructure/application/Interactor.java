package video.rental.demo.infrastructure.application;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import video.rental.demo.domain.ChildrenPrice;
import video.rental.demo.domain.Customer;
import video.rental.demo.domain.NewReleasePrice;
import video.rental.demo.domain.Price;
import video.rental.demo.domain.Rating;
import video.rental.demo.domain.RegularPrice;
import video.rental.demo.domain.Repository;
import video.rental.demo.domain.Video;
import video.rental.demo.infrastructure.RepositoryCustomerImpl;
import video.rental.demo.infrastructure.RepositoryVideoImpl;

public class Interactor {
	private RepositoryCustomerImpl repositoryCustomer;
	private RepositoryVideoImpl repositoryVideo;

	public Interactor(RepositoryCustomerImpl repoCustomer, RepositoryVideoImpl repoVideo) {
		super();
		this.repositoryCustomer = repoCustomer;
		this.repositoryVideo = repoVideo;
	}

	public String showCustomerInfo(int customerCode) {
		Customer foundCustomer = getRepositoryCustomer().findCustomerById(customerCode);
		if (foundCustomer == null) {
			throw new IllegalArgumentException("No such customer exists");
		}
		return foundCustomer.getCustomerRentalInfo();
	}

	public void clearRentals(int customerCode) {

		Customer foundCustomer = getRepositoryCustomer().findCustomerById(customerCode);

		foundCustomer.clearRentals();
		getRepositoryCustomer().saveCustomer(foundCustomer);
	}

	public void returnVideo(int customerCode, String videoTitle) {
		Customer foundCustomer = getRepositoryCustomer().findCustomerById(customerCode);
		if (foundCustomer == null) {
			throw new IllegalArgumentException("No such customer exists");
		}

		Video video = foundCustomer.returnVideo(videoTitle);
		if (video != null)
			getRepositoryVideo().saveVideo(video);

		getRepositoryCustomer().saveCustomer(foundCustomer);
	}

	public String listVideos() {
		StringBuilder builder = new StringBuilder();

		for (Video video : getRepositoryVideo().findAllVideos()) {
			builder.append("Video type: " + video.getVideoType() + ", " + "Price code: " + video.getPriceCode() + ", "
					+ "Rating: " + video.getVideoRating() + ", " + "Title: " + video.getTitle() + "\n");
		}

		return builder.toString();
	}

	public String listCustomers() {
		StringBuilder builder = new StringBuilder();
		for (Customer customer : getRepositoryCustomer().findAllCustomers()) {
			builder.append(customer.getCustomerRentalDetailInfo());
		}
		return builder.toString();
	}

	public String getCustomerReport(int code) {
		StringBuilder builder = new StringBuilder();
		Customer foundCustomer = getRepositoryCustomer().findCustomerById(code);
		if (foundCustomer == null) {
			throw new IllegalArgumentException("No such customer exists");
		}

		builder.append(foundCustomer.getReport() + "\n");
		return builder.toString();
	}

	public void rentVideo(int code, String videoTitle) {
		Customer foundCustomer = getRepositoryCustomer().findCustomerById(code);
		if (foundCustomer == null)
			throw new IllegalArgumentException("No such customer exists");

		Video foundVideo = getRepositoryVideo().findVideoByTitle(videoTitle);
		if (foundVideo == null)
			throw new IllegalArgumentException("Cannot find the video " + videoTitle);
		if (foundVideo.isRented())
			throw new IllegalStateException("The video " + videoTitle + " is already rented");

		if (foundVideo.rentFor(foundCustomer)) {
			getRepositoryVideo().saveVideo(foundVideo);
			getRepositoryCustomer().saveCustomer(foundCustomer);
		} else {
			throw new IllegalStateException(
					"Customer " + foundCustomer.getName() + " cannot rent this video because he/she is under age.");
		}
	}

	public void registerCustomer(String name, int code, String dateOfBirth) {
		// dirty hack for the moment
		if (getRepositoryCustomer().findAllCustomers().stream().mapToInt(Customer::getCode).anyMatch(c -> c == code)) {
			throw new IllegalArgumentException("Customer code " + code + " already exists");
		}

		try {
			new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
		} catch (Exception ignored) {
		}

		getRepositoryCustomer().saveCustomer(new Customer(code, name, LocalDate.parse(dateOfBirth)));
	}

	public void registerVideo(String title, int videoType, int price, int videoRating) {
		LocalDate registeredDate = LocalDate.now();
		Rating rating;
		Price priceCode;
		if (videoRating == 1)
			rating = Rating.TWELVE;
		else if (videoRating == 2)
			rating = Rating.FIFTEEN;
		else if (videoRating == 3)
			rating = Rating.EIGHTEEN;
		else
			throw new IllegalArgumentException("No such rating " + videoRating);

		if (price == 1)
			priceCode = new RegularPrice();
		else if (price == 2)
			priceCode = new NewReleasePrice();
		else if (price == 3)
			priceCode = new ChildrenPrice();
		else
			throw new IllegalArgumentException("No such priceCode " + price);

		// dirty hack for the moment
		if (getRepositoryVideo().findAllVideos().stream().map(Video::getTitle).anyMatch(t -> t.equals(title))) {
			throw new IllegalArgumentException("Video " + title + " already exists");
		}

		getRepositoryVideo().saveVideo(new Video(title, videoType, priceCode, rating, registeredDate));
	}

	private RepositoryCustomerImpl getRepositoryCustomer() {
		return repositoryCustomer;
	}

	private RepositoryVideoImpl getRepositoryVideo() {
		return repositoryVideo;
	}

}
