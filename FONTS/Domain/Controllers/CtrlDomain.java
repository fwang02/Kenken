/**
 * @author Feiyang Wang
 */
package Domain.Controllers;

import Domain.KenkenCage;
import Domain.KenkenCell;
import Domain.Operation.Operation;
import Domain.PlayerScore;
import Domain.TypeDifficulty;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class CtrlDomain {
    private final CtrlDomainUser ctrlDomainUser;
    private final CtrlDomainKenken ctrlDomainKenken;

    public CtrlDomain() {
        ctrlDomainUser = new CtrlDomainUser();
        ctrlDomainKenken = new CtrlDomainKenken();
    }

    /*public static void setCurrentGame(String content) {
        //ctrlDomainKenken.

    }

    public static String getCurrentGame() {
        //ctrlDomainKenken.getCurrentGame().;
        File gameFile = "../DATA/GAME.txt";

    }*/


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

    public boolean openKenkenByFile(String fileName) {
        return ctrlDomainKenken.solveKenkenByFile(fileName);
    }
    public boolean openKenkenByFile(File file) {
        return ctrlDomainKenken.solveKenkenByFile(file);
    }

    public boolean generateKenkenByDifficulty(int size, HashSet<Operation> operations, TypeDifficulty diff) {
        return ctrlDomainKenken.generateKenkenByDifficulty(size,operations,diff);
    }

    public boolean currentGameExist() {
        return ctrlDomainKenken.hasCurrentGame();
    }

    public ArrayList<KenkenCage> printRegions() {
        return ctrlDomainKenken.getCages();
    }

    public KenkenCell[][] printSolution() {
        return ctrlDomainKenken.getSolution();
    }

    public int[][] printBoard() {
        return ctrlDomainKenken.getBoard();
    }

    public boolean insertNumberBoard(int a, int b, int v) {
        return ctrlDomainKenken.insertNumberBoard(a,b,v);
    }

    public boolean deleteNumberBoard(int a, int b) {
        return ctrlDomainKenken.deleteNumberBoard(a,b);
    }

    public void incrHints() {
        ctrlDomainKenken.incrHintsCurrGame();
    }

    public int checkBoard() {
        return ctrlDomainKenken.check();
    }

    public void resetBoard() {
        ctrlDomainKenken.resetBoard();
    }


    public boolean newMaxPoint(int newMaxPoint) {
        return ctrlDomainUser.updateMaxPointCurrUser(newMaxPoint);
    }

    public int getKenkenSize() {
        return ctrlDomainKenken.getCurrentGameSize();
    }

    /*public int[][] getKenkenCells() {
        int s = getKenkenSize();
        int[][] cells = new int[s][s];

        for (int i = 0; i < s; ++i) {
            for (int j = 0; j < s; ++j) {
                cells[i][j] = ctrlDomainKenken.getDomainCells()
            }
        }
    }*/

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
        System.out.println("getNCages = " + ctrlDomainKenken.getCurrentGame().getNumberCages());
        return ctrlDomainKenken.getCurrentGame().getNumberCages();
    }

    public int[] getCageCellsX(int index) {
        KenkenCage cage = ctrlDomainKenken.getCages().get(index);

        int ncells = cage.getCageSize();
        int[] cellsX = new int[ncells];

        for (int i = 0; i < ncells; ++i) {
            cellsX[i] = cage.getPos(i).posX;
        }

        return cellsX;
    }

    public int[] getCageCellsY(int index) {
        KenkenCage cage = ctrlDomainKenken.getCages().get(index);

        int ncells = cage.getCageSize();
        int[] cellsY = new int[ncells];

        for (int i = 0; i < ncells; ++i) {
            cellsY[i] = cage.getPos(i).posY;
        }

        return cellsY;
    }

    public char getCageOp(int index) {
        KenkenCage cage = ctrlDomainKenken.getCages().get(index);

        return cage.getOperationAsChar();
    }

    public int getCageRes(int index) {
        KenkenCage cage = ctrlDomainKenken.getCages().get(index);

        return cage.getResult();
    }
}
