/**
 * @author Feiyang Wang
 */
package Domain.Controllers;

import Domain.KenkenCage;
import Domain.KenkenCell;
import Domain.Operation.*;
import Domain.PlayerScore;
import Domain.TypeDifficulty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class CtrlDomain {
    private static final CtrlDomainUser CDU = CtrlDomainUser.getInstance();
    private static CtrlDomainKenken CDK;

    public CtrlDomain() {
        CDK = new CtrlDomainKenken();
    }

    public boolean loginUser(String username, String password) {
        if(!CDU.isUserExist(username)) return false;
        if(!CDU.isPasswordCorrect(username,password)) return false;
        CDU.loginUser(username);
        return true;
    }

    public boolean registerUser(String username, String password) {
        if(CDU.isUserExist(username)) return false;
        CDU.addUser(username,password);
        return true;
    }

    //Para pasar el mapa a la capa de presentación
    public PriorityQueue<PlayerScore> showRanking() {
        return CDU.getRanking();
    }

    public boolean importKenkenByFile(String fileName) {
        return CDK.solveKenkenByFile(fileName);
    }
    public boolean generateKenkenByDifficulty(int size, HashSet<Operation> operations, TypeDifficulty diff) {
        return CDK.generateKenkenByDifficulty(size,operations,diff);
    }

    public boolean currentGameExist() {
        return CDK.hasCurrentGame();
    }

    public ArrayList<KenkenCage> printRegions() {
        return CDK.getCages();
    }

    public KenkenCell[][] printSolution() {
        return CDK.getSolution();
    }

    public int[][] printBoard() {
        return CDK.getBoard();
    }

    public boolean insertNumberBoard(int a, int b, int v) {
        return CDK.insertNumberBoard(a,b,v);
    }

    public boolean deleteNumberBoard(int a, int b) {
        return CDK.deleteNumberBoard(a,b);
    }

    public void incrHints() {
        CDK.incrHintsCurrGame();
    }

    public int checkBoard() {
        return CDK.check();
    }

    public void resetBoard() {
        CDK.resetBoard();
    }


    public boolean newMaxPoint(int newMaxPoint) {
        return CDU.updateMaxPointCurrUser(newMaxPoint);
    }
}
