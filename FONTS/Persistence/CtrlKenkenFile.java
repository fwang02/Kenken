/**
 * @author Javier Parcerisas
 */
package Persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashSet;

import Domain.*;
import Domain.Operation.*;

public class CtrlKenkenFile {
    private static final CtrlKenkenFile CTRL_KENKEN_FILE = new CtrlKenkenFile();
    private Kenken currentGame;
	/*private KenkenCell[][] cells;
    private HashSet<Operation> opSet;
    private ArrayList<KenkenCage> cages;*/
    //private static final String filePath = "./DATA/";

    private CtrlKenkenFile() {
    }

    public static CtrlKenkenFile getInstance() {
        return CTRL_KENKEN_FILE;
    }

	// PARA JUGAR FICHEROS YA DEFINIDOS (PARTIDAS ESTANDAR)
    public Kenken readKenkenByFile(String fileName) {
		try {
			Scanner scanner = new Scanner(new File("../DATA/" + fileName + ".txt"));
			return getKenkenByFile(scanner);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

    public Kenken readKenkenByFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			return getKenkenByFile(scanner);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

    private Kenken getKenkenByFile(Scanner scanner) {
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
	}

	// PARA GUARDAR UNA PARTIDA
	public boolean saveKenkenGame(Kenken kenken) {
		try {
			File savedGame = new File("../DATA/GAME.txt");
			FileWriter myWriter = new FileWriter("../DATA/GAME.txt");
			String a = Integer.toString(kenken.getSize());
			String b = Integer.toString(kenken.getNumberCages());
			myWriter.write(a + " " + b + "\n");
			myWriter.write("OPA OPA NUEVA LINEA");
			//myWriter.write(" ");
			//myWriter.write(b);
			myWriter.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}

	// PARA CONTINUAR UNA PARTIDA
	public Kenken loadKenkenByFile(String fileName) {
		try {
			Scanner scanner = new Scanner(new File("../DATA/" + fileName + ".txt"));
			return loadKenkenGame(scanner);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

    public Kenken loadKenkenByFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			return loadKenkenGame(scanner);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	private Kenken loadKenkenGame(Scanner scanner) {
		int size = scanner.nextInt();
		int numCage = scanner.nextInt();

		KenkenCell[][] cells = new KenkenCell[size][size];
		HashSet<Operation> opSet = new HashSet<>();
		ArrayList<KenkenCage> cages = new ArrayList<>(numCage);
		int[][] board = new int[size][size];

		scanner.nextLine();
		
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
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j ) {
				int v = scanner.nextInt();
				board[i][j] = v;
			}
		}
		scanner.close();

		return new Kenken(size, opSet, TypeDifficulty.EXPERT, cages, cells, board);
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
  
}