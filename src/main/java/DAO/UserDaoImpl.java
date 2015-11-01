package DAO;

import Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDaoImpl implements UserDAO {

    private SessionFactory sessionFactory;
    private Session session;

    public UserDaoImpl(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session= sessionFactory.openSession();
    }

    public void addUser(User user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public List<User> getAllUsers() {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        List<User> userList =  session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return userList;

    }

    public void deleteUser(Integer id_user) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query = session.createQuery("from User where id_user=:id_user");
        query.setParameter("id_user", id_user);
        User user = (User)query.uniqueResult();

        session.delete(user);

        session.getTransaction().commit();
        session.close();

    }

    public User getUser(String email) {
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query = session.createQuery("from User where email=:email");
        query.setParameter("email", email);
        User user = (User)query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        if(null != user){
            return user;
        } else{ return null;}
    }

    public List<User> getUserByCity(String city){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query=session.createQuery("from User where city=:city");
        query.setParameter("city", city);
        List<User> listUser = query.list();

        session.getTransaction().commit();
        session.close();

        return listUser;
    }

    public List<User> getUserByRate(double rate){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query=session.createQuery("from User where rate<=:rate");
        query.setParameter("rate", rate);
        List<User> listUser = query.list();

        session.getTransaction().commit();
        session.close();

        return listUser;
    }
    public List<User> getUserByAttributes(String name, String surname, String city, double rate, String role){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        List<User> listUser = session.createCriteria(User.class)
                .add(Restrictions.like("name", check(name)))
                .add(Restrictions.like("surname", check(surname)))
                .add(Restrictions.like("city", check(city)))
                .add(Restrictions.lt("rate", checkDouble(rate)))
                .add(Restrictions.like("role", check(role))).list();

        session.getTransaction().commit();
        session.close();
        return listUser;
    }

    public User getUserIfExist(String email, String password){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query=session.createQuery("from User where (email=:email and password=:password)");
        query.setParameter("email", email);
        query.setParameter("password", password);
        User user = (User)query.uniqueResult();

        session.getTransaction().commit();
        session.close();
        return user;

    }

    public boolean authentication(String email, String password){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();


        Query query=session.createQuery("from User where (email=:email and password=:password)");
        query.setParameter("email", email);
        query.setParameter("password", password);
        User user = (User)query.uniqueResult();

        session.getTransaction().commit();
        session.close();
        if (user==null){
            return false;
        }return true;
    }




    private double checkDouble(double rate) {
        if (rate==0){
            return Double.MAX_VALUE;
        }return rate;
    }

    private String check(String name) {
        if(name == null){
            return "%";
        }return name + "%";
    }

}
