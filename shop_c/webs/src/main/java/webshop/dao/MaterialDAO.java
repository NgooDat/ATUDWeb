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

import webshop.entity.Material;
import webshop.security.Authentication;

@Repository
public class MaterialDAO {

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

    // Lấy tất cả chất liệu sản phẩm
    @SuppressWarnings("unchecked")
    public List<Material> getAllMaterials() {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return session.createQuery("FROM Material").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Lấy chất liệu sản phẩm theo ID
    public Material getMaterialById(int id) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return (Material) session.get(Material.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Thêm mới chất liệu sản phẩm
    public boolean addMaterial(Material material) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            session.save(material);

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

    // Cập nhật chất liệu sản phẩm
    public boolean updateMaterial(Material material) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            session.update(material);

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

    // Xóa chất liệu sản phẩm
    public boolean deleteMaterial(int id) {
        Transaction transaction = null;
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();

            Material material = (Material) session.get(Material.class, id);
            if (material != null) {
                session.delete(material);
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
