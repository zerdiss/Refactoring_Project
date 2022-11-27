package video.rental.demo.domain;

import video.rental.demo.infrastructure.PersistenceManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Repository {

	/*
	 * Database Access private methods
	 */

	// JPA EntityManager
	private static final EntityManager em = PersistenceManager.INSTANCE.getEntityManager();

	public <T> T find(Supplier<T> action) {
		T value = null;
		try {
			getEm().getTransaction().begin();
			value = action.get();
			getEm().getTransaction().commit();
		} catch (PersistenceException ex) {
			ex.printStackTrace();
		}
		return value;
	}

	public <T> void doIt(T value, Consumer<T> action) {
		try {
			getEm().getTransaction().begin();
			action.accept(value);
			getEm().getTransaction().commit();
		} catch (PersistenceException ex) {
			ex.printStackTrace();
		}
	}

	public static EntityManager getEm() {
		return em;
	}

//	List<Customer> findAllCustomers();
//
//	List<Video> findAllVideos();
//
//	Customer findCustomerById(int code);
//
//	Video findVideoByTitle(String title);
//
//	void saveCustomer(Customer customer);
//
//	void saveVideo(Video video);

}