package Presentation;

import Domain.Controllers.CtrlDomain;
import Domain.PlayerScore;
import Presentation.Views.GameCreatorView;
import Presentation.Views.GameView;
import Presentation.Views.MainMenuView;
import Presentation.Views.PlayOptionView;

import java.io.File;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @author feiyang.wang
 *
 */
public class CtrlPresentation {

    private static CtrlDomain ctrlDomain;
    private final MainMenuView mainMenuView;
    private PlayOptionView playOptionView;
    private GameView gameView;
    private final GameCreatorView gameCreatorView;

    public CtrlPresentation () {
        ctrlDomain = new CtrlDomain();
        mainMenuView = new MainMenuView(this);
        gameCreatorView = new GameCreatorView(this);
        gameView = new GameView(this);
    }

    public void initPresentation() {
        mainMenuView.makeVisible();
    }

    /////Gestion de vistas
    public void mainViewToPlayOptionView() {
        mainMenuView.makeInvisible();
        if(playOptionView == null) playOptionView = new PlayOptionView(this);
        playOptionView.makeVisible();
        playOptionView.addLoggedUser();
    }

    public void playOptionViewToMainMenuView() {
        playOptionView.makeInvisible();
        mainMenuView.makeVisible();
        ctrlDomain.logoutCurrentUser();
    }

    public void playOptionViewToGameCreatorView() {
        playOptionView.makeInvisible();
        gameCreatorView.makeVisible();
    }

    public void playOptionViewToGameView() {
        playOptionView.makeInvisible();
        if(gameView == null) gameView = new GameView(this);
        gameView.startPlay();
        gameView.makeVisible();
    }

    public void gameViewToPlayOptionView() {
        gameView.makeInvisible();
        playOptionView.backToPlayOption();
        playOptionView.makeVisible();
    }

    public void gameCreatorViewToPlayOptionView() {
        gameCreatorView.makeInvisible();
        playOptionView.backToPlayOption();
        playOptionView.makeVisible();
    }

    public void gameCreatorViewToGameView() {
        gameCreatorView.makeInvisible();
        gameView.startPlay();
        gameView.makeVisible();
    }

    //Llamadas a la capa dominio
    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomain.getRanking();
    }

    public boolean createKenken(int size, HashSet<String> operations, String diff) {
        return ctrlDomain.generateKenkenFromView(size,operations,diff);
    }

    public boolean openKenkenByFile(File file) {
        return ctrlDomain.openKenkenByFile(file);
    }

    public boolean openKenkenByFile(String fileName) {
        return ctrlDomain.openKenkenByFile(fileName);
    }

    public boolean saveCurrentGame(String gameName, int[] values) {
        return ctrlDomain.saveCurrent(gameName, values);
    }

    public boolean continueGame(File file) {
        return ctrlDomain.continueGame(file);
    }

    public int getKenkenSize() {
        return ctrlDomain.getKenkenSize();
    }

    public boolean loginUser(String username, String password) {
        return ctrlDomain.loginUser(username,password);
    }

    public boolean isUserExist(String username) {
        return ctrlDomain.isUserExist(username);
    }

    public boolean registerUser(String username, String password) {
        return ctrlDomain.registerUser(username,password);
    }

    public String getLoggedUserName() {
        return ctrlDomain.getLoggedUserName();
    }

    public int getNCages() {
        return ctrlDomain.getNCages();
    }

    public int[] getCageCellsX(int index) {
        return ctrlDomain.getCageCellsX(index);
    }

    public int[] getCageCellsY(int index) {
        return ctrlDomain.getCageCellsY(index);
    }

    public char getCageOp(int index) {
        return ctrlDomain.getCageOp(index);
    }

    public int getCageRes(int index) {
        return ctrlDomain.getCageRes(index);
    }

    public void setGameCreatorSize(int selectedSize) {
        gameCreatorView.initGameCreator(selectedSize);
    }

    public int[] getBoard() {
        return ctrlDomain.getCurrentBoard();
    }

    public int[] getCells() {
        return ctrlDomain.getCells();
    }

    public void setCage(int[] cellsX, int[] cellsY, int op, int res) {
        ctrlDomain.setCage(cellsX, cellsY, op, res);
    }

    public void initCurrentGame(int size) {
        ctrlDomain.initCurrentGame(size);
    }

    public boolean solve() {
        return ctrlDomain.solveCurrent();
    }

    public boolean check(int[] values) {
        return ctrlDomain.checkCurrent(values);
    }

    public int[] hint(int[] values) {
        return ctrlDomain.hintCurrent(values);
    }

    public int getGamePoints() {
        return ctrlDomain.getGamePoints();
    }

    public boolean updateMaxPoint(int gamePoints) {
        return ctrlDomain.newMaxPoint(gamePoints);
    }

    /**
     * Obtiene las celdas que no cambian.
     */
    public int[] getLockedCells() { return ctrlDomain.getLockedCells();}

    /**
     * Asigna las celdas que no cambian.
     */
    public void setLockedCells(int[] valCells) {
        ctrlDomain.setLockedCells(valCells);
    }
}