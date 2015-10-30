package Spark;

import DAO.User;
import DAO.UserManagerImpl;
import spark.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static Json.JsonUtil.json;
import static spark.Spark.*;

public class UserController {
    private  List<Session> listSessions;

    public UserController(final UserManagerImpl userManager){

        listSessions = new ArrayList<Session>();

        get("/users", (request, response) -> userManager.getAllUsers(), json());
        after((req, res) -> res.type("application/json"));



        post("/add", (request, response) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date bday = formatter.parse(request.queryParams("bday"));

            double rate = Double.parseDouble(request.queryParams("rate"));

            User user = new User(request.queryParams("name"), request.queryParams("surname"), request.queryParams("email"), request.queryParams("password"), bday, request.queryParams("role"), request.queryParams("city"), rate);
            userManager.addUser(user);
            return request.queryParams("name") + " e' stato inserito";
        });

        get("/add", ((request, response) -> "Empty"));

        post("/getuser", ((request, response) -> {

            User user = userManager.getUser(request.queryParams("email"));
            if (user != null) {
                return user;
            } else {
                return "No user have this email";
            }
        }), json());

        post("/delete", ((request, response) -> {
            userManager.deleteUser(Integer.parseInt(request.queryParams(("id_user"))));
            return "";
        }));

        post("/getByCity",((request, response) -> {
            List<User> list = userManager.getUserByCity(request.queryParams("city"));

            return list;

        }), json());

        post("/getByRate",((request, response) -> {
            List<User> list = userManager.getUserByRate(Double.parseDouble(request.queryParams("rate")));

            return list;

        }), json());

        post("/getFiltered", (request, response) -> {
            List<User> list = userManager.getUserByAttributes(request.queryParams("name"),request.queryParams("surname"), request.queryParams("city"), check(request.queryParams("rate")), request.queryParams("role"));
            return list;
        },json());

        post("/checkLogin",((request, response) -> {
            User user = userManager.getUserIfExist(request.queryParams("email"), request.queryParams("password"));

            if (user != null){
                if (checkSession(user.getEmail())){
                    return "aldery in a session";
                }else{
                    Session session = createSession(user, request.session(true));
                    listSessions.add(session);
                    return "login effettuato";
                }
            }return "email o password errate";
        }),json());

    }

    private Session createSession(User user, Session newSession) {
        Session session = newSession;
        session.attribute("email", user.getEmail());
        return session;
    }

    private double check(String rate) {
        if (rate==null){
            return Double.MAX_VALUE;
        }
        return Double.parseDouble(rate);
    }

    private boolean checkSession(String email){

        for (int i = 0; i<listSessions.size();i++){
            System.out.println(listSessions.get(i).attribute("email").toString());
            if ((listSessions.get(i).attribute("email")).toString().equals(email)){
                return true;
            }
        }return false;
    }
}
