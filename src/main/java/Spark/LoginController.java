package Spark;

import DAO.User;
import DAO.UserManagerImpl;
import Token.*;

import static spark.Spark.halt;
import static spark.Spark.post;

public class LoginController {

    public LoginController(final UserManagerImpl userManager, final ClientDAOImpl clientDAO, final TokenManager tokenManager) {

        post("/authorization", (request, response) -> {
            String token = request.headers("Authorization");
            Client client = clientDAO.ClientAuth(request.queryParams("random_id"), request.queryParams("secret_id"), request.queryParams("grant_types"));
            if(request.queryParams("grant_types").equals("Password")){
                User user = userManager.getUserIfExist(request.queryParams("email"), request.queryParams("password"));

                if (user != null && client != null) {
                    AccessToken accessToken = tokenManager.createAccessToken(user.getId_user(), client.getId());
                    RefreshToken refreshToken = tokenManager.createRefreshToken(user.getId_user(), client.getId());

                    return " Password" + accessToken.getToken() + " ; " + refreshToken.getToken();
                }
                return "NO OK";
            }
            else{
                if(!tokenManager.isRTokenActive(token)){
                    halt(401,"401");// di al Client di rifare il Login! ovvero andare su /autho... con Type Password
                }
                else{
                    AccessToken accessToken = tokenManager.createAccessToken(tokenManager.getUserIdByToken(token), client.getId());
                    RefreshToken refreshToken = tokenManager.createRefreshToken(tokenManager.getUserIdByToken(token), client.getId());
                    return accessToken.getToken() + " ; " + refreshToken.getToken();
                }
                return "";
            }
        });
    }
}
