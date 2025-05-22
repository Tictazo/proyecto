public class Admin {
    private String correo;
    private String passwordHash;

    public Admin(String correo, String passwordHash) {
        this.correo = correo;
        this.passwordHash = passwordHash;
    }

    public String getCorreo() { return correo; }
    public String getPasswordHash() { return passwordHash; }

    @Override
    public String toString() {
        return correo + "," + passwordHash;
    }
}
