package Domain;

/**
 * Esta clase representa a un usuario en el videojuego.
 * Contiene información sobre el nombre de usuario, la contraseña y la puntuación máxima del usuario.
 *
 * @author Feiyang Wang
 */
public class User {
    private String username;
    private String password;
    private int maxPoint;

    /**
     * Constructor que inicializa el nombre de usuario y la contraseña. La puntuación máxima se inicializa a 0.
     *
     * @param username El nombre de usuario del usuario.
     * @param password La contraseña del usuario.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        maxPoint = 0;
    }

    /**
     * Constructor que inicializa el nombre de usuario, la contraseña y la puntuación máxima.
     *
     * @param username El nombre de usuario del usuario.
     * @param password La contraseña del usuario.
     * @param maxPoint La puntuación máxima del usuario.
     */
    public User(String username, String password, int maxPoint) {
        this.username = username;
        this.password = password;
        this.maxPoint = maxPoint;
    }

    /**
     * Obtiene el nombre de usuario del usuario.
     *
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene la puntuación máxima del usuario.
     *
     * @return La puntuación máxima del usuario.
     */
    public int getMaxPoint() {
        return maxPoint;
    }

    /**
     * Establece la puntuación máxima del usuario.
     *
     * @param point La nueva puntuación máxima del usuario.
     */
    public void setMaxPoint(int point) {
        maxPoint = point;
    }
}
