package Presentation;

import Domain.CtrlDomainUser;
import Domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DriverUser {
    private static final CtrlDomainUser CDU = CtrlDomainUser.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String op;
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrar");
        System.out.println("3. Listar usuarios");
        System.out.println("4. Salir");
        op = sc.nextLine();
        while(!op.equals("4")) {
            String username;
            String password;
            switch (op) {

                case "1":
                    System.out.println("Escribe el username:");
                    username = sc.nextLine();
                    System.out.println("Escribe la contraseña:");
                    password = sc.nextLine();

                    boolean logged;
                    if(!CDU.isPasswordCorrect(username,password)) logged = false;
                    else {
                        CDU.loginUser(username);
                        logged = true;
                    }
                    if (!logged) System.out.println("El username o la contraseña es incorrecto");
                    else {
                        System.out.println("El usuario actual es " + username);
                    }
                    break;
                case "2":
                    System.out.println("Escribe un username nuevo:");
                    username = sc.nextLine();
                    System.out.println("Configura la contraseña:");
                    password = sc.nextLine();

                    boolean canRegister = !CDU.isUserExist(username);
                    if (canRegister) {
                        CDU.addUser(username,password);
                        System.out.println("El usuario: " + username + " está registrado!");
                    } else {
                        System.out.println("El usuario ya existe");
                    }
                    break;
                case "3":
                    HashMap<String, User> users = CDU.getAllUsers();
                    if(users.isEmpty()) {
                        System.out.println("La lista de usuarios está vacía");
                        break;
                    }
                    System.out.println("Username   Password   MaxPoint");
                    for (Map.Entry<String,User> entry : users.entrySet()) {
                        System.out.println(entry.getKey()+"  "+entry.getValue().getPassword()+"  "+entry.getValue().getMaxPoint());
                    }
                    break;
                default:
                    System.out.println("Comando incorrecto");
            }
            op = sc.nextLine();
        }

    }
}
