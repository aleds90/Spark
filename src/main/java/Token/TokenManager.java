package Token;

import Hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigInteger;
import java.security.SecureRandom;

public class TokenManager {
    private SessionFactory sessionFactory;
    private Session session;

    public TokenManager(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.session= sessionFactory.openSession();}

        public RefreshToken createRefreshToken(int id_user, int id_client){
            if(!session.isOpen()){
                session = sessionFactory.openSession();
            }
            session.getTransaction().begin();
            SecureRandom secureRandom = new SecureRandom();
            String randomToken= new BigInteger(130, secureRandom).toString(32);
            RefreshToken token = new RefreshToken(id_client, id_user, 20000, randomToken);
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
        AccessToken token = new AccessToken(id_client, id_user, 15000, randomToken);
        session.save(token);

        session.getTransaction().commit();
        session.close();
        return token;
    }







}
