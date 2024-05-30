package Persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Esta clase es responsable de la persistencia de los datos del usuario en un archivo.
 * Proporciona métodos para leer y escribir datos de usuario en un archivo.
 * Esta clase es un singleton, lo que significa que solo puede haber una instancia de esta clase en el programa.
 * Utiliza el patrón de diseño Singleton para asegurar esto.
 *
 * @author Feiyang Wang
 */
public class CtrlUserFile {
    private static final CtrlUserFile CTRL_USER_FILE = new CtrlUserFile();
    private static final String filePath = "../DATA/users.txt";

    private CtrlUserFile() {
    }

    /**
     * Método para obtener la instancia de esta clase.
     *
     * @return La única instancia de esta clase.
     */
    public static CtrlUserFile getInstance() {
        return CTRL_USER_FILE;
    }

    /**
     * Lee todos los usuarios del archivo y los devuelve en una lista.
     *
     * @return Una lista de arrays de strings, donde cada array contiene los datos de un usuario.
     */
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

    /**
     * Escribe los datos de un nuevo usuario en la última línea el archivo.
     *
     * @param username El nombre de usuario del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @return Verdadero si la operación fue exitosa, falso en caso contrario.
     */
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

    /**
     * Actualiza los datos de todos los usuarios en el archivo.
     *
     * @param usersData Una lista de listas de strings, donde cada lista contiene los datos de un usuario.
     * @return Verdadero si la operación fue exitosa, falso en caso contrario.
     */
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

    /**
     * Obtiene la ruta del archivo donde se almacenan los datos del usuario.
     *
     * @return La ruta del archivo.
     */
    public static String getFilePath() {
        return filePath;
    }
}
