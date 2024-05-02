/**
 * @author Javier Parcerisas Nisa
 */
package Domain;

import Domain.Operation.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class CtrlDomainKenken {
	private Kenken currentGame;
	private KenkenCell[][] cells;
    private HashSet<Operation> opSet;
    private ArrayList<KenkenCage> cages;

	public CtrlDomainKenken() {
		currentGame = null;
        opSet = null;
        cages = null;
        cells = null;
	}

	public KenkenCell[][] getDomainCells(int size) {
		cells = new KenkenCell[size][size];
		return cells;
	}

	public HashSet<Operation> getDomainOperations() {
		opSet = new HashSet<>();
		return opSet;
	}

	public ArrayList<KenkenCage> getDomainCages() {
		cages = new ArrayList<>();
		return cages;
	}

	static Operation getOperation(int num) {
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

	private Kenken readKenkenByFile(String fileName) {
		try {
			Scanner scanner = new Scanner(new File("../DATA/" + fileName + ".txt"));
			int size = scanner.nextInt();
			if (size > 9 || size < 3) {
				System.out.println("El tamaño indicado en el fichero es incorrecto, debe ser 3-9");
				return null;
			}
			int numCage = scanner.nextInt();

			KenkenCell[][] cells = new KenkenCell[size][size];

			scanner.nextLine();
			HashSet<Operation> opSet = new HashSet<>();
			ArrayList<KenkenCage> cages = new ArrayList<>(numCage);

			int count = 0;
			while (scanner.hasNextLine() && count < numCage) {
				String line = scanner.nextLine();
				String[] numStr = line.split("\\s+");

				Operation op = getOperation(Integer.parseInt(numStr[0]));
				opSet.add(op);

				int result = Integer.parseInt(numStr[1]);
				int numCells = Integer.parseInt(numStr[2]);
				Pos[] posCells = new Pos[numCells];

				int offset = 3;
				for (int i = 0; i < numCells; i++) {
					int posX = Integer.parseInt(numStr[offset + 2 * i]) - 1;
					int posY = Integer.parseInt(numStr[offset + 1 + 2 * i]) - 1;

					// cell with [number]
					if ((offset + 2 + 2 * i < numStr.length) && numStr[offset + 2 + 2 * i].startsWith("[")) {
						String str = numStr[offset + 2 + 2 * i];
						int val = Integer.parseInt(str.substring(1, str.length() - 1));

						cells[posX][posY] = new KenkenCell(posX, posY, val, true);
						System.out.println("pos " + posX + " " + posY + ": " + val);
						offset++;
					} else cells[posX][posY] = new KenkenCell(posX, posY, 0, false);

					posCells[i] = new Pos(posX, posY);
				}

				cages.add(new KenkenCage(op, result, posCells));
				++count;
			}
			scanner.close();

			return new Kenken(size, opSet, TypeDifficulty.EXPERT, cages, cells);
		} catch (FileNotFoundException e) {
			return null;
		}
	}


    public boolean generateKenkenByDifficulty(int size, HashSet<Operation> ops, TypeDifficulty difficulty){
        Kenken kenken = new Kenken(size,ops,difficulty);
        KenkenConfig kenkenConfig = new KenkenConfig(kenken);
        kenkenConfig.generateKenkenV1();
        currentGame = kenken;
		return currentGame != null;
	}

    public boolean solveKenkenByUserParameters(int size, HashSet<Operation> operations, TypeDifficulty dificult, ArrayList<KenkenCage> cages, KenkenCell[][] cells) {
    	Kenken kenken = new Kenken(size, operations, dificult, cages, cells);
    	KenkenConfig kenkenConfig = new KenkenConfig(kenken);
    	if(kenkenConfig.solveKenken()) {
    		currentGame = kenken;
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    public boolean solveKenkenByFile(String fileName) {
    	Kenken kenken = readKenkenByFile(fileName);
		if(kenken == null) return false;
    	KenkenConfig kenkenConfig = new KenkenConfig(kenken);
    	if(kenkenConfig.solveKenken()) {
    		currentGame = kenken;
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    // JUGAR CON EL KENKEN

    public boolean insertNumberBoard(int x, int y, int v) {
    	return currentGame.insertNumberBoard(x,y,v);
    }

    public boolean deleteNumberBoard(int x, int y) {
    	return currentGame.deleteNumberBoard(x, y);
    }

    public void resetBoard() {
    	currentGame.resetBoard();
    }

    public int[][] getBoard() {
		//currentGame.showBoard();
		return currentGame.getBoard();
	}

    public ArrayList<KenkenCage> getCages() {
    	//currentGame.showCages();
		return currentGame.getAllCages();
	}

    public KenkenCell[][] getSolution() {
    	//currentGame.showSolution();
		return currentGame.getAllCells();
	}

	public void incrHintsCurrGame() {
		currentGame.incrHints();
	}

    public int check() {
    	return currentGame.check();}

    public int getPoints() {
    	return currentGame.getPoints();
    }

    // Geters y seters

    public KenkenCell getNewKenkenCell(int x, int y, int val, boolean state) {
    	return (new KenkenCell(x,y,val,state));
    }

    public KenkenCage getNewKenkenCage(Operation operation, int result, Pos[] posCells) {
    	return (new KenkenCage(operation, result, posCells));
    }

	public boolean hasCurrentGame() {
		return currentGame != null;
	}

	public Kenken getCurrentGame() {
		return currentGame;
	}
}
