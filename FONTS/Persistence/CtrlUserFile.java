/**
 * @author Feiyang Wang
 */
package Persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CtrlUserFile {
    private static final CtrlUserFile CTRL_USER_FILE = new CtrlUserFile();
    private static final String filePath = "./DATA/users.txt";

    private CtrlUserFile() {
    }

    public static CtrlUserFile getInstance() {
        return CTRL_USER_FILE;
    }

     public ArrayList<String[]> allUsers() {
        ArrayList<String[]> users = new ArrayList<>();
        try {
            File usersFile = new File(filePath);
            Scanner scanner = new Scanner(usersFile);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] userData = line.split(";");
                if(userData.length == 3) {
                    users.add(userData);
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return users;
    }

    public boolean writeNewUserToFile(String username, String password) {
        try {
            FileWriter fw = new FileWriter(filePath,true);
            fw.write(username+';'+password+';'+'0'+'\n');
            fw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean updateDatas(ArrayList<ArrayList<String>> usersData) {
        try {
            FileWriter fw = new FileWriter(filePath,false);
            for(ArrayList<String> userData : usersData) {
                fw.write(userData.get(0)+';'+userData.get(1)+';'+userData.get(2)+'\n');
            }
            fw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static String getFilePath() {
        return filePath;
    }
}
