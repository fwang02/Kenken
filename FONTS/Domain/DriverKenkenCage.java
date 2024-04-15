package Domain;

import java.util.Scanner;

public class DriverKenkenCage {
    private static KenkenCage kkc;
    private static final Scanner in = new Scanner(System.in);

    public static void testConstructora() {
        System.out.println("Selecciona la operación de la región.");
        System.out.println("+-----------+------------+");
        System.out.println("| 1. ADD  + | 2. SUB   - |");
        System.out.println("| 3. MULT * | 4. DIV   : |");
        System.out.println("| 5. MOD  % | 6. POW   ^ |");
        System.out.println("+-----------+------------+");

        TypeOperation operation = null;
        int numOp = in.nextInt();

        switch (numOp) {
            case 1:
                operation = TypeOperation.ADD;
                break;
            case 2:
                operation = TypeOperation.SUB;
                break;
            case 3:
                operation = TypeOperation.MULT;
                break;
            case 4:
                operation = TypeOperation.DIV;
                break;
            case 5:
                operation = TypeOperation.MOD;
                break;
            case 6:
                operation = TypeOperation.POW;
                break;
            default:
                System.err.println("Carácter no válido encontrado en la cadena: " + numOp);
                break;
        }

        if(operation == null) System.err.println("La operación no puede ser nula.");

        System.out.println("Escribe el resultado de la región:");
        int result = in.nextInt();
        in.nextLine();

        System.out.println("introduzca el tamaño de la región:");
        int cageSize = in.nextInt();
        Pos[] posCells = new Pos[cageSize];

        for (int i = 0; i < cageSize; i++) {
            System.out.println("Introduzca la posicion de cela "+ (i+1) +":");
            int posX = in.nextInt();
            int posY = in.nextInt();
            if(posX < 0 || posY < 0 || posX > 8 || posY > 8 ) {
                System.out.println("Valor introducido inválido");
                return;
            }
            posCells[i] = new Pos(posX,posY);
        }

        for(Pos p : posCells) {
            p.print();
        }

        kkc = new KenkenCage(operation,result,posCells);

        System.out.println("Se ha creado una región de Kenken correctamente");
    }

    public static void testGetCageSize() {
        try {
            System.out.println("El tamaño de la región es: "+kkc.getCageSize());
        } catch (NullPointerException e) {
            System.err.println("Debería crear una instancia antes de consultar");
        }
    }

    public static void testGetPos() {
        System.out.println("Introduzca un índice de las posiciones de celas:");
        int iPos = in.nextInt();
        try {
            Pos p = kkc.getPos(iPos);
            p.print();
        } catch (NullPointerException e) {
            System.err.println("Debería crear una instancia antes de consultar");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("El índice fuera de rango");
        }

    }

    public static void testSetResult() {
        System.out.println("Introduzca un nuevo resultado:");
        int result = in.nextInt();
        try {
            kkc.setResult(result);
            System.out.println("El resultado se ha cambiado a "+result);
        } catch (NullPointerException e) {
            System.err.println("Debería crear una instancia antes de hacer esta operación");
        }
    }

    private static void testGetResult() {
        try {
            int result = kkc.getResult();
            System.out.println("El resultado es "+result);
        } catch (NullPointerException e) {
            System.err.println("Debería crear una instancia antes de hacer esta operación");
        }
    }



    private static void printOp() {
        System.out.println("Options:");
        System.out.println("1. Constructora");
        System.out.println("2. ");
    }



    public static void main(String[] args) {
        printOp();
        int op = in.nextInt();
        while(op != 9) {
            switch (op) {
                case 1:
                    System.out.println("Has seleccionado: Constructora");
                    testConstructora();
                    break;
                case 2:
                    System.out.println("Has seleccionado: GetCageSize");
                    testGetCageSize();
                    break;
                case 3:
                    System.out.println("Has seleccionado: GetPos");
                    testGetPos();
                    break;
                case 4:
                    System.out.println("Has seleccionado: SetResult");
                    testSetResult();
                    break;
                case 5:
                    System.out.println("Has seleccionado: GetResult");
                    testGetResult();
                default:
                    System.out.println("Valor introducido inválido");
                    break;
            }
            printOp();
            op = in.nextInt();
        }

    }


}
