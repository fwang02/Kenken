package Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;


public class CtrlDomain {
    private static final CtrlDomainUser ctrlDomainUser = CtrlDomainUser.getInstance();
    private static final Ranking ranking = Ranking.getInstance();
    private static User loggedUser;
    private static KenkenPlay currentPlay;
    private static Kenken currentGame;

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

        Kenken Game = new Kenken(size,opSet,TypeDificult.EXPERT,cages);
        KenkenConfig kenkenConfig = new KenkenConfig(Game);
        if(kenkenConfig.solveKenken()) {
            KenkenPlay currentPlay = new KenkenPlay(Game);
            currentPlay.start();    
        }
        else {
            System.out.println("El Kenken no tiene solucion");
        }

    }

    public Boolean generateKenkenByDifficulty(int size, HashSet<TypeOperation> ops, TypeDificult difficulty){
        Kenken kenken = new Kenken(size,ops,difficulty);
        KenkenConfig kkConfig = new KenkenConfig(kenken);
        kkConfig.generateKenkenv1();
        currentGame = kenken;
        return true;
    }

    public Boolean generateKkByNumCagesNumICells(int size, HashSet<TypeOperation> ops, int numCages, int numICells) {
        Kenken kenken = new Kenken(size,ops,numCages,numICells);
        KenkenConfig kkConfig = new KenkenConfig(kenken);
        kkConfig.generateKenkenv2();
        currentGame = kenken;
        return true;
    }

    public Boolean generateKenkenByNumCages(int size, HashSet<TypeOperation> ops, int numCages) {
        Kenken kenken = new Kenken(size,ops,numCages);
        KenkenConfig kkConfig = new KenkenConfig(kenken);
        kkConfig.generateKenkenv2();
        currentGame = kenken;
        return true;
    }

    public void playCurrentGame() {
        Kenken table = new Kenken(currentGame);
        KenkenPlay kkPlay = new KenkenPlay(currentGame,table);
        kkPlay.start();
    }

    //Para pasar el mapa a la capa de presentación
    public TreeMap<Integer,String> showRanking() {
        return ranking.getRanking();
    }

    public void saveCurrentGame(String nameGame) {



    }

    static TypeOperation getOperation(int num)
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
