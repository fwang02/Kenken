package Domain.Controllers;

import Domain.*;
import Domain.Operation.*;
import Domain.Operation.Operation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @author Feiyang Wang
 */
public class CtrlDomain {
    private final CtrlDomainUser ctrlDomainUser;
    private final CtrlDomainKenken ctrlDomainKenken;

    public CtrlDomain() {
        ctrlDomainUser = new CtrlDomainUser();
        ctrlDomainKenken = new CtrlDomainKenken();
    }

    public boolean loginUser(String username, String password) {
        if(!ctrlDomainUser.isUserExist(username)) return false;
        if(!ctrlDomainUser.isPasswordCorrect(username,password)) return false;
        ctrlDomainUser.loginUser(username);
        return true;
    }

    public boolean registerUser(String username, String password) {
        return ctrlDomainUser.addUser(username,password);
    }

    //Para pasar el mapa a la capa de presentación
    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomainUser.getRanking();
    }

    public boolean solveCurrent() {
        return ctrlDomainKenken.solveCurrentGame();
    }

    public boolean openKenkenByFile(String fileName) {
        return ctrlDomainKenken.solveKenkenByFile(fileName);
    }
    public boolean openKenkenByFile(File file) {
        return ctrlDomainKenken.solveKenkenByFile(file);
    }

    public boolean continueGame(File file) {
        return ctrlDomainKenken.continueKenken(file);
    }

    public boolean generateKenkenFromView(int size, HashSet<String> operations, String diff) {
        HashSet<Operation> opSets = new HashSet<>();
        for(String op : operations) {
            switch (op) {
                case "ADD":
                    opSets.add(new ADD());
                    break;
                case "SUB":
                    opSets.add(new SUB());
                    break;
                case "MULT":
                    opSets.add(new MULT());
                    break;
                case "DIV":
                    opSets.add(new DIV());
                    break;
                case "POW":
                    opSets.add(new POW());
                    break;
                case "MOD":
                    opSets.add(new MOD());
                    break;
            }
        }
        return ctrlDomainKenken.generateKenkenByDifficulty(size,opSets,TypeDifficulty.valueOf(diff));
    }

    public int[] hintCurrent(int[] values) {
        ctrlDomainKenken.incrHintsCurrGame();
        return ctrlDomainKenken.hintCurrentGame(values);
    }

    public boolean checkCurrent(int[] values) {
        return ctrlDomainKenken.checkCurrentGame(values);
    }

    public boolean newMaxPoint(int newMaxPoint) {
        return ctrlDomainUser.updateMaxPointCurrUser(newMaxPoint);
    }

    public int getKenkenSize() {
        return ctrlDomainKenken.getCurrentGameSize();
    }

    public boolean isUserExist(String username) {
        return ctrlDomainUser.isUserExist(username);
    }

    public String getLoggedUserName() {
        return ctrlDomainUser.getLoggedUser();
    }

    public void logoutCurrentUser() {
        ctrlDomainUser.logoutCurrentUser();
    }

    public int getNCages() {
        return ctrlDomainKenken.getCurrentGame().getNumberCages();
    }

    public int[] getCageCellsX(int index) {
        return ctrlDomainKenken.getCurrentCageCellsX(index);
    }

    public int[] getCageCellsY(int index) {
        return ctrlDomainKenken.getCurrentCageCellsY(index);
    }

    public char getCageOp(int index) {
        KenkenCage cage = ctrlDomainKenken.getCages().get(index);

        return cage.getOperationAsChar();
    }

    public int getCageRes(int index) {
        KenkenCage cage = ctrlDomainKenken.getCages().get(index);

        return cage.getResult();
    }

    public int[] getCurrentBoard() {
        return ctrlDomainKenken.getCurrentGameBoard();
    }

    public int[] getCells() {
        return ctrlDomainKenken.getCurrentCells();
    }

    public void setCage(int[] cellsX, int[] cellsY, int op, int res) {
        Pos[] cells = new Pos[cellsX.length];

        for (int i = 0; i < cellsX.length; ++i) {
            cells[i] = new Pos(cellsX[i], cellsY[i]);
        }
        Operation o = CtrlDomainKenken.getOperation(op);
        ctrlDomainKenken.getCurrentGame().addOpCage(o, res, cells);

    }

    public void initCurrentGame(int size) {
        if (ctrlDomainKenken.hasCurrentGame()) {
            System.out.println("Reset Current Game");
        }

        HashSet<Operation> opSets = new HashSet<>();
        opSets.add(new ADD());
        opSets.add(new SUB());
        opSets.add(new MULT());
        opSets.add(new DIV());
        opSets.add(new MOD());
        opSets.add(new POW());

        Kenken k = ctrlDomainKenken.getCurrentGame();

        ctrlDomainKenken.setCurrentGame(new Kenken(size, opSets, TypeDifficulty.CUSTOM));
    }

    public boolean saveCurrent(String gameName, int[] values) {
        return ctrlDomainKenken.saveCurrentGame(gameName, values);
    }

    public int getGamePoints() {
        return ctrlDomainKenken.getPoints();
    }

    /**
     * Obtiene las celdas que no cambian.
     */
    public int[] getLockedCells() {
         return ctrlDomainKenken.getLockedCells();
    }

    /**
     * Asigna las celdas que no cambian.
     */
    public void setLockedCells(int[] valCells) {
        ctrlDomainKenken.setLockedCells(valCells);
    }
}
