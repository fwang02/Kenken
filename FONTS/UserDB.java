import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class UserDB {
    private static final UserDB userDB = new UserDB();
    private final HashMap<String,User> users;
    private static final String filePath = "./DATA/users.txt";


    private UserDB()
    {
        users = new HashMap<>();
        loadUserData();
    }

    public static UserDB getInstance() {
        return userDB;
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
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no existe：" + filePath);
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
            throw new RuntimeException(e);
        }
    }

    public void addUser(String username, String password) {
        users.put(username,new User(username,password));
        writeToFile(username);
    }

    public User loginUser(String username, String password) {
        User u = users.get(username);
        if(u == null) {
            System.out.println("El usuario no existe");
            return null;
        }
        if(! Objects.equals(u.getPassword(), password)) {
            System.out.println("La contraseña es incorrecta");
            return null;
        }
        return u;
    }

    public boolean isUserExist(String username) {
        return users.containsKey(username);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void printUsers() {
        System.out.println("Username  Password  MaxPoint");
        for(Map.Entry<String,User> set: users.entrySet()) {
            System.out.println(set.getKey()+" "+set.getValue().getPassword()+" "+set.getValue().getMaxPoint());
        }
    }
}
