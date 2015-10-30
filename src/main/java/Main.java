import DAO.UserDaoImpl;
import DAO.UserManagerImpl;
import Spark.UserController;

public class Main {
    public static void main(String[] args){

        new UserController(new UserManagerImpl(new UserDaoImpl()));
    }
}
