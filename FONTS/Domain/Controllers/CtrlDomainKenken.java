package Domain.Controllers;

import Domain.*;
import Domain.Operation.*;
import Persistence.CtrlKenkenFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Controlador de Dominio del Kenken. Gestiona el Kenken que se esta jugando actualmente en
 * la varible currentGame
 * 
 * @author Javier Parcerisas
 */
public class CtrlDomainKenken {
	private Kenken currentGame;
	private final CtrlKenkenFile CKF;

	/**
     * Constructora de la clase CtrlDomainKenken
     */
	public CtrlDomainKenken() {
		currentGame = null;
		CKF = CtrlKenkenFile.getInstance();
	}

	/**
     * Función que traduce de un número a su operación correspondiente
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
     * Función que crea un kenken y lo asigna como juego actual
	 * 
	 * @param size indica el tamaño del kenken
	 * @param ops indica las operaciones del kenken
	 * @param difficulty indica la dificultad del kenken
	 * @return devuelve cierto si se genera el kenken correctamente
     */
	public boolean generateKenkenByDifficulty(int size, HashSet<Operation> ops, TypeDifficulty difficulty){
        Kenken kenken = new Kenken(size,ops,difficulty);
        KenkenConfig kenkenConfig = new KenkenConfig(kenken);
        kenkenConfig.generateKenkenV1();

		for (KenkenCell[] row : kenken.getAllCells()) {
			for (KenkenCell c : row) {
				c.setUnlocked();
			}
		}

        currentGame = kenken;
		return true;
	}

	/**
     * Función soluciona el kenken currentGame
	 * 
	 * @return devuelve cierto si el kenken tiene solución, falso si no tiene
     */
    public boolean solveCurrentGame() {
    	KenkenConfig kenkenConfig = new KenkenConfig(currentGame);
        return kenkenConfig.solveKenken();
    }

	/**
     * Función que carga en currentGame un kenken desde un fichero
	 * 
	 * @param fileName nombre del fichero que contiene el kenken
	 * @return devuelve cierto si el kenken tiene solución, falso si no tiene
     */
    public boolean solveKenkenByFile(String fileName) {
    	Kenken kenken = CKF.readKenkenByFile(fileName);
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

	/**
     * Función que carga en currentGame un kenken desde un fichero
	 * 
	 * @param file fichero que contiene el kenken
	 * @return devuelve cierto si el kenken tiene solución, falso si no tiene
     */
	public boolean solveKenkenByFile(File file) {
		Kenken kenken = CKF.readKenkenByFile(file);
		if(kenken == null) return false;
		KenkenConfig kenkenConfig = new KenkenConfig(kenken);
		if (kenkenConfig.solveKenken()) {
			currentGame = kenken;
			return true;
		}
		else {
			return false;
		}
	}

	/**
     * Función que guarda el kenken de currentGame
	 * 
	 * @param gameName nombre del kenken guardado
	 * @param values valor del tablero del jugador
	 * @return devuelve cierto si se ha podido guardar correctamente, falso en caso contrario
     */
	public boolean saveCurrentGame(String gameName, int[] values) {
		int s = currentGame.getSize();
		int[][] auxboard = new int[s][s];
		for(int i = 0; i < s*s; ++i) {
			int x = i / s;
			int y = i % s;
			auxboard[x][y] = values[i];
		}
		currentGame.setBoard(auxboard);
        return CKF.saveKenkenGame(currentGame, gameName);
	}

	/**
     * Función que carga en currentGame un kenken desde un fichero y con un 
	 * tablero de juego de usuario ya definido
	 * 
	 * @param file fichero que contiene el kenken
	 * @return devuelve cierto si el kenken se ha podido cargar correctamente
     */
	public boolean continueKenken(File file) {
		Kenken kenken = CKF.loadKenkenByFile(file);
		if(kenken == null) return false;
		else {
			currentGame = kenken;
			return true;
		}
	}

	/**
     * Getter que nos permite obtener los valores del tablero de juego del usuario
	 * del kenken actual
	 * 
	 * @return devuelve los valores del tablero de currentGame
     */
    public int[] getCurrentGameBoard() {
		int[][] auxboard = currentGame.getBoard();
		int s = currentGame.getSize();
		int[] values = new int[s*s];
		for(int i = 0; i < s*s; ++i) {
			int x = i / s;
			int y = i % s;
			values[i] = auxboard[x][y];
		}
		return values;
	}

	/**
     * Getter que nos permite obtener las regiones del kenken actual
	 * 
	 * @return devuelve las regiones de currentGame
     */
    public ArrayList<KenkenCage> getCages() {
		return currentGame.getAllCages();
	}

	/**
     * Getter que nos permite obtener las posiciones del eje x de las casillas de
	 * una región del kenken actual
	 * 
	 * @param index posición de la región
	 * @return devuelve las posiciones x de las casillas de currentGame
     */
	public int[] getCurrentCageCellsX(int index) {
		KenkenCage cage = currentGame.getAllCages().get(index);

        int ncells = cage.getCageSize();
        int[] cellsX = new int[ncells];

        for (int i = 0; i < ncells; ++i) {
            cellsX[i] = cage.getPos(i).posX;
        }

        return cellsX;
	}

	/**
     * Getter que nos permite obtener las posiciones del eje x de las casillas de
	 * una región del kenken actual
	 * 
	 * @param index posición de la región
	 * @return devuelve las posiciones y de las casillas de currentGame
     */
	public int[] getCurrentCageCellsY(int index) {
        KenkenCage cage = currentGame.getAllCages().get(index);

        int ncells = cage.getCageSize();
        int[] cellsY = new int[ncells];

        for (int i = 0; i < ncells; ++i) {
            cellsY[i] = cage.getPos(i).posY;
        }

        return cellsY;
    }

	/**
     * Getter que nos permite obtener las casillas del kenken actual
	 * 
	 * @return devuelve las casillas de currentGame
     */	
	public int[] getCurrentCells() {
		KenkenCell[][] kCells = currentGame.getAllCells();

        int s = getCurrentGameSize();
        int[] cells = new int[s*s];

        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                cells[i * s + j] = kCells[i][j].getValue();
            }
        }

