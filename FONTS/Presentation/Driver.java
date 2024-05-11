/**
 * @author Feiyang Wang
 */
package Presentation;

import Domain.*;
import Domain.Controllers.CtrlDomain;
import Domain.Operation.*;
import java.util.*;


public class Driver {
    private static CtrlDomain CD;
    private static Scanner sc;
    private static boolean end;
    private static boolean firstPage;
    private static boolean gameCreationPage;
    private static boolean playOption;
    private static boolean playing;
    private static boolean resultPage;

    private Driver() {
        CD = new CtrlDomain();
        sc = new Scanner(System.in);
        end = false;
        firstPage = true;
        gameCreationPage = false;
        playOption = false;
        playing = false;
        resultPage = false;
    }

    private static void loginUser() {
        String username;
        String password;

        System.out.println("Escribe el username:");
        username = sc.nextLine();
        System.out.println("Escribe la contraseña:");
        password = sc.nextLine();

        boolean logged = CD.loginUser(username, password);
        if (!logged) System.out.println("El username o la contraseña es incorrecto");
        else {
            System.out.println("Welcome back! " + username);
            firstPage = false;
            gameCreationPage = true;
        }
    }

    private static void registerUser() {
        String username;
        String password;
        System.out.println("Escribe un username nuevo:");
        username = sc.nextLine();
        System.out.println("Configura la contraseña:");
        password = sc.nextLine();

        boolean registered = CD.registerUser(username, password);
        if (registered) {
            System.out.println("El usuario: " + username + " está registrado!");
        } else {
            System.out.println("El usuario ya existe");
        }
    }

    private static boolean generateByDifficulty() {
        System.out.println("Selecciona el tamaño del tablero (3-9).");
        int size = sc.nextInt();
        sc.nextLine();
        if (size < 3 || size > 9) {
            System.out.println("El tamaño ha de ser entre 3 y 9.");
            return false;
        }
        System.out.println("El tamaño es: " + size + "\n");

        HashSet<Operation> operations = chooseOps();
        if (operations == null) {
            return false;
        }

        System.out.println("Selecciona una dificultad.");
        System.out.println("1. Fácil");
        System.out.println("2. Medio");
        System.out.println("3. Difícil");
        System.out.println("4. Experto");
        //System.out.println("5. Custom (Selecciona num de regiones)");

        String numDiff = sc.nextLine();
        TypeDifficulty diff;
        switch (numDiff) {
            case "1":
                diff = TypeDifficulty.EASY;
                break;
            case "2":
                diff = TypeDifficulty.MEDIUM;
                break;
            case "3":
                diff = TypeDifficulty.HARD;
                break;
            case "4":
                diff = TypeDifficulty.EXPERT;
                break;
            default:
                System.out.println("La opción para dificultad no válida");
                return false;
        }
        return CD.generateKenkenByDifficulty(size, operations, diff);
    }

    private static HashSet<Operation> chooseOps() {
        System.out.println("Selecciona las operaciones.");
        System.out.println("+-----------+------------+");
        System.out.println("| 1. ADD  + | 2. SUB   - |");
        System.out.println("| 3. MULT * | 4. DIV   : |");
        System.out.println("| 5. MOD  % | 6. POW   ^ |");
        System.out.println("+-----------+------------+");
        System.out.println("Poner los números consecutivamente.");

        HashSet<Operation> operations = new HashSet<>();
        String op = sc.nextLine();

        for (int i = 0; i < op.length(); ++i) {
            int num = Character.getNumericValue(op.charAt(i)); // Obtener el carácter en la posición i
            operations.add(getOperation(num));
        }
        if (operations.isEmpty()) {
            System.out.println("La lista de operaciones no puede ser nula.");
            return null;
        }

        System.out.println("Has seleccionado las siguientes operaciones:");
        for (Operation operation : operations) {
            System.out.println(operation);
        }
        System.out.print('\n');
        return operations;
    }

    private static Operation getOperation(int num) {
        switch (num) {
            case 1:
                return new ADD();
            case 2:
                return new SUB();
            case 3:
                return new MULT();
            case 4:
                return new DIV();
            case 5:
                return new MOD();
            case 6:
                return new POW();
            default:
                System.out.println("Carácter no válido encontrado en la cadena: " + num);
                return null;
        }
    }

