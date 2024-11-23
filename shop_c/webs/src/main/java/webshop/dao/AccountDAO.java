package webshop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import webshop.entity.Account;

import java.util.Optional;

@Repository
public class AccountDAO {
    @Autowired
    private SessionFactory sessionFactory;

    // Create
    public boolean saveAccount(Account account) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();  // Đóng session thủ công
            }
        }
    }

    // Read
    public Account getAccountById(int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return (Account) session.get(Account.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();  
            }
        }
    }

    // Update
    public boolean updateAccount(Account account) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(account);
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

    // Delete
    public boolean deleteAccount(int id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Account account = (Account) session.get(Account.class, id);
            if (account != null) {
                session.delete(account);
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
