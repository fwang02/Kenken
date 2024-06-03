package Persistence;

import Domain.*;
import Domain.Operation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Controlador de Dominio de los ficheros relacionados con los Kenken. Sus principales funciones son guardar un kenken en 
 * fichero, leer un kenken desde fichero y continuar una partida de un kenken guardado en fichero
 * 
 * @author Javier Parcerisas
 */
public class CtrlKenkenFile {
    private static final CtrlKenkenFile CTRL_KENKEN_FILE = new CtrlKenkenFile();

	/**
     * Constructora de la clase CtrlDomainKenken
     */
    private CtrlKenkenFile() {
    }

	/**
     * Getter que nos permite obtener la instancia del controlador
	 * 
	 * @return instancia de CtrlKenkenFile
     */
    public static CtrlKenkenFile getInstance() {
        return CTRL_KENKEN_FILE;
    }

	
	/**
     * Función que llama a la lectura de kenkens desde un fichero
	 * 
	 * @param fileName nombre del fichero que contiene el kenken
	 * @return kenken leido del fichero
     */
    public Kenken readKenkenByFile(String fileName) {
		try {
			Scanner scanner = new Scanner(new File("../DATA/" + fileName + ".txt"));
			return getKenkenByFile(scanner);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
     * Función que llama a la lectura de kenkens desde un fichero
	 * 
	 * @param file fichero que contiene el kenken
	 * @return kenken leido del fichero
     */
    public Kenken readKenkenByFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			return getKenkenByFile(scanner);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
     * Función que lee un kenken en fichero. Únicamente lee el kenken y le asigna un tablero
	 * vacio.
	 * 
	 * @param scanner apunta al fichero que contiene el kenken
	 * @return kenken leido del fichero con un tablero nulo
     */
	private Kenken getKenkenByFile(Scanner scanner) {
		int size;
		KenkenCell[][] cells;
		HashSet<Operation> opSet;
		ArrayList<KenkenCage> cages;

		try {
			size = scanner.nextInt();
			if (size > 9 || size < 3) {
				return null;
			}
			int numCage = scanner.nextInt();
			if (numCage > size * size) return null;

			cells = new KenkenCell[size][size];

			scanner.nextLine();
			opSet = new HashSet<>();
			cages = new ArrayList<>(numCage);

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

					if ((offset + 2 + 2 * i < numStr.length) && numStr[offset + 2 + 2 * i].startsWith("[")) {
						String str = numStr[offset + 2 + 2 * i];
						int val = Integer.parseInt(str.substring(1, str.length() - 1));

						cells[posX][posY] = new KenkenCell(posX, posY, val, true);
						offset++;
					} else {
						cells[posX][posY] = new KenkenCell(posX, posY, 0, false);
					}

					posCells[i] = new Pos(posX, posY);
				}

				cages.add(new KenkenCage(op, result, posCells));
				++count;
			}
			scanner.close();
		} catch (Exception e) {
			return null;
		}

		return new Kenken(size, opSet, TypeDifficulty.CUSTOM, cages, cells);
	}

	/**
     * Función que guarda un kenken en ficheo
	 * 
	 * @param kenken kenken que queremos guardar en fichero
	 * @param UserFileName nombre que tendra el fichero
	 * @return cierto si se ha guardado correctamente, falso en caso contrario
     */
	public boolean saveKenkenGame(Kenken kenken, String  UserFileName) {
		try {
			File savedGame = new File("../DATA/savedGames/" + UserFileName + ".txt");
			FileWriter myWriter = new FileWriter("../DATA/savedGames/" + UserFileName + ".txt");

			int size = kenken.getSize();
			int ncages = kenken.getNumberCages();
			myWriter.write(Integer.toString(size) + " " + Integer.toString(ncages) + "\n");

			ArrayList<KenkenCage> cages = kenken.getAllCages();
			Pos p;
			int cont;

			for(KenkenCage cage : cages) {
				int op = getOperationNumber(cage.getOperation());
				int res = cage.getResult();
				int e = cage.getCageSize();
				myWriter.write(Integer.toString(op) + " " + Integer.toString(res) + " " + Integer.toString(e));
				for (int i = 0; i < e; ++i) {
					p = cage.getPos(i);
					cont = kenken.getCell(p).getValue();
					myWriter.write(" " + Integer.toString(p.posX+1) + " " + Integer.toString(p.posY+1));
					if (kenken.getCell(p).isLocked()) myWriter.write(" " + "["+Integer.toString(cont)+"]");
					if (i == e - 1) myWriter.write("\n");
				}
			}

			int[][] board = kenken.getBoard();
			for(int i = 0; i < size; ++i) {
				for(int j = 0; j < size; ++j) {
					if(j == 0 && i == 0) myWriter.write(Integer.toString(board[i][j]));
					else myWriter.write(" " + Integer.toString(board[i][j]));
				}
			}
			myWriter.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}

	/**
     * Función que llama a la lectura de kenkens desde un fichero
	 * 
	 * @param fileName nombre del fichero que contiene el kenken
	 * @return kenken leido del fichero
     */
	public Kenken loadKenkenByFile(String fileName) {
		try {
			Scanner scanner = new Scanner(new File("../DATA/" + fileName + ".txt"));
			return loadKenkenGame(scanner);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
     * Función que llama a la lectura de kenkens desde un fichero
	 * 
	 * @param file fichero que contiene el kenken
	 * @return kenken leido del fichero
     */
    public Kenken loadKenkenByFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			return loadKenkenGame(scanner);
		} catch (Exception e) {
			return null;
		}
	}

	/**
     * Función que lee un kenken en fichero. Lee el kenken de fichero y
	 * seguidamente lee el valor del tablero guardado en el mismo fichero
	 * 
	 * @param scanner apunta al fichero que contiene el kenken
	 * @return kenken leido del fichero con un tablero inicializado
     */
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

		return new Kenken(size, opSet, TypeDifficulty.CUSTOM, cages, cells, board);
	}

	/**
	 * Función que nos permite obtener que operación representa cada número
	 */
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

	/**
	 * Función que nos permite obtener que número representa cada operación
	 */
	static int getOperationNumber(Operation o) {
        if (o instanceof ADD) return 1;
		else if (o instanceof SUB) return 2;
		else if (o instanceof MULT) return 3;
		else if (o instanceof DIV) return 4;
		else if (o instanceof MOD) return 5;
		else if (o instanceof POW) return 6;
		return 0;
    }
  
}