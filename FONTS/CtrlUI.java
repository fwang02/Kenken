import java.util.HashSet;
import java.util.Scanner;
public class CtrlUI {

    private static final Scanner sc = new Scanner(System.in);
    private boolean finish;

    CtrlPlay ctrlPlay;

    CtrlUI() {
        finish = false;
    }

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

    private void help() {
        System.out.print("1. casillas -> muestra el tablero con las casillas\n");
        System.out.print("2. regiones -> muestra las regiones del tablero\n");
        System.out.print("3. comprueba -> comprueba los valores añadidos\n");
        System.out.print("4. añadir -> añade un valor a las coordenadas X Y del tablero\n");
        System.out.print("5. borrar -> borra el valor de las coordenadas X Y del tablero\n");
        System.out.print("6. reset -> pone todas las casillas a 0\n");
        System.out.print("7. stop -> abandona la partida y sientete como un perdedor\n");
        System.out.print("8. solucion -> muestra la solucion\n");
        System.out.print("9. help -> lista los comandos disponibles\n");
    }

    private static TypeDificult chooseDifficulty()
    {
        System.out.println("Selecciona una dificultad:");
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

    private static int chooseSize()
    {
        System.out.println("Selecciona el tamaño del tablero (3-9).");
        int size = sc.nextInt();
        if(size < 3 || size > 9) throw new IllegalArgumentException("El tamaño debería ser 3-9.");
        else System.out.println("El tamaño es: "+size+"\n");
        return size;
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


    public void main() {
        int size = chooseSize();
        TypeDificult dif = chooseDifficulty();
        HashSet<TypeOperation> operations = chooseOps();

        ctrlPlay = new CtrlPlay(size,operations,dif);

        ctrlPlay.generateKenken();


        System.out.print("¡Hola!, estos son los comandos disponibles: Puedes insertar el numero de orden o la palabra clave.\n");
        help();

        while (!finish) {
            String order = sc.nextLine();

            switch (order) {
                case "1":
                case "casillas":
                    ctrlPlay.listCells();
                    break;
                case "2":
                case "regiones":
                    ctrlPlay.listCages();
                    break;
                case "3":
                case "comprueba":
                    if (ctrlPlay.check()) {
                        System.out.println("Kenken resuelto!");
                        finish = true;
                    }
                    break;
                case "4":
                case "añadir":
                    System.out.println("Inserte los valores de uno en uno tal que: X, Y, valor");
                    int a = sc.nextInt();
                    int b = sc.nextInt();
                    int v = sc.nextInt();
                    sc.nextLine();
                    ctrlPlay.insertNumber(a, b, v);
                    break;
                case "5":
                case "borrar":
                    System.out.println("Inserte los valores de uno en uno tal que: X, Y");
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    sc.nextLine();
                    ctrlPlay.deleteNumber(x, y);
                    break;
                case "6":
                case "reset":
                    ctrlPlay.reset();
                    break;
                case "7":
                case "stop":
                    finish = true;
                    break;
                case "8":
                case "solucion":
                    ctrlPlay.showSolution();
                    break;
                case "9":
                case "help":
                    help();
                    break;
                default:
                    //System.out.println("Comando no válido");
                    break;
            }
        }

    }
}
