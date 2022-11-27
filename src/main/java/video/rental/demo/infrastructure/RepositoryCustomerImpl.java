package video.rental.demo.infrastructure;

import java.util.List;
import javax.persistence.TypedQuery;

import video.rental.demo.domain.Customer;
import video.rental.demo.domain.Repository;

public class RepositoryCustomerImpl extends Repository {

    public List<Customer> findAllCustomers() {
        TypedQuery<Customer> query = getEm().createQuery("SELECT c FROM Customer c", Customer.class);
        return query.getResultList();
    }

    public Customer findCustomerById(int code) {
        return find(() -> getEm().find(Customer.class, code));
    }

    public void saveCustomer(Customer customer) {
        doIt(customer, getEm()::persist);
    }
}
