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

import webshop.entity.Reason;
import webshop.security.Authentication;

@Repository
public class ReasonDAO {

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


    // Lấy tất cả lý do
    @SuppressWarnings("unchecked")
    public List<Reason> getAllReasons() {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return session.createQuery("FROM Reason").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Lấy lý do theo ID
    public Reason getReasonById(int id) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return (Reason) session.get(Reason.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Thêm mới lý do
    public boolean addReason(Reason reason) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            session.save(reason);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Cập nhật lý do
    public boolean updateReason(Reason reason) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            session.update(reason);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Xóa lý do
    public boolean deleteReason(int id) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            Reason reason = (Reason) session.get(Reason.class, id);
            if (reason != null) {
                session.delete(reason);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
//@@te
