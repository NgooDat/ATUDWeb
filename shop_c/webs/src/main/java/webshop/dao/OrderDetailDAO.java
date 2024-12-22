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

import webshop.entity.OrderDetail;
import webshop.entity.OrderDetailId;
import webshop.security.Authentication;

@Repository
public class OrderDetailDAO {
	
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

    // Lấy tất cả các chi tiết hóa đơn
    @SuppressWarnings("unchecked")
    public List<OrderDetail> getAllOrderDetails() {
    	int role = Authentication.getRole();
        Session session = getSessionFactoryBasedOnRole(role).openSession();
        try {
            return session.createQuery("FROM OrderDetail").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
 // Lấy chi tiết hóa đơn theo OrderId
    @SuppressWarnings("unchecked")
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
    	int role = Authentication.getRole();
        Session session = getSessionFactoryBasedOnRole(role).openSession();
        try {
            return session.createQuery("FROM OrderDetail od WHERE od.id.ordersID = :orderId")
                          .setParameter("orderId", orderId)
                          .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }


    // Lấy chi tiết hóa đơn theo ID
    public OrderDetail getOrderDetailById(OrderDetailId id) {
    	int role = Authentication.getRole();
        Session session = getSessionFactoryBasedOnRole(role).openSession();
        try {
            return (OrderDetail) session.get(OrderDetail.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    // Thêm mới chi tiết hóa đơn
    public boolean addOrderDetail(OrderDetail orderDetail) {
        Transaction transaction = null;
        int role = Authentication.getRole();
        Session session = getSessionFactoryBasedOnRole(role).openSession();
        try {
            transaction = session.beginTransaction();
            session.save(orderDetail);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    // Cập nhật chi tiết hóa đơn
    public boolean updateOrderDetail(OrderDetail orderDetail) {
        Transaction transaction = null;
        int role = Authentication.getRole();
        Session session = getSessionFactoryBasedOnRole(role).openSession();
        try {
            transaction = session.beginTransaction();
            session.update(orderDetail);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    // Xóa chi tiết hóa đơn
    public boolean deleteOrderDetail(OrderDetailId id) {
        Transaction transaction = null;
        int role = Authentication.getRole();
        Session session = getSessionFactoryBasedOnRole(role).openSession();
        try {
            transaction = session.beginTransaction();
            OrderDetail orderDetail = (OrderDetail) session.get(OrderDetail.class, id);
            if (orderDetail != null) {
                session.delete(orderDetail);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
}
