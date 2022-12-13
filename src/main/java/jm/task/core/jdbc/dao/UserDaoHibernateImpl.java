package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction;
    private SessionFactory sessionFactory = Util.getSessionFactory();


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            NativeQuery query = session
                    .createNativeQuery("CREATE TABLE IF NOT EXISTS user " +
                            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                            "age TINYINT NOT NULL)");
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery("DROP TABLE IF EXISTS user");
            query.executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных!");
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        }catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> employees = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            employees = session.createQuery("from User")
                    .getResultList();
            transaction.commit();
        }catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
        }
        return employees;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
        }
    }
}
