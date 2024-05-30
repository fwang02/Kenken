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
    private MainMenuView mainMenuView;
    private PlayOptionView playOptionView;
    private GameView gameView;
    private GameCreatorView gameCreatorView;

    public CtrlPresentation () {
        ctrlDomain = new CtrlDomain();
        mainMenuView = new MainMenuView(this);
        gameCreatorView = new GameCreatorView(this);
    }

    public static boolean isValid() {

        if (ctrlDomain.openKenkenByFile("../DATA/input.txt")) {
            System.out.println("KENKEN GAME VALIDO");
            return true;
        }
        else {
            System.out.println("KENKEN GAME NO VALIDO");
            return false;
        }
    }


    public void initPresentation() {
        mainMenuView.makeVisible();
        //gameView.makeVisible();
        //gameCreatorView.makeVisible();
    }

    /////Gestion de vistas
    public void mainViewToPlayOptionView() {
        mainMenuView.makeInvisible();
        if(playOptionView == null) playOptionView = new PlayOptionView(this);
        playOptionView.makeVisible();
        playOptionView.addLoggedUser();
        //gameView.makeVisible();
    }

    public void playOptionViewToMainMenuView() {
        playOptionView.makeInvisible();
        mainMenuView.makeVisible();
        ctrlDomain.logoutCurrentUser();
    }

    public void playOptionViewToGameView() {
        playOptionView.makeInvisible();
        if(gameView == null) gameView = new GameView(this);
        gameView.startPlay();
        gameView.makeVisible();
        //gameCreatorView.startPlay();
        //gameCreatorView.makeVisible();

    }

    public void gameViewToPlayOptionView() {
        gameView.makeInvisible();
        playOptionView.makeVisible();
    }

    public void gameCreatorViewToPlayOptionView() {
        gameCreatorView.makeInvisible();
        playOptionView.makeVisible();
    }

    //Llamadas a la capa dominio
    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomain.getRanking();
    }

    public boolean openKenkenByFile(File file) {
        return ctrlDomain.openKenkenByFile(file);
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

    public boolean createKenken(int size, HashSet<String> operations, String diff) {
        return ctrlDomain.generateKenkenFromView(size,operations,diff);
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
}