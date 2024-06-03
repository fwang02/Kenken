package Domain.Controllers;

import Domain.PlayerScore;
import Domain.User;
import Persistence.CtrlUserFile;

import java.util.*;

/**
 * Esta clase es el controlador de dominio para los usuarios.
 * Gestiona las operaciones relacionadas con los usuarios, como la autenticación y la gestión de puntuaciones.
 *
 * @author Feiyang Wang
 */
public class CtrlDomainUser {
    private final CtrlUserFile ctrlUserFile;
    private final HashMap<String, User> users;
    private final PriorityQueue<PlayerScore> ranking;
    private String loggedUser;

    /**
     * Constructor de CtrlDomainUser. Inicializa el controlador de persistencia, el mapa de usuarios, el ranking y carga los datos de los usuarios.
     */
    public CtrlDomainUser() {
        ctrlUserFile = CtrlUserFile.getInstance();
        users = new HashMap<>();
        ranking = new PriorityQueue<>(Comparator.comparingInt(PlayerScore::getMaxScore).reversed());
        loggedUser = null;
        loadUserData();
    }

    /**
     * Carga los datos de los usuarios desde la persistencia.
     */
    private void loadUserData() {
        ArrayList<String[]> usersList = ctrlUserFile.allUsers();
        for(String[] user : usersList) {
            User u = new User(user[0],user[1],Integer.parseInt(user[2]));
            users.put(user[0],u);
            ranking.add(new PlayerScore(user[0],Integer.parseInt(user[2])));
        }
    }

    /**
     * Añade un nuevo usuario.
     *
     * @param username El nombre de usuario del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @return true si el usuario se añadió con éxito, false en caso contrario.
     */
    public boolean addUser(String username, String password) {
        users.put(username,new User(username,password));
        ranking.add(new PlayerScore(username,0));
        return ctrlUserFile.writeNewUserToFile(username, password);
    }

    /**
     * Comprueba si la contraseña proporcionada es correcta para el nombre de usuario proporcionado.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña a comprobar.
     * @return true si la contraseña es correcta, false en caso contrario.
     */
    public boolean isPasswordCorrect(String username, String password) {
        User u = users.get(username);
        if(u == null) {
            return false;
        }
        return Objects.equals(u.getPassword(), password);
    }

    /**
     * Inicia sesión de un usuario.
     *
     * @param username El nombre de usuario que inicia sesión.
     */
    public void loginUser(String username) {
        loggedUser = username;
    }

    /**
     * Comprueba si un usuario existe.
     *
     * @param username El nombre de usuario a comprobar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean isUserExist(String username) {
        return users.containsKey(username);
    }

    /**
     * Obtiene el nombre de usuario del usuario que ha iniciado sesión.
     *
     * @return El nombre de usuario del usuario que ha iniciado sesión.
     */
    public String getLoggedUser() {
        return loggedUser;
    }

    /**
     * Obtiene el ranking de los usuarios.
     *
     * @return Una cola de prioridad con las puntuaciones máximas de los usuarios.
     */
    public PriorityQueue<PlayerScore> getRanking() {
        return ranking;
    }

    /**
     * Actualiza la puntuación máxima del usuario que ha iniciado sesión.
     *
     * @param newMaxPoint La nueva puntuación máxima.
     * @return true si la puntuación se actualizó con éxito, false en caso contrario.
     */
    public boolean updateMaxPointCurrUser(int newMaxPoint) {
        if(users.get(loggedUser).getMaxPoint() > newMaxPoint) {
            return false;
        }
        ranking.removeIf(playerScore -> playerScore.getName().equals(loggedUser));
        ranking.add(new PlayerScore(loggedUser,newMaxPoint));

        users.get(loggedUser).setMaxPoint(newMaxPoint);

        return updateAllUsers();
    }

    /**
     * Actualiza los datos de todos los usuarios en la persistencia.
     *
     * @return true si los datos se actualizaron con éxito, false en caso contrario.
     */
    private boolean updateAllUsers() {
        ArrayList<String[]> allUsersData = new ArrayList<>();
        for (User user : users.values()) {
            String[] userData = new String[3];
            userData[0] = user.getUsername();
            userData[1] = user.getPassword();
            userData[2] = String.valueOf(user.getMaxPoint());
            allUsersData.add(userData);
        }
        return ctrlUserFile.updateDatas(allUsersData);
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logoutCurrentUser() {
        loggedUser = null;
    }
}
