package Domain;

public class Token {
    private int id, token_type;
    private long expiration_date;
    private String username, token_value;

    public Token() {
    }

    public Token(int id, String username, String token_value, int token_type, int expiration_date) {
        this.id = id;
        this.username = username;
        this.token_value = token_value;
        this.token_type = token_type;
        this.expiration_date = expiration_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToken_type() {
        return token_type;
    }

    public void setToken_type(int token_type) {
        this.token_type = token_type;
    }

    public long getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(long expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTokenValue() {
        return token_value;
    }

    public void setTokenValue(String token_value) {
        this.token_value = token_value;
    }
}
