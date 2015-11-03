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

    public TokenManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session = sessionFactory.openSession();
    }


    public int getUserIdByToken(String token){
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id_user FROM RefreshToken WHERE token =:token");
        query.setParameter("token", token);

        int id = (int) query.uniqueResult();

        session.close();

        return id;

    }

    public void refreshTime(AccessToken token) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        long timeToAdd = 3600000 * 6; // 1 Ora per 6 = 6 Ore
        long future = new Date().getTime() + timeToAdd;
        token.setExpair_app(new Timestamp(future));
        session.save(token);

        session.getTransaction().commit();
        session.close();
    }

    public boolean isATokenActive(String token) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query = session.createQuery(" FROM AccessToken WHERE token =:token");
        query.setParameter("token", token);

        AccessToken accessToken = (AccessToken) query.uniqueResult();
        Date dateTime = accessToken.getExpair_app();
        session.close();
        return dateTime.after(new Date());

    }

    public boolean isRTokenActive(String token) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();

        Query query = session.createQuery(" FROM RefreshToken WHERE token =:token");
        query.setParameter("token", token);

        RefreshToken refreshToken = (RefreshToken) query.uniqueResult();
        Date dateTime = refreshToken.getExpair_app();
        session.close();
        return dateTime.after(new Date());

    }


    public RefreshToken createRefreshToken(int id_user, int id_client) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        SecureRandom secureRandom = new SecureRandom();
        String randomToken = new BigInteger(130, secureRandom).toString(32);
        long future = new Date().getTime() + 3600000 * 24*30; // 1 Mese
        RefreshToken token = new RefreshToken(id_client, id_user, new Timestamp(future), randomToken);
        session.save(token);

        session.getTransaction().commit();
        session.close();
        return token;
    }

    public AccessToken createAccessToken(int id_user, int id_client) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        session.getTransaction().begin();
        SecureRandom secureRandom = new SecureRandom();
        String randomToken = new BigInteger(130, secureRandom).toString(32);
        long future = new Date().getTime() + 3600000 * 6; // 6 Ore
        AccessToken token = new AccessToken(id_client, id_user, new Timestamp(future), randomToken);
        session.save(token);

        session.getTransaction().commit();
        session.close();
        return token;
    }


}
