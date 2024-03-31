import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static TypeOperation getOperation(int num)
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
    private static TypeDificult chooseDifficulty(Scanner sc)
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

    private static int chooseSize(Scanner sc)
    {
        System.out.println("Selecciona el tamaño del tablero (3-9).");
        int size = sc.nextInt();
        if(size < 3 || size > 9) throw new IllegalArgumentException("El tamaño debería ser 3-9.");
        else System.out.println("El tamaño es: "+size+"\n");
        return size;
    }
    private static HashSet<TypeOperation> chooseOps(Scanner sc)
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

    private static void readFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int size = scanner.nextInt();
        if(size > 9 || size < 3) throw new IllegalArgumentException("Tamaño incorrecto");
        int numCage = scanner.nextInt();
        //for test
        System.out.print(size);
        System.out.print(' ');
        System.out.println(numCage);
        scanner.nextLine();
        KenkenCage[] cages = new KenkenCage[numCage];
        int count = 0;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] numStr = line.split("\\s+");

            TypeOperation op = Main.getOperation(Integer.parseInt(numStr[0]));
            int result = Integer.parseInt(numStr[1]);
            int numCells = Integer.parseInt(numStr[2]);

            Pos[] posCells = new Pos[numCells];
            for (int i = 0; i < numCells; i++) {
                int posX = Integer.parseInt(numStr[2*i+3]);
                int posY = Integer.parseInt(numStr[2*i+4]);
                posCells[i] = new Pos(posX,posY);
            }
            //test
            for(Pos p : posCells) {
                System.out.print(p.posX+" ");
                System.out.print(p.posY+"  ");
            }
            System.out.print('\n');
            cages[count] = new KenkenCage(op,result,posCells);
            ++count;
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        /*
        Scanner scanner = new Scanner(System.in);
        int size = chooseSize(scanner);
        TypeDificult dif = chooseDifficulty(scanner);
        HashSet<TypeOperation> operations = chooseOps(scanner);

        Kenken kenken = new Kenken(size,operations,dif);
        KenkenPlay kenkenPlay = new KenkenPlay(kenken);

        kenkenPlay.fillKenken(0,0);
        kenkenPlay.printKenken();
        */
        Main.readFile("./DATA/input.txt");

    }


}

/*while (scanner.hasNextLine()) {
            int count = 0;
            int result;
            int cageSize;
            TypeOperation op;
            ArrayList<Pos> pos = new ArrayList<>();
            while (scanner.hasNextInt()) {
                if (count == 0) {
                    op = getOperation(scanner.nextInt());
                    System.out.print(op.toString() + " ");
                } else if (count == 1) {
                    result = scanner.nextInt();
                    System.out.print(result + " ");
                } else if (count == 2) {
                    cageSize = scanner.nextInt();
                    System.out.print(cageSize + " ");
                } else {
                    int posX = scanner.nextInt();
                    int posY = scanner.nextInt();
                    pos.add(new Pos(posX-1,posY-1));
                    System.out.print(posX+" "+posY);
                }
                ++count;
                System.out.print(" count:"+count+'\n');
            }
            scanner.nextLine();
        }*/