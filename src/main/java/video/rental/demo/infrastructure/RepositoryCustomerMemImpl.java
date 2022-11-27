package video.rental.demo.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import video.rental.demo.domain.Customer;

public class RepositoryCustomerMemImpl extends RepositoryCustomerImpl {

	private HashMap<Integer, Customer> customers = new LinkedHashMap<>();
	
	@Override
	public List<Customer> findAllCustomers() {
		return new ArrayList<>(customers.values());
	}

	@Override
	public Customer findCustomerById(int code) {
		return customers.get(code);
	}

	@Override
	public void saveCustomer(Customer customer) {
		customers.put(customer.getCode(), new Customer(customer));
	}

}
