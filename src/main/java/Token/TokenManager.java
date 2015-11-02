package Token;

import Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;


public class TokenManager {
    private SessionFactory sessionFactory;
    private Session session;

    public TokenManager(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session= sessionFactory.openSession();}


    public boolean isTokenAtive(String token){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT expair_app FROM RefreshToken WHERE token =:token");
        query.setParameter("token", token);

        Date dateTime = (Date)query.uniqueResult();

        session.close();
        return dateTime.after(new Date());

    }

    public RefreshToken createRefreshToken(int id_user, int id_client){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        SecureRandom secureRandom = new SecureRandom();
        String randomToken= new BigInteger(130, secureRandom).toString(32);
        long future = new Date().getTime() + 100000;
        RefreshToken token = new RefreshToken(id_client, id_user, new Timestamp(future), randomToken);
        session.save(token);

        session.getTransaction().commit();
        session.close();
        return token;
    }

    public AccessToken createAccessToken(int id_user, int id_client){
        if(!session.isOpen()){
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        SecureRandom secureRandom = new SecureRandom();
        String randomToken= new BigInteger(130, secureRandom).toString(32);
        long future = new Date().getTime() + 200000;
        AccessToken token = new AccessToken(id_client, id_user, new Timestamp(future), randomToken);
        session.save(token);

        session.getTransaction().commit();
        session.close();
        return token;
    }







}
