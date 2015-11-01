package Spark;


import DAO.UserManagerImpl;
import Token.ClientDAOImpl;
import Token.TokenManager;


import static spark.Spark.post;

public class LoginController {

    public LoginController(final UserManagerImpl userManager, final ClientDAOImpl clientDAO, final TokenManager tokenManager){

        post("/authorization", (request, response) -> {

            boolean resultAuthorization = userManager.authentication(request.queryParams("email"), request.queryParams("password"));
            boolean resultClient =  clientDAO.ClientAuth(request.queryParams("random_id"),request.queryParams("secret_id"),request.queryParams("grant_types"));

            if (resultAuthorization&&resultClient){
//                tokenManager.createAccessToken(,userManager.getUser(request.queryParams("email")).getId_user())

            }return "login errato error 401";
        });
    }
}
