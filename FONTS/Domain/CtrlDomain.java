package Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class CtrlDomain {
    private static final CtrlDomainUser ctrlDomainUser = CtrlDomainUser.getInstance();
    private static final Ranking ranking = Ranking.getInstance();
    private static User loggedUser;
    private static KenkenPlay currentGame;

    CtrlDomain() {
        loggedUser = null;
    }
    public boolean loginUser(String username, String password) {
        if(!ctrlDomainUser.isUserExist(username)) return false;
        if(!ctrlDomainUser.checkPassword(username,password)) return false;
        loggedUser = ctrlDomainUser.getUser(username);
        return true;
    }

    public boolean registerUser(String username, String password) {
        if(ctrlDomainUser.isUserExist(username)) return false;
        ctrlDomainUser.addUser(username,password);
        return true;
    }

    private static void readFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("../DATA/"+fileName+".txt"));
        int size = scanner.nextInt();
        if(size > 9 || size < 3) throw new IllegalArgumentException("Tamaño incorrecto");
        int numCage = scanner.nextInt();
        //for test
        System.out.print(size);
        System.out.print(' ');
        System.out.println(numCage);

        scanner.nextLine();
        HashSet<TypeOperation> opSet = new HashSet<>();
        ArrayList<KenkenCage> cages = new ArrayList<>(numCage);

        int count = 0;
        while(scanner.hasNextLine() && count < numCage) {
            String line = scanner.nextLine();
            String[] numStr = line.split("\\s+");

            TypeOperation op = getOperation(Integer.parseInt(numStr[0]));
            opSet.add(op);

            int result = Integer.parseInt(numStr[1]);
            int numCells = Integer.parseInt(numStr[2]);
            Pos[] posCells = new Pos[numCells];

            for (int i = 0; i < numCells; i++) {
                int posX = Integer.parseInt(numStr[2*i+3])-1;
                int posY = Integer.parseInt(numStr[2*i+4])-1;
                posCells[i] = new Pos(posX,posY);
            }
            //test
            for(Pos p : posCells) {
                System.out.print(p.posX+" ");
                System.out.print(p.posY+"  ");
            }
            System.out.print('\n');

            cages.add(new KenkenCage(op,result,posCells));
            ++count;
        }
        scanner.close();

        Kenken kenken = new Kenken(size,opSet,TypeDificult.EXPERT,cages);
        KenkenConfig kenkenConfig = new KenkenConfig(kenken);
        kenkenConfig.solveKenken();

        Kenken table = new Kenken(kenken);

        currentGame = new KenkenPlay(kenken,table);
        //currentGame.start();

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

}
