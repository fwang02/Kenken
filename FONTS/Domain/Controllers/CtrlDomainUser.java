package Domain.Controllers;

import Domain.PlayerScore;
import Domain.User;
import Persistence.CtrlUserFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CtrlDomainUser {
    private final CtrlUserFile ctrlUserFile;
    private final HashMap<String, User> users;
    private final PriorityQueue<PlayerScore> ranking;
    private String loggedUser;

    public CtrlDomainUser() {
        ctrlUserFile = CtrlUserFile.getInstance();
        users = new HashMap<>();
        ranking = new PriorityQueue<>(Comparator.comparingInt(PlayerScore::getMaxScore).reversed());
        loggedUser = null;
        loadUserData();
    }

    private void loadUserData() {
        ArrayList<String[]> usersList = ctrlUserFile.allUsers();
        for(String[] user : usersList) {
            User u = new User(user[0],user[1],Integer.parseInt(user[2]));
            users.put(user[0],u);
            ranking.add(new PlayerScore(user[0],Integer.parseInt(user[2])));
        }
    }

    private void writeToFile(String username) {

    }

    public boolean addUser(String username, String password) {
        users.put(username,new User(username,password));
        ranking.add(new PlayerScore(username,0));
        return ctrlUserFile.writeNewUserToFile(username,password);
    }

    public boolean isPasswordCorrect(String username, String password) {
        User u = users.get(username);
        if(u == null) {
            return false;
        }
        return Objects.equals(u.getPassword(), password);
    }

    /**
     *
     * @param username Nombre usuario
     */
    public void loginUser(String username) {
        loggedUser = username;
    }

    public boolean isUserExist(String username) {
        return users.containsKey(username);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public HashMap<String,User> getAllUsers() {
        return users;
    }

    //ranking methods
    public PriorityQueue<PlayerScore> getRanking() {
        return ranking;
    }

    public boolean updateMaxPointCurrUser(int newMaxPoint) {
        if(users.get(loggedUser).getMaxPoint() > newMaxPoint) {
            return false;
        }
        ranking.removeIf(playerScore -> playerScore.getName().equals(loggedUser));
        ranking.add(new PlayerScore(loggedUser,newMaxPoint));

        users.get(loggedUser).setMaxPoint(newMaxPoint);

        return updateAllUsers();
    }

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

    public boolean deleteUser(String username) {
        if (!users.containsKey(username)) {
            return false;
        }

        // Remove user from memory
        users.remove(username);
        ranking.removeIf(playerScore -> playerScore.getName().equals(username));

        String userFilePath = CtrlUserFile.getFilePath();
        // Remove user from file
        try {
            Path path = Paths.get(userFilePath);
            List<String> lines = Files.readAllLines(path);
            Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.startsWith(username + ";")) {
                    iterator.remove();
                    break;
                }
            }
            Files.write(path, lines);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void logoutCurrentUser() {
        loggedUser = null;
    }
}
