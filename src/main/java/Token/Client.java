package Token;

public class Client {
    private int id;
    private String random_id;
    private String secret_id;
    private String grant_types;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRandom_id() {
        return random_id;
    }

    public void setRandom_id(String random_id) {
        this.random_id = random_id;
    }

    public String getSecret_id() {
        return secret_id;
    }

    public void setSecret_id(String secret_id) {
        this.secret_id = secret_id;
    }

    public String getGrant_types() {
        return grant_types;
    }

    public void setGrant_types(String grant_types) {
        this.grant_types = grant_types;
    }
}
