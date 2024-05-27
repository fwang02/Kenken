package Domain.Controllers;

import Domain.*;
import Domain.Operation.*;
import Persistence.CtrlKenkenFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
//import java.util.Scanner;

/**
 * @author Javier Parcerisas
 */
public class CtrlDomainKenken {
	private Kenken currentGame;
	private KenkenCell[][] cells;
    private HashSet<Operation> opSet;
    private ArrayList<KenkenCage> cages;

	private CtrlKenkenFile CKF;

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
		if(kenkenConfig.solveKenken()) {
			currentGame = kenken;
			return true;
		}
		else {
			return false;
		}
	}

	public boolean saveKenken() {
		if(CKF.saveKenkenGame(currentGame, "cacatua")) return true;
		else return false;
	}

	public boolean continueKenken(String fileName) {
		Kenken kenken = CKF.loadKenkenByFile(fileName);
		if(kenken == null) return false;
		else {
			currentGame = kenken;
			return true;
		}
	}

	public boolean continueKenken(File file) {
		Kenken kenken = CKF.loadKenkenByFile(file);
		if(kenken == null) return false;
		else {
			currentGame = kenken;
			return true;
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

}
