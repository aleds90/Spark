package Token;


public class RefreshToken implements Token {
    private int token_id;
    //chiave esterna client
    private int id_client;
    //chiave esterna user
    private int id_user;
    private int expair_app;
    private String token;

    public RefreshToken(int id_client, int id_user, int expair_app, String token) {
        this.id_client = id_client;
        this.id_user = id_user;
        this.expair_app = expair_app;
        this.token = token;
    }

    public RefreshToken(){}

    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getExpair_app() {
        return expair_app;
    }

    public void setExpair_app(int expair_app) {
        this.expair_app = expair_app;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
