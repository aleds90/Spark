package Token;


import Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ClientDAOImpl {
    private SessionFactory sessionFactory;
    private Session session;
    public ClientDAOImpl(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session= sessionFactory.openSession();
    }

    public Client ClientAuth(String random_id, String secret_id, String grand_types){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        Query query=session.createQuery("from Client where (random_id=:random_id and secret_id=:secret_id and grant_types=:grand_types)");
        query.setParameter("random_id", random_id);
        query.setParameter("secret_id", secret_id);
        query.setParameter("grand_types", grand_types);
        Client client = (Client)query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        if (client==null){return null;}
        return client;
    }
}
