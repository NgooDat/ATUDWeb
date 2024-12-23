package webshop.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;


import webshop.entity.Type;
import webshop.security.Authentication;

@Repository
public class TypeDAO {

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


    // Lấy tất cả các Type
    @SuppressWarnings("unchecked")
    public List<Type> getAllTypes() {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return session.createQuery("FROM Type").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Lấy Type theo ID
    public Type getTypeById(int id) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return (Type) session.get(Type.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Thêm mới Type
    public boolean addType(Type type) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            session.save(type);

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

    // Cập nhật Type
    public boolean updateType(Type type) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            session.update(type);

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

    // Xóa Type
    public boolean deleteType(int id) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            Type type = (Type) session.get(Type.class, id);
            if (type != null) {
                session.delete(type);
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
