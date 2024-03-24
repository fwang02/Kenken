package FONTS.src;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selecciona el tamaño.");
        int size = scanner.nextInt();
        if(size < 3 || size > 9) throw new IllegalArgumentException("El tamaño debería ser 3-9.");
        else System.out.println("El tamaño es: "+size+"\n");

        System.out.println("Selecciona una dificultad.");
        System.out.println("1. Fácil");
        System.out.println("2. Medio");
        System.out.println("3. Difícil");
        System.out.println("4. Experto");
        int num_dif = scanner.nextInt();
        scanner.nextLine();
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
        System.out.println("Dificultad: "+dif.name()+"\n");

        System.out.println("Selecciona las operaciones.");
        System.out.println("+-----------+------------+");
        System.out.println("| 1. ADD  + | 2. SUB   - |");
        System.out.println("| 3. MULT * | 4. DIV   / |");
        System.out.println("| 5. MOD  % | 6. POW   ^ |");
        System.out.println("+-----------+------------+");
        System.out.println("Poner los números consecutivamente.");

        HashSet<TypeOperation> operations = new HashSet<>();
        String op = scanner.nextLine();

        for (int i = 0; i < op.length(); ++i) {
            char c = op.charAt(i); // Obtener el carácter en la posición i

            switch (c) {
                case '1':
                    operations.add(TypeOperation.ADD);
                    break;
                case '2':
                    operations.add(TypeOperation.SUB);
                    break;
                case '3':
                    operations.add(TypeOperation.MULT);
                    break;
                case '4':
                    operations.add(TypeOperation.DIV);
                    break;
                case '5':
                    operations.add(TypeOperation.MOD);
                    break;
                case '6':
                    operations.add(TypeOperation.POW);
                    break;
                default:
                    throw new IllegalArgumentException("Carácter no válido encontrado en la cadena: " + c);
            }
        }
        if(operations.isEmpty()) throw new NullPointerException("La lista de operaciones no puede ser nula.");

        System.out.println("Has seleccionado las siguientes operaciones:");
        for(TypeOperation operation : operations) {
            System.out.println(operation.name());
        }




    }


}
