package Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CtrlDomainUser {
    private static final CtrlDomainUser CTRL_USER = new CtrlDomainUser();
    private final HashMap<String,User> users;
    private final PriorityQueue<PlayerScore> ranking;
    private static User loggedUser;
    private static final String filePath = "../DATA/users.txt";


    private CtrlDomainUser() {
        users = new HashMap<>();
        ranking = new PriorityQueue<>(Comparator.comparingInt(PlayerScore::getMaxScore).reversed());
        loadUserData();
    }

    private void loadUserData() {
        try {
            File usersFile = new File(filePath);
            Scanner scanner = new Scanner(usersFile);

            while(scanner.hasNextLine()) {
                String user = scanner.nextLine();
                String[] userData = user.split(";");
                if(userData.length == 3) {
                    User u = new User(userData[0],userData[1],Integer.parseInt(userData[2]));
                    users.put(userData[0],u);
                    ranking.add(new PlayerScore(userData[0],Integer.parseInt(userData[2])));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no existe：" +filePath+", los usuarios no están cargados en la memoria");
        }

    }

    private void writeToFile(String username) {
        try {
            FileWriter fw = new FileWriter(filePath,true);
            String password = users.get(username).getPassword();
            int maxPoint = users.get(username).getMaxPoint();
            fw.write(username+';'+password+';'+maxPoint+'\n');
            fw.close();
        } catch (IOException e) {
            System.err.println("El usuario no se ha guardado en el fichero");
        }
    }

    public static CtrlDomainUser getInstance() {
        return CTRL_USER;
    }

    public void addUser(String username, String password) {
        if (isUserExist(username)) {
            System.out.println("El usuario ya existe");
            return;
        }
        users.put(username,new User(username,password));
        writeToFile(username);
    }

    public boolean isPasswordCorrect(String username, String password) {
        User u = users.get(username);
        if(u == null) {
            return false;
        }
        return Objects.equals(u.getPassword(), password);
    }

    public void loginUser(String username) {
        loggedUser = getUser(username);
    }

    public boolean isUserExist(String username) {
        return users.containsKey(username);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public User getLoggedUser() {return loggedUser;}

    public HashMap<String,User> getAllUsers() {
        return users;
    }

    //ranking methods
    public PriorityQueue<PlayerScore> getRanking() {
        return ranking;
    }

    public boolean updateMaxPointCurrUser(int newMaxPoint) {
        String username = loggedUser.getUsername();
        if(loggedUser.getMaxPoint() > newMaxPoint) {
            return false;
        }
        ranking.removeIf(playerScore -> playerScore.getName().equals(username));
        ranking.add(new PlayerScore(username,newMaxPoint));

        users.get(username).setMaxPoint(newMaxPoint);
        return true;
    }

    public void deleteUser(String username) {
        if (!users.containsKey(username)) {
            System.out.println("User does not exist.");
            return;
        }

        // Remove user from memory
        users.remove(username);
        ranking.removeIf(playerScore -> playerScore.getName().equals(username));

        // Remove user from file
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.startsWith(username + ";")) {
                    iterator.remove();
                    break;
                }
            }
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            System.err.println("Error deleting user from file: " + e.getMessage());
        }
    }

}
