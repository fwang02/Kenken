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
	private KenkenCell[][] cells;
    private HashSet<Operation> opSet;
    private ArrayList<KenkenCage> cages;
	private final CtrlKenkenFile CKF;

	public CtrlDomainKenken() {
		currentGame = null;
        opSet = null;
        cages = null;
        cells = null;
		CKF = CtrlKenkenFile.getInstance();
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

	public boolean generateKenkenByDifficulty(int size, HashSet<Operation> ops, TypeDifficulty difficulty){
        Kenken kenken = new Kenken(size,ops,difficulty);
        KenkenConfig kenkenConfig = new KenkenConfig(kenken);
        kenkenConfig.generateKenkenV1();
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

    public void resetBoard() {
    	currentGame.resetBoard();
    }

	public void resetCages() {
		currentGame.resetCages();
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

    public KenkenCell[][] getSolution() {
		return currentGame.getAllCells();
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

    // Getters y setters

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

	public int getCurrentGameSize() {
		return currentGame != null ? currentGame.getSize() : 0;
	}

	public void setCurrentGame(Kenken k) {
		currentGame = k;
	}
}
