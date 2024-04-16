package Domain;

import java.util.HashSet;
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.*;

//import static Domain.CtrlDomain.getOperation;

public class Main {
    private static final CtrlDomainUser udb = CtrlDomainUser.getInstance();
    private static final Ranking ranking = Ranking.getInstance();
    private static final Scanner sc = new Scanner(System.in);
    private static User currentUser;
    private static KenkenPlay currentGame;

    private static TypeDificult chooseDifficulty()
    {
        System.out.println("Selecciona una dificultad.");
        System.out.println("1. Fácil");
        System.out.println("2. Medio");
        System.out.println("3. Difícil");
        System.out.println("4. Experto");

        int num_dif = sc.nextInt();
        TypeDificult dif;

        switch(num_dif) {
            case 1:
                dif = TypeDificult.EASY;
                break;
            case 2:
                dif = TypeDificult.MEDIUM;
                break;
            case 3:
                dif = TypeDificult.HARD;
                break;
            case 4:
                dif = TypeDificult.EXPERT;
                break;
            default:
                throw new IllegalArgumentException("El valor debería ser entre 1 y 4.");
        }
        System.out.println("Dificultad: "+ dif +"\n");
        return dif;
    }

    private static int chooseNumCages()
    {
        System.out.println("Selecciona el número de regiones");
        int numCages = sc.nextInt();
        //if(numCages < 1 || numCages > 9) throw new IllegalArgumentException("El número .");
        /*else */System.out.println("El número de regiones es: "+numCages+"\n");
        return numCages;
    }

    private static int chooseNumIndividualCells()
    {
        System.out.println("Selecciona el número de casillas individuales");
        int numCages = sc.nextInt();
        //if(numCages < 1 || numCages > 9) throw new IllegalArgumentException("El número .");
        /*else */System.out.println("El número de regiones es: "+numCages+"\n");
        return numCages;
    }

    private static int chooseSize()
    {
        System.out.println("Selecciona el tamaño del tablero (3-9).");
        int size = sc.nextInt();
        if(size < 3 || size > 9) throw new IllegalArgumentException("El tamaño debería ser 3-9.");
        else System.out.println("El tamaño es: "+size+"\n");
        return size;
    }

    static TypeOperation getOperation(int num)
    {
        switch (num) {
            case 1:
                return TypeOperation.ADD;
            case 2:
                return TypeOperation.SUB;
            case 3:
                return TypeOperation.MULT;
            case 4:
                return TypeOperation.DIV;
            case 5:
                return TypeOperation.MOD;
            case 6:
                return TypeOperation.POW;
            default:
                throw new IllegalArgumentException("Carácter no válido encontrado en la cadena: " + num);
        }
    }

    private static HashSet<TypeOperation> chooseOps()
    {
        System.out.println("Selecciona las operaciones.");
        System.out.println("+-----------+------------+");
        System.out.println("| 1. ADD  + | 2. SUB   - |");
        System.out.println("| 3. MULT * | 4. DIV   : |");
        System.out.println("| 5. MOD  % | 6. POW   ^ |");
        System.out.println("+-----------+------------+");
        System.out.println("Poner los números consecutivamente.");

        HashSet<TypeOperation> operations = new HashSet<>();
        sc.nextLine();
        String op = sc.nextLine();

        for (int i = 0; i < op.length(); ++i) {
            int num = Character.getNumericValue(op.charAt(i)); // Obtener el carácter en la posición i
            operations.add(getOperation(num));
        }
        if(operations.isEmpty()) throw new NullPointerException("La lista de operaciones no puede ser nula.");

        System.out.println("Has seleccionado las siguientes operaciones:");
        for(TypeOperation operation : operations) {
            System.out.println(operation);
        }
        System.out.print('\n');
        return operations;
    }



    private static boolean login() {
        String username;
        String password;

        System.out.println("Escribe el username:");
        username = sc.nextLine();
        System.out.println("Escribe la contraseña:");
        password = sc.nextLine();
        //currentUser = udb.checkPassword(username,password);
        if(currentUser == null) return false;

        System.out.println("Welcome back! "+currentUser.getUsername());
        return true;
    }

    private static boolean register() {
        String username;
        String password;
        System.out.println("Escribe un username nuevo:");
        username = sc.nextLine();

        if(udb.isUserExist(username)) {
            System.out.println("El username ya existe");
            return false;
        }

        System.out.println("Configura la contraseña:");
        password = sc.nextLine();
        udb.addUser(username,password);
        System.out.println("El usuario: "+username+" está registrado!");
        return true;
    }

    private static boolean firstOptions() {
        System.out.print('\n');
        System.out.println("Bienvenido a KenKen!");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrar");
        System.out.println("3. domain.Ranking");
        System.out.print('\n');

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                return login();
            case 2:
                return register();
            case 3:
                ranking.showRanking();
                return false;
        }
        return false;
    }

    private static boolean gamePlayOption() {
        System.out.print('\n');
        System.out.println("1. Crear nuevo");
        System.out.println("2. Importar desde fichero");
        System.out.println("3. Seguir jugar");
        System.out.println("4. domain.Ranking");
        System.out.print('\n');

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                return createNewGame();
            case 2:
                return importFile();
            case 4:
                ranking.showRanking();
                return false;
        }
        return false;
    }

    private static boolean importFile() {
        System.out.println("Escribe el nombre del fichero (sin poner ruta).");
        String fileName = sc.nextLine();

        /*try {
            readFile(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("El fichero no existe.");
            return false;
        }

         */
        return true;

    }

    
    private static boolean createNewGame() {
        int size = chooseSize();
        TypeDificult dif = chooseDifficulty();
        HashSet<TypeOperation> operations = chooseOps();

        Kenken kenken = new Kenken(size,operations,dif);
        KenkenConfig kenkenConfig = new KenkenConfig(kenken);

        kenkenConfig.generateKenkenv1();

        
        return true;
    }


    public static void main(String[] args) throws FileNotFoundException {
        
        /*
        //for test
        udb.printUsers();

        boolean firstPage = false;
        while(!firstPage) {
            firstPage = firstOptions();
        }
        boolean gameCreationPage = false;
        while (! gameCreationPage) {
            gameCreationPage = gamePlayOption();
        }
        boolean gamePlayPage = false;
        */

        
        int size = chooseSize();
        int nCages = chooseNumCages();
        int niCells = chooseNumIndividualCells();
        TypeDificult dif = chooseDifficulty();
        HashSet<TypeOperation> operations = chooseOps();


        // domain.Kenken generado por dificultad
        Kenken kenken = new Kenken(size, operations, dif);

        // domain.Kenken generado por numero de regiones
        //Kenken kenken = new Kenken(size, operations, niCells,nCages);


        KenkenConfig kenkenConfig = new KenkenConfig(kenken);

        kenkenConfig.generateKenkenv1();


        KenkenPlay kenkenPlay = new KenkenPlay(kenken);
        kenkenPlay.start();
        

    }

}