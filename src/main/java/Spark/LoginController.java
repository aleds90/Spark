package Spark;

import DAO.User;
import DAO.UserManagerImpl;
import Token.Client;
import Token.ClientDAOImpl;
import Token.TokenManager;


import static spark.Spark.post;

public class LoginController {

    public LoginController(final UserManagerImpl userManager, final ClientDAOImpl clientDAO, final TokenManager tokenManager){

        post("/authorization", (request, response) -> {

            User user = userManager.getUserIfExist(request.queryParams("email"), request.queryParams("password"));
            Client client =  clientDAO.ClientAuth(request.queryParams("random_id"),request.queryParams("secret_id"),request.queryParams("grant_types"));

            if (user != null && client != null){
                tokenManager.createAccessToken(user.getId_user(), client.getId());
                tokenManager.createRefreshToken(user.getId_user(), client.getId());

                return "OK";
            }
            return "NO OK";
        });
    }
}
