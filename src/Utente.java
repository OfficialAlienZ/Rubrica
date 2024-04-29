public class Utente {
    private String username;
    private String password;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean verificaCredenziali(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
