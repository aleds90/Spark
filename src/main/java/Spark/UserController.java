package Spark;
import DAO.User;
import DAO.UserManagerImpl;
import Token.TokenManager;
import spark.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static Json.JsonUtil.json;
import static spark.Spark.*;

public class UserController {

    public UserController(final UserManagerImpl userManager, final TokenManager tokenManager) {


        before("/api/*", (request, response) -> {
            if (!tokenManager.isATokenActive(request.headers("Authorization"))) {
                halt(403,"403");// dí al Client di andare su /authorization perö con Grand_Type Refresh(per aggiornate i Token)
            }
        });

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


        post("/api/getFiltered", (request, response) -> {
            List<User> list = userManager.getUserByAttributes(request.queryParams("name"), request.queryParams("surname"), request.queryParams("city"), check(request.queryParams("rate")), request.queryParams("role"));
            return list;
        }, json());



    }


    private double check(String rate) {
        if (rate == null) {
            return Double.MAX_VALUE;
        }
        return Double.parseDouble(rate);
    }
}
