package Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class CtrlDomainUser {
    private static final CtrlDomainUser CTRL_USER = new CtrlDomainUser();
    private final HashMap<String,User> users;
    private static final String filePath = "./DATA/users.txt";


    private CtrlDomainUser() {
        users = new HashMap<>();
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
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no existe：" + filePath+", los usuarios no están cargados en la memoria");
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
            throw new RuntimeException(e);
        }
    }

    public static CtrlDomainUser getInstance() {
        return CTRL_USER;
    }

    public void addUser(String username, String password) {
        users.put(username,new User(username,password));
        writeToFile(username);
    }

    public boolean checkPassword(String username, String password) {
        User u = users.get(username);
        if(u == null) {
            return false;
        }
        return Objects.equals(u.getPassword(), password);
    }


    public boolean isUserExist(String username) {
        return users.containsKey(username);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public HashMap<String,User> getAllUsers() {
        return users;
    }

    /*public void printUsers() {
        System.out.println("Username  Password  MaxPoint");
        for(Map.Entry<String,User> set: users.entrySet()) {
            System.out.println(set.getKey()+" "+set.getValue().getPassword()+" "+set.getValue().getMaxPoint());
        }
    }

     */


}
