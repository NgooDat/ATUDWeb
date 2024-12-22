package webshop.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import webshop.entity.Order;
import webshop.security.Authentication;

@Repository
public class OrderDAO {

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

    // Lấy tất cả các đơn hàng
    @SuppressWarnings("unchecked")
    public List<Order> getAllOrders() {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return session.createQuery("FROM Order").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Lấy đơn hàng theo ID
    public Order getOrderById(int orderId) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return (Order) session.get(Order.class, orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Thêm mới đơn hàng
    public boolean addOrder(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Cập nhật đơn hàng
    public boolean updateOrder(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Xóa đơn hàng
    public boolean deleteOrder(int orderId) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            Order order = (Order) session.get(Order.class, orderId);
            if (order != null) {
                session.delete(order);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    // Lấy danh sách đơn hàng theo CustomerId
    @SuppressWarnings("unchecked")
    public List<Order> getOrdersByCustomerId(int customerId) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            String hql = "FROM Order o WHERE o.customer.id = :customerId";
            return session.createQuery(hql)
                    .setParameter("customerId", customerId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }
}
//@@te
