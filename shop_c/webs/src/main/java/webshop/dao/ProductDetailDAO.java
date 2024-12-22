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

import webshop.entity.ProductDetail;
import webshop.security.Authentication;

@Repository
public class ProductDetailDAO {

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

    // Lấy tất cả chi tiết sản phẩm
    @SuppressWarnings("unchecked")
	public List<ProductDetail> getAllProductDetails() {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return session.createQuery("FROM ProductDetail").list();
        } catch (Exception e) {
            logError("Error fetching all product details", e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    // Lấy chi tiết sản phẩm theo ID
    public ProductDetail getProductDetailById(int id) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            return (ProductDetail) session.get(ProductDetail.class, id);
        } catch (Exception e) {
            logError("Error fetching product detail by ID: " + id, e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }
    


    // Thêm mới chi tiết sản phẩm
    public boolean addProductDetail(ProductDetail productDetail) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            session.save(productDetail);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error adding product detail", e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Cập nhật chi tiết sản phẩm
    public boolean updateProductDetail(ProductDetail productDetail) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            session.update(productDetail);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error updating product detail", e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Xóa chi tiết sản phẩm
    public boolean deleteProductDetail(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            transaction = session.beginTransaction();
            ProductDetail productDetail = (ProductDetail) session.get(ProductDetail.class, id);
            if (productDetail != null) {
                session.delete(productDetail);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logError("Error deleting product detail with ID: " + id, e);
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    // Lấy danh sách chi tiết sản phẩm theo ProductID
    @SuppressWarnings("unchecked")
	public List<ProductDetail> getProductDetailsByProductId(int productId) {
        Session session = null;
        try {
        	int role = Authentication.getRole();
            session = getSessionFactoryBasedOnRole(role).openSession();
            String hql = "FROM ProductDetail pd WHERE pd.product.id = :productId";
            return session.createQuery(hql)
                    .setParameter("productId", productId)
                    .list();
        } catch (Exception e) {
            logError("Error fetching product details by ProductID: " + productId, e);
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    
    private void logError(String message, Exception e) {
        // Replace this with your preferred logging framework (e.g., SLF4J, Log4j)
        System.err.println(message);
        e.printStackTrace();
    }
}
//@@te
