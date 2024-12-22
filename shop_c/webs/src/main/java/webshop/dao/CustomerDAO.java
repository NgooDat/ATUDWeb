package webshop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import webshop.entity.Customer;
import webshop.security.Authentication;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Repository
public class CustomerDAO {

	@Autowired
    private SessionFactory adminSessionFactory;
    
    @Autowired
    private SessionFactory employeeSessionFactory;
    
    @Autowired
    private SessionFactory userSessionFactory;

    @Autowired
    private SessionFactory guestSessionFactory;

    // Lấy SessionFactory dựa trên vai trò
    private SessionFactory getSessionFactoryBasedOnRole(int role) {
        if (role == 1 ) {
            return adminSessionFactory;
        } else if (role == 2) {
            return employeeSessionFactory;
        } else if (role == 3 ) {
            return userSessionFactory;
        } else if(role == 0) {
        	return guestSessionFactory;
        }
        else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalStateException("Không có request hiện tại.");
        }
        return (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    }

    // Create new customer
    public boolean createCustomer(Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error creating customer", e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
 // Get customers by account ID
    public Customer getCustomerByAccountID(int accountId) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            String hql = "FROM Customer WHERE account.id = :accountId";
            return (Customer) session.createQuery(hql)
                    .setParameter("accountId", accountId)
                    .uniqueResult();
        } catch (Exception e) {
            logError("Error getting customer by Account ID: " + accountId, e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Read customer by ID
    public Customer getCustomerById(int id) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return (Customer) session.get(Customer.class, id);
        } catch (Exception e) {
            logError("Error getting customer by ID: " + id, e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Update a customer
    public boolean updateCustomer(Customer customer) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession(); 
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error updating customer", e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Delete a customer
    public boolean deleteCustomer(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            Customer customer = (Customer) session.get(Customer.class, id);
            if (customer != null) {
                session.delete(customer);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error deleting customer with ID: " + id, e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Get all customers
    @SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return session.createQuery("FROM Customer").list();
        } catch (Exception e) {
            logError("Error getting all customers", e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Log error method
    private void logError(String message, Exception e) {
        // Replace with a logging framework like SLF4J or Log4j
        System.err.println(message);
        e.printStackTrace();
    }
}
//@@te