        return cells;
	}

	/**
     * Función utilizada para conseguir pistas sobre el juego actual
	 * 
	 * @param values estado actual del tablero de juego del usuario
	 * @return devuelve la posición de la casilla y el valor que le corresponde
     */
	public int[] hintCurrentGame(int[] values) {
		int[] hint;
        int s = currentGame.getSize();
        for(int i = 0; i < s*s; ++i) {
            if(values[i] == 0) {
                int x = i / s;
                int y = i % s;
                int res = currentGame.getCell(x, y).getValue();
                hint = new int[] {x, y, res};
                return hint;
            }
        }
		for(int i = 0; i < s*s; ++i) {
			int x = i / s;
            int y = i % s;
            if(values[i] != currentGame.getCell(x, y).getValue()) {
                int res = currentGame.getCell(x, y).getValue();
                hint = new int[] {x, y, res};
                return hint;
            }
        }
        hint = new int[] {0,0,0};
        return hint;
    }

	/**
     * Función que incrementa el número de pistas utilizadas para solucionar el kenken
     */
	public void incrHintsCurrGame() {
		currentGame.incrHints();
	}

	/**
     * Función que nos permite comprobar la respuesta del usuario mediante el tablero
	 * de juego
	 * 
	 * @param values estado actual del tablero de juego del usuario
	 * @return cierto si la respuesta es correcta o falso en caso contrario
     */
    public boolean checkCurrentGame(int[] values) {
    	int s = currentGame.getSize();
		int correctCount = 0;
        for (int i = 0; i < s*s; ++i) {
            int x = i / s;
            int y = i % s;
            if (values[i] != 0) {
                int correctValue = currentGame.getCell(x, y).getValue();
				++correctCount;
                if (values[i] != correctValue) return false;
            }
        }
		if(correctCount == s*s) currentGame.setFinished();
        return true;
	}

	/**
	 * Getter que nos permite obtener los puntos obtenidos al solucionar el kenken
	 * 
	 * @return cantidad de puntos obtenidos
	 */
    public int getPoints() {
    	return currentGame.getPoints();
    }

	/**
	 * Función que nos permite comprobar si hay un currentGame cargado
	 * 
	 * @return cierto si hay un currentGame o falso si no hay
	 */
	public boolean hasCurrentGame() {
		return currentGame != null;
	}

	/**
	 * Getter que nos permite obtener el kenken acutal
	 * 
	 * @return currentGame
	 */
	public Kenken getCurrentGame() {
		return currentGame;
	}

	/**
	 * Getter que nos permite obtener el tamaño del kenken actual
	 * 
	 * @return tamaño del currentGame
	 */
	public int getCurrentGameSize() {
		return currentGame != null ? currentGame.getSize() : 0;
	}

	/**
	 * Setter que nos permite asignar un kenken actual
	 * 
	 * @param k kenken que asignara como currentGame
	 */
	public void setCurrentGame(Kenken k) {
		currentGame = k;
	}

	/**
	 * Getter que nos permite obtener las casillas estaticas
	 * 
	 * @return casillas estaticas con un valor fijo y visible
	 */
	public int[] getLockedCells() {
		KenkenCell[][] kCells = currentGame.getAllCells();

		int s = getCurrentGameSize();
		int[] cells = new int[s*s];

		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				cells[i * s + j] = kCells[i][j].isLocked() ? kCells[i][j].getValue() : 0;
			}
		}

		return cells;
	}

	/**
	 * Setter que nos permite asignar las casillas estaticas
	 * 
	 * @param valCells valor de las casillas estaticas
	 */
	public void setLockedCells(int[] valCells) {
		KenkenCell[][] kCells = currentGame.getAllCells();

		int s = getCurrentGameSize();

		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				if (valCells[i*s + j] > 0) {
					kCells[i][j].setValue(valCells[i*s+j]);
					kCells[i][j].setLocked();
				}
			}
		}
	}
}
