package Domain.Controllers;

import Domain.*;
import Domain.Operation.*;
import Persistence.CtrlKenkenFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Javier Parcerisas
 */
public class CtrlDomainKenken {
	private Kenken currentGame;
	private final CtrlKenkenFile CKF;

	public CtrlDomainKenken() {
		currentGame = null;
		CKF = CtrlKenkenFile.getInstance();
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

    public boolean solveCurrentGame() {
    	KenkenConfig kenkenConfig = new KenkenConfig(currentGame);
        return kenkenConfig.solveKenken();
    }

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

	public boolean continueKenken(File file) {
		Kenken kenken = CKF.loadKenkenByFile(file);
		if(kenken == null) return false;
		else {
			currentGame = kenken;
			return true;
		}
	}

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

    public ArrayList<KenkenCage> getCages() {
		return currentGame.getAllCages();
	}

	public int[] getCurrentCageCellsX(int index) {
		KenkenCage cage = currentGame.getAllCages().get(index);

        int ncells = cage.getCageSize();
        int[] cellsX = new int[ncells];

        for (int i = 0; i < ncells; ++i) {
            cellsX[i] = cage.getPos(i).posX;
        }

        return cellsX;
	}

	public int[] getCurrentCageCellsY(int index) {
        KenkenCage cage = currentGame.getAllCages().get(index);

        int ncells = cage.getCageSize();
        int[] cellsY = new int[ncells];

        for (int i = 0; i < ncells; ++i) {
            cellsY[i] = cage.getPos(i).posY;
        }

        return cellsY;
    }

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

	public void incrHintsCurrGame() {
		currentGame.incrHints();
	}

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

    public int getPoints() {
    	return currentGame.getPoints();
    }

	public boolean hasCurrentGame() {
		return currentGame != null;
	}

	public Kenken getCurrentGame() {
		return currentGame;
	}

	public int getCurrentGameSize() {
		return currentGame != null ? currentGame.getSize() : 0;
	}

	public void setCurrentGame(Kenken k) {
		currentGame = k;
	}

	/**
	 * Obtiene las celdas que no cambian.
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
	 * Asigna las celdas que no cambian.
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
