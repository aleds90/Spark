package DAO;

import java.util.List;

public class UserManagerImpl implements UserManager {

    private UserDaoImpl userDAO;

    public  UserManagerImpl(UserDaoImpl userDao){
        this.userDAO = userDao;
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void deleteUser(Integer id_user) {
        userDAO.deleteUser(id_user);
    }

    public List<User> getUserByCity(String city){return userDAO.getUserByCity(city);}

    public User getUser(String email) {
        return  userDAO.getUser(email);
    }

    public List<User> getUserByRate(double rate){return  userDAO.getUserByRate(rate);}
    public List<User> getUserByAttributes(String nome, String surname, String city, double rate, String role){return userDAO.getUserByAttributes(nome, surname, city, rate, role);};
    public User getUserIfExist(String email, String password){return  userDAO.getUserIfExist(email, password);}
    public boolean authentication(String email, String password){return userDAO.authentication(email,password);}

}
