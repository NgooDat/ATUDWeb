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

import webshop.entity.Product;
import webshop.security.Authentication;


@Repository
public class ProductDAO {
	

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
    
  //Lấy rqeuset hiện tại
  	public static HttpServletRequest getCurrentHttpRequest() {
          RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
          if (requestAttributes == null) {
              throw new IllegalStateException("Không có request hiện tại.");
          }
          return (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
      }
    // Lấy tất cả sản phẩm
    @SuppressWarnings("unchecked")
	public List<Product> getAllProducts() {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return session.createQuery("FROM Product").list();
        } catch (Exception e) {
            logError("Error fetching all products", e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(int id) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return (Product) session.get(Product.class, id);
        } catch (Exception e) {
            logError("Error fetching product by ID: " + id, e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Thêm mới sản phẩm
    public boolean addProduct(Product product) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error adding product", e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error updating product", e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            Product product = (Product) session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error deleting product with ID: " + id, e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Tìm kiếm sản phẩm theo tên
    @SuppressWarnings("unchecked")
	public List<Product> searchProductsByName(String name) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            String hql = "FROM Product WHERE name LIKE :name";
            return session.createQuery(hql)
                    .setParameter("name", "%" + name + "%")
                    .list();
        } catch (Exception e) {
            logError("Error searching products by name: " + name, e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Phương thức log lỗi
    private void logError(String message, Exception e) {
        // Replace this with your preferred logging framework (e.g., SLF4J, Log4j)
        System.err.println(message);
        e.printStackTrace();
    }
    

  	
  	
}
//@@te