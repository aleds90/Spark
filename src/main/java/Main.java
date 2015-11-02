import DAO.UserDaoImpl;
import DAO.UserManagerImpl;
import Spark.LoginController;
import Spark.UserController;
import Token.ClientDAOImpl;
import Token.TokenManager;

public class Main {
    public static void main(String[] args){

        new UserController(new UserManagerImpl(new UserDaoImpl()), new TokenManager());
        new LoginController(new UserManagerImpl(new UserDaoImpl()), new ClientDAOImpl(), new TokenManager());
    }
}