    private static void printMethods() {
        System.out.println("1 -> Mostrar regiones del kenken");
        System.out.println("2 -> Mostrar solucion del kenken");
        System.out.println("3 -> Mostrar tablero actual del kenken");
        System.out.println("4 -> añadir numero al tablero");
        System.out.println("5 -> borrar numero del tablero");
        System.out.println("6 -> resetear el tablero de juego");
        System.out.println("7 -> dame una pista");
        System.out.println("8 -> comprueba la solucion/estado del tablero");
        System.out.println("9 -> salir del juego");
    }

    private static void showRegions() {
        ArrayList<KenkenCage> cages = CD.printRegions();
        for(int i = 0; i < cages.size(); ++i) {
            System.out.print("Region " + i + "-> ");
            System.out.print("Size: " + cages.get(i).getCageSize() + " ");
            System.out.print("Casillas: ");
            for(int j = 0; j < cages.get(i).getCageSize(); ++j) {
                int x = cages.get(i).getPos(j).posX + 1;
                int y = cages.get(i).getPos(j).posY + 1;
                System.out.print(x + " ");
                System.out.print(y + " || ");
            }
            System.out.print(cages.get(i).getOperation() + " = ");
            System.out.print(cages.get(i).getResult() + "\n");
        }
    }

    private static void showAnswer() {
        KenkenCell[][] cells = CD.printSolution();
        System.out.println("Solucion del Kenken");
        for (KenkenCell[] cell : cells) {
            for (int j = 0; j < cells.length; ++j) {
                System.out.print(cell[j].getValue() + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private static void showCurrent() {
        int[][] board = CD.printBoard();
        int size = board.length;
        System.out.print("Kenken\n");
        System.out.print("   ");
        for (int i = 0; i < size; ++i) {
            System.out.print(i+1 + "  ");
        }
        System.out.print("\n");

        for (int i = 0; i < size; ++i) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < size; ++j) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private static void insertNumberToBoard() {
        System.out.println("Inserte los valores de uno en uno tal que: X, Y, valor");
        int a = sc.nextInt();
        int b = sc.nextInt();
        int v = sc.nextInt();
        sc.nextLine();
        if(!CD.insertNumberBoard(a,b,v)) {
            System.out.print("Error: Por favor, inserte valores entre 1 i ");
            System.out.print("Error: Por favor, inserte coordenadas validas");
        }
    }

    private static void deleteNumberFromBoard() {
        System.out.println("Inserte los valores de uno en uno tal que: X, Y");
        int a = sc.nextInt();
        int b = sc.nextInt();
        sc.nextLine();
        if(!CD.deleteNumberBoard(a,b)) {
            System.out.print("Error: Por favor, inserte coordenadas validas");
        }
    }

    private static void askHint() {
        int[][] board = CD.printBoard();
        KenkenCell[][] cells = CD.printSolution();
        int size = board.length;
        boolean hintGived = false;
        for(int i = 0; i < size && !hintGived; ++i) {
            for(int j = 0; j < size && !hintGived; ++j) {
                if(board[i][j] == 0) {
                    hintGived = true;
                    int v = cells[i][j].getValue();
                    CD.incrHints();
                    int x = i + 1;
                    int y = j + 1;
                    System.out.println("Prueba con poner " + v + " en la casilla x: " + x + " y: " + y + " Puede que funcione");
                }
            }
        }
    }

    private static void askCheck() {
        int p = CD.checkBoard();
        if(p >= 1) {
            System.out.println("HAS RESUELTO EL KENKEN, ENHORABUENA, PUNTUACION: " + p);
            if(CD.newMaxPoint(p)) {
                System.out.println("SE HA ACTUALIZADO MAX POINTS DEL USUARIO");
            }
            playing = false;
        }
        else if (CD.checkBoard() == -1){
            System.out.println("ALGUNA CASILLA QUE HAS COLOCADO ES INCORRECTA");
        }
        else {
            System.out.println("ESTA TODO MUY VACIO");
        }
    }

    public static void main(String[] args) {
        CD = new CtrlDomain();
        sc = new Scanner(System.in);
        end = false;
        firstPage = true;
        gameCreationPage = false;
        playOption = false;
        playing = false;
        resultPage = false;
        String op;

        while (!end) {
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
                        PriorityQueue<PlayerScore> rk = CD.getRanking();
                        if(rk.isEmpty()) {
                            System.out.println("No hay usuario, el ranking está vacío");
                            break;
                        }
                        int rank = 1;
                        Iterator<PlayerScore> it = rk.iterator();
                        System.out.println("Rank  Username  MaxPoint");
                        while(it.hasNext()) {
                            PlayerScore ps = it.next();
                            System.out.println(rank+". "+ps.getName()+": "+ps.getMaxScore());
                            rank++;
                        }
                        break;
                    default:
                        System.out.println("Comando incorrecto");

                }
            }

            while (gameCreationPage) {
                System.out.print('\n');
                System.out.println("1. Crear nuevo");
                System.out.println("2. Importar desde fichero");
                System.out.println("3. Jugar uno existente");
                System.out.println("4. Ranking");
                System.out.print('\n');
                op = sc.nextLine();

                switch (op) {
                    case "1":
                        boolean generated = generateByDifficulty();
                        if (!generated) {
                            System.out.println("El kenken no se ha generado, vuelve a intentarlo");
                            break;
                        }
                        gameCreationPage = false;
                        playOption = true;
                        break;
                    case "2":
                        System.out.println("Escribe el nombre del fichero:");
                        String fileName = sc.nextLine();
                        boolean imported = CD.importKenkenByFile(fileName);
                        if (!imported) {
                            System.out.println("No se ha podido encontrar el fichero o el fichero no existe");
                            break;
                        }
                        gameCreationPage = false;
                        playOption = true;
                        break;
                    case "3":
                        System.out.println("Kenken disponibles: ");
                        System.out.println("BASICOS: basico3x3 - basico4x4 - basico5x5 - basico6x6 - basico7x7 - basico8x8 - basico9x9");
                        String name = sc.nextLine();
                        if(CD.importKenkenByFile(name)) {
                            System.out.println("KENKEN GENERADO, YA PUEDES JUGAR");
                            gameCreationPage = false;
                            playOption = true;
                            break;
                        }
                        System.out.println("Kenken no disponible");
                        break;
                    case "4":
                        PriorityQueue<PlayerScore> rk = CD.getRanking();
                        if(rk.isEmpty()) {
                            System.out.println("No hay usuario, el ranking está vacío");
                            break;
                        }
                        int rank = 1;
                        Iterator<PlayerScore> it = rk.iterator();
                        System.out.println("Rank  Username  MaxPoint");
                        while(it.hasNext()) {
                            PlayerScore ps = it.next();
                            System.out.println(rank+". "+ps.getName()+" "+ps.getMaxScore());
                            rank++;
                        }
                        break;
                    default:
                        System.out.println("comando incorrecto");
                }
            }

            while (playOption) {
                System.out.print('\n');
                System.out.println("1. Jugar el actual");
                System.out.println("2. Volver");
                System.out.print('\n');
                op = sc.nextLine();

                switch (op) {
                    case "1":
                        if(!CD.currentGameExist()) {
                            System.out.println("No hay kenken cargado");
                            break;
                        }
                        playOption = false;
                        playing = true;
                        break;
                    case "2":
                        playOption = false;
                        gameCreationPage = true;
                        break;
                    default:
                        System.out.println("comando incorrecto");
                }
            }

            String order;
            while (playing) {
                printMethods();
                order = sc.nextLine();
                switch (order) {
                    case "1":
                        showRegions();
                        break;
                    case "2":
                        showAnswer();
                        break;
                    case "3":
                        showCurrent();
                        break;
                    case "4":
                        insertNumberToBoard();
                        break;
                    case "5":
                        deleteNumberFromBoard();
                        break;
                    case "6":
                        CD.resetBoard();
                        break;
                    case "7":
                        askHint();
                        break;
                    case "8":
                        askCheck();
                        break;
                }
            }
        }
    }
}


