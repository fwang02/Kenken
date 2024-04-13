package Domain;

import java.util.Scanner;

public class DriverUser {
    private static User u;
    private static final Scanner sc = new Scanner(System.in);

    public static void testConstructora(){
        System.out.println("Introduce un username:");
        sc.nextLine();
        String username = sc.nextLine();


        System.out.println("Introduce una contraseña:");
        String password = sc.nextLine();

        u = new User(username,password,0);
        System.out.println("El usuario "+u+" está creado correctamente");
    }

    public static void testGetUsername() {
        String username = u.getUsername();
        if(username == null) {
            System.out.println("El usuario no está creado");
            return;
        }
        System.out.println("El nombre del usuario es: "+username);
    }

    public static void testGetPassword() {
        String password = u.getPassword();
        if(password == null) {
            System.out.println("El usuario no está creado");
            return;
        }
        System.out.println("La contraseña del usuario es: "+password);
    }

    public static void testGetMaxPoint() {
        int maxPoint = u.getMaxPoint();
        if(u == null) {
            System.out.println("El usuario no está creado");
            return;
        }
        System.out.println("El punto máx del usuario es: "+maxPoint);
    }

    public static void testSetUsername() {
        System.out.println("Introduzca un nuevo username");
        sc.nextLine();
        String newU = sc.nextLine();
        u.setUsername(newU);
        System.out.println("Se ha cambiado el username a "+newU);
    }
    public static void testSetPassWord() {
        System.out.println("Introduzca una nueva contraseña");
        sc.nextLine();
        String newP = sc.nextLine();
        u.setPassword(newP);
        System.out.println("Se ha cambiado la contraseña a "+newP);
    }

    public static void testSetMaxPoint() {
        System.out.println("Introduzca un nuevo punto máx");
        sc.nextLine();
        int newMP = sc.nextInt();
        u.setMaxPoint(newMP);
        System.out.println("Se ha cambiado el punto máx a "+newMP);
    }

    private static void printOptions() {
        System.out.println("Opciones:");
        System.out.println("1. Constructora");
        System.out.println("2. GetUsername");
        System.out.println("3. GetPassword");
        System.out.println("4. GetMaxPoint");
        System.out.println("5. SetUsername");
        System.out.println("6. SetPassword");
        System.out.println("7. SetMaxPoint");
        System.out.println("8. Salir");
    }

    public static void main(String[] args) {
        printOptions();
        int op = sc.nextInt();
        while(op != 8) {
            switch (op) {
                case 1:
                    System.out.println("Has seleccionado: Constructora");
                    testConstructora();
                    break;
                case 2:
                    System.out.println("Has seleccionado: GetUsername");
                    testGetUsername();
                    break;
                case 3:
                    System.out.println("Has seleccionado: GetPassword");
                    testGetPassword();
                    break;
                case 4:
                    System.out.println("Has seleccionado: GetMaxPoint");
                    testGetMaxPoint();
                    break;
                case 5:
                    System.out.println("Has seleccionado: SetUsername");
                    testSetUsername();
                    break;
                case 6:
                    System.out.println("Has seleccionado: SetPassword");
                    testSetPassWord();
                    break;
                case 7:
                    System.out.println("Has seleccionado: SetMaxPoint");
                    testSetMaxPoint();
                    break;
                default:
                    System.out.println("valor no válido");
                    break;
            }
            printOptions();
            op = sc.nextInt();
        }
    }
}
