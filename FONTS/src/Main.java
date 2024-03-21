package FONTS.src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Selecciona una dificultad.");
        System.out.println("1. Fácil");
        System.out.println("2. Medio");
        System.out.println("3. Difícil");
        System.out.println("4. Experto");
        int dif = reader.nextInt();

        if(dif == 1) {
            System.out.println("easy");
        } else if (dif == 2) {
            System.out.println("medium");

        } else if (dif == 3) {
            System.out.println("hard");
        } else if (dif == 4) {
            System.out.println("difficult");
        }

    }


}
