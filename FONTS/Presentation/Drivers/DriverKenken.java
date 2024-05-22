package Presentation.Drivers;

import Domain.*;
import Domain.Controllers.CtrlDomainKenken;
import Domain.Operation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class DriverKenken {
	private static CtrlDomainKenken CDK;
	private static Scanner sc;
    private static boolean stop = false;

    private static void printMethods() {
        System.out.println("1 -> Generar Kenken");
        System.out.println("20 -> Guardar Kenken");
        System.out.println("30 -> Continuar Kenken");
        System.out.println("2 -> Importar Kenken");
        System.out.println("3 -> Crear Kenken");
        System.out.println("4 -> Jugar Kenken ya disponible");
        System.out.println("\nANTES DE PROBAR LOS SIGUIENTES METODOS HAS DE HABER GENERADO O IMPORTADO UN KENKEN\n");
        System.out.println("5 -> Mostrar regiones del kenken");
        System.out.println("6 -> Mostrar solucion del kenken");
        System.out.println("7 -> Mostrar tablero actual del kenken");
        System.out.println("8 -> añadir numero al tablero");
        System.out.println("9 -> borrar numero del tablero");
        System.out.println("10 -> resetear el tablero de juego");
        System.out.println("11 -> dame una pista");
        System.out.println("12 -> comprueba la solucion/estado del tablero");
        System.out.println("stop -> salir del driver");
    }

	private static TypeDifficulty chooseDifficulty()
    {
        System.out.println("Selecciona una dificultad.");
        System.out.println("1. Fácil");
        System.out.println("2. Medio");
        System.out.println("3. Difícil");
        System.out.println("4. Experto");

        int num_dif = sc.nextInt();
        TypeDifficulty dif;

        switch(num_dif) {
            case 1:
                dif = TypeDifficulty.EASY;
                break;
            case 2:
                dif = TypeDifficulty.MEDIUM;
                break;
            case 3:
                dif = TypeDifficulty.HARD;
                break;
            case 4:
                dif = TypeDifficulty.EXPERT;
                break;
            default:
                throw new IllegalArgumentException("El valor debería ser entre 1 y 4.");
        }
        System.out.println("Dificultad: "+ dif +"\n");
        return dif;
    }

    private static int chooseSize()
    {
        System.out.println("Selecciona el tamaño del tablero (3-9).");
        int size = sc.nextInt();
        if(size < 3 || size > 9) throw new IllegalArgumentException("El tamaño debería ser 3-9.");
        else System.out.println("El tamaño es: "+size);
        return size;
    }

    private static Operation getOperation(int num)
    {
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
                throw new IllegalArgumentException("Carácter no válido encontrado en la cadena: " + num);
        }
    }

    private static HashSet<Operation> chooseOps()
    {
        System.out.println("Selecciona las operaciones.");
        System.out.println("+-----------+------------+");
        System.out.println("| 1. ADD  + | 2. SUB   - |");
        System.out.println("| 3. MULT * | 4. DIV   : |");
        System.out.println("| 5. MOD  % | 6. POW   ^ |");
        System.out.println("+-----------+------------+");
        System.out.println("Poner los números consecutivamente.");

        HashSet<Operation> operations = new HashSet<>();
        sc.nextLine();
        String op = sc.nextLine();

        for (int i = 0; i < op.length(); ++i) {
            int num = Character.getNumericValue(op.charAt(i)); // Obtener el carácter en la posición i
            operations.add(getOperation(num));
        }
        if(operations.isEmpty()) throw new NullPointerException("La lista de operaciones no puede ser nula.");

        System.out.println("Has seleccionado las siguientes operaciones:");
        for(Operation operation : operations) {
            System.out.println(operation);
        }
        System.out.print('\n');
        return operations;
    }

    private static void generatekenken() {
        int size = chooseSize();
        TypeDifficulty dif = chooseDifficulty();
        HashSet<Operation> operations = chooseOps();
        CDK.generateKenkenByDifficulty(size, operations, dif);
        System.out.println("KENKEN GENERADO, YA PUEDES JUGAR");
    }

    private static void saveKenken() {
        if(!CDK.hasCurrentGame()) {
            System.out.println("NECESITAS UN KENKEN");
            return;
        }
        else {
            CDK.saveKenken();
        }
    }
    private static void continueKenken() {
        System.out.println("Nombre de la partida que quieres continuar (GAME)");
        String name = sc.nextLine();
        if(!CDK.continueKenken(name)) {
            System.out.println("El Kenken introducido no tiene solucion");
            return;
        }
        System.out.println("KENKEN CARGADO, YA PUEDES CONTINUAR");
    }

    private static void importKenken() {
        System.out.println("Introduzca el nombre del fichero. Recuerde que el fichero ha de estar en el directorio DATA");
        String name = sc.nextLine();
        if(!CDK.solveKenkenByFile(name)) {
            System.out.println("El Kenken introducido no tiene solucion");
            return;
        }
        System.out.println("KENKEN GENERADO, YA PUEDES JUGAR");
    }

    private static void defineKenken() {
        int size = chooseSize();
        int maxCells = 0;
        KenkenCell[][] cells = CDK.getDomainCells(size);
        HashSet<Operation> opSet = CDK.getDomainOperations();
        ArrayList<KenkenCage> cages = CDK.getDomainCages();
        System.out.println("\nVamos a definir las regiones");
        System.out.println("Cuando hayas finalizado, escribe stop");
        System.out.println("Para indicar una region, sigue el siguiente esquema: ");
        System.out.println("OPERACION, RESULTADO, NUMERO DE CASILLAS, CASILLA 1.x, CASILLA 1.y ...  (como el formato estandar de fichero)");
        System.out.println("OPERADORES: 1.ADD, 2.SUB, 3.MULT, 4.DIV, 5.MOD, 6.POW\n");
        String line = "";
        Scanner cage = new Scanner(System.in);
        line = cage.nextLine();

        while(!line.equals("stop")) {
            String[] numStr = line.split("\\s+");
            Operation op = getOperation(Integer.parseInt(numStr[0]));
            opSet.add(op);

            int result = Integer.parseInt(numStr[1]);
            int numCells = Integer.parseInt(numStr[2]);
            Pos[] posCells = new Pos[numCells];
            int offset = 3;
            for (int i = 0; i < numCells; i++) {
                int posX = Integer.parseInt(numStr[offset + 2*i]) - 1;
                int posY = Integer.parseInt(numStr[offset + 1 + 2*i]) - 1;
                cells[posX][posY] = CDK.getNewKenkenCell(posX, posY, 0, false);
                ++maxCells;
                posCells[i] = new Pos(posX,posY);
            }
            cages.add(CDK.getNewKenkenCage(op, result, posCells));
            line = cage.nextLine();
        }

        if(maxCells > size*size) {
            System.out.println("Has introducido mas casillas de las que puedes");
            return;
        }
        else {
            boolean generated = CDK.solveKenkenByUserParameters(size, opSet, TypeDifficulty.EXPERT, cages, cells);
            if(generated) {
                System.out.println("KENKEN GENERADO, YA PUEDES JUGAR");
            }
            else {
                System.out.println("KENKEN NO PUEDE SER RESUELTO");
            }
        }
    }

    private static void databaseKenken() {
        System.out.println("Kenken disponibles: ");
        System.out.println("BASICOS: basico3x3 - basico4x4 - basico5x5 - basico6x6 - basico7x7 - basico8x8 - basico9x9");
        String name = sc.nextLine();
        if(CDK.solveKenkenByFile(name)) {
            System.out.println("KENKEN GENERADO, YA PUEDES JUGAR");
        }
    }

    private static void showRegions() {
        ArrayList<KenkenCage> cages = CDK.getCages();
        for(int i = 0; i < cages.size(); ++i) {
            System.out.print("Cage " + i + "-> ");
            System.out.print("Size: " + cages.get(i).getCageSize() + " ");
            System.out.print("Cells: ");
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
        KenkenCell[][] cells = CDK.getSolution();
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
        int[][] board = CDK.getBoard();
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
        if(!CDK.insertNumberBoard(a,b,v)) {
            System.out.println("Error: Por favor, inserte valores entre 1 i ");
            System.out.println("Error: Por favor, inserte coordenadas validas");
        }
    }

    private static void deleteNumberFromBoard() {
        System.out.println("Inserte los valores de uno en uno tal que: X, Y");
        int a = sc.nextInt();
        int b = sc.nextInt();
        sc.nextLine();
        if(!CDK.deleteNumberBoard(a,b)) {
            System.out.print("Error: Por favor, inserte coordenadas validas");
        }
    }

    private static void askHint() {
        //cdk.Hint();
        int[][] board = CDK.getBoard();
        KenkenCell[][] cells = CDK.getSolution();
        int size = board.length;
        boolean hintGived = false;
        for(int i = 0; i < size && !hintGived; ++i) {
            for(int j = 0; j < size && !hintGived; ++j) {
                if(board[i][j] == 0) {
                    hintGived = true;
                    int v = cells[i][j].getValue();
                    CDK.incrHintsCurrGame();
                    int x = i + 1;
                    int y = j + 1;
                    System.out.println("Prueba con poner " + v + " en la casilla x: " + x + " y: " + y + " Puede que funcione");
                }
            }
        }
    }

    private static void askCheck() {
        int p = CDK.check();
        if(p >= 1) {
            System.out.println("HAS RESUELTO EL KENKEN, ENHORABUENA, PUNTUACION: " + p);
        }
        else if (CDK.check() == -1){
            System.out.println("ALGUNA CASILLA QUE HAS COLOCADO ES INCORRECTA");
        }
        else {
            System.out.println("ESTA TODO MUY VACIO");
        }
    }

    

	public static void main(String [] args) {
        sc = new Scanner(System.in);
		CDK = new CtrlDomainKenken();
        printMethods();
        String order = sc.nextLine();
        while(!order.equals("stop")) {
            switch(order) {
                case "1": 
                    generatekenken();
                    break;
                case "20":
                    saveKenken();
                    break;
                case "30":
                    continueKenken();
                    break;
                case "2": 
                    importKenken();
                    break;
                case "3": 
                    defineKenken();
                    break;
                case "4":
                    databaseKenken();
                    break;
                case "5": 
                    showRegions();
                    break;
                case "6":
                    showAnswer();
                    break; 
                case "7": 
                    showCurrent();
                    break;
                case "8": 
                    insertNumberToBoard();
                    break;
                case "9": 
                    deleteNumberFromBoard();
                    break;
                case "10":
                    CDK.resetBoard();
                    break;
                case "11":
                    askHint();
                    break;
                case "12":
                    askCheck();
                    break;
            }  
            order = sc.nextLine(); 
        }
        sc.close();
	}
}