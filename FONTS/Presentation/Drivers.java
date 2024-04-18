package Presentation;

import Domain.CtrlDomain;
import Domain.CtrlDomainUser;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Drivers {
    private static CtrlDomain CD;
    private static Scanner sc;
    private static boolean firstPage;
    private static boolean gameCreationPage;
    private static final CtrlDomainUser CDU = CtrlDomainUser.getInstance();


    Drivers() {
        CD = new CtrlDomain();
        sc = new Scanner(System.in);
        firstPage = true;
        gameCreationPage = false;

    }

    private static void loginUser() {
        String username;
        String password;

        System.out.println("Escribe el username:");
        username = sc.nextLine();
        System.out.println("Escribe la contraseña:");
        password = sc.nextLine();

        boolean logged = CD.loginUser(username,password);
        if(!logged) System.out.println("El username o la contraseña es incorrecto");
        else {
            System.out.println("Welcome back! " + username);
            firstPage = false;
        }
    }

    private static void registerUser() {
        String username;
        String password;
        System.out.println("Escribe un username nuevo:");
        username = sc.nextLine();
        System.out.println("Configura la contraseña:");
        password = sc.nextLine();

        boolean registered =CD.registerUser(username,password);
        if(registered) {
            System.out.println("El usuario: "+username+" está registrado!");
        }
        else {
            System.out.println("El usuario ya existe");
        }
    }

    public static void main(String[] args) {
        String op;
        while (firstPage) {
            System.out.print('\n');
            System.out.println("Bienvenido a KenKen!");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar");
            System.out.println("3. Ranking");
            System.out.print('\n');
            op = sc.nextLine();

            switch (op) {
                case "1":
                    loginUser();
                    break;
                case "2":
                    registerUser();
                    break;
                case "3":
                    TreeMap<Integer,String> rk = CD.showRanking();
                    if(rk.isEmpty()) {
                        System.out.println("No hay usuario, el ranking está vacío");
                        break;
                    }
                    int count = 1;
                    System.out.println("Count  Username  MaxPoint");
                    for(Map.Entry<Integer,String> set : rk.entrySet()) {
                        System.out.println(count+". "+set.getValue()+": "+set.getKey());
                        count++;
                    }
                    break;
                default:
                    System.out.println("Comando incorrecto");

            }
        }

        while(gameCreationPage) {
            System.out.print('\n');
            System.out.println("1. Crear nuevo");
            System.out.println("2. Importar desde fichero");
            System.out.println("3. Seguir jugar");
            System.out.println("4. Ranking");
            System.out.print('\n');
            op = sc.nextLine();

            switch (op) {
                case "1":
                    System.out.println("Modo de creación:");
                    System.out.println("1.Generado por dificultad");
                    System.out.println("2.Generado por poner número de regiones");

                    String opCreation = sc.nextLine();
                    if(opCreation.equals("1")) {
                        generateByDifficulty();
                    } else if (opCreation.equals("2")) {
                        generateByNumCages();
                    } else {

                    }

                    break;
                case "2":
                    System.out.println("ss");
                    break;
                case "3":
                    System.out.println("dsdasd");
                    break;
                case "4":
                    TreeMap<Integer,String> rk = CD.showRanking();
                    if(rk.isEmpty()) {
                        System.out.println("No hay usuario, el ranking está vacío");
                        break;
                    }
                    int count = 1;
                    System.out.println("Count  Username  MaxPoint");
                    for(Map.Entry<Integer,String> set : rk.entrySet()) {
                        System.out.println(count+". "+set.getValue()+": "+set.getKey());
                        count++;
                    }
                    break;

            }


        }


    }

    private static void generateByNumCages() {
    }

    private static void generateByDifficulty() {
        int size = sc.nextInt();

    }
}
