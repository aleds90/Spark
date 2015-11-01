package DAO;

import java.util.List;

public interface UserManager {

    public void addUser(User user);
    public List<User> getAllUsers();
    public void  deleteUser(Integer id_user);
    public User getUser(String email);
    public List<User> getUserByCity(String city);
    public List<User> getUserByRate(double rate);
    public List<User> getUserByAttributes(String nome, String surname, String city, double rate, String role);
    public User getUserIfExist(String email, String password);
    public boolean authentication(String email, String password);


}
