package Presentation;

import Domain.Controllers.CtrlDomain;
import Domain.Operation.Operation;
import Domain.PlayerScore;
import Domain.TypeDifficulty;
import Presentation.Views.GameCreatorView;
import Presentation.Views.GameView;
import Presentation.Views.MainMenuView;
import Presentation.Views.PlayOptionView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        playOptionView = new PlayOptionView(this);
        gameView = new GameView(this);
        gameCreatorView = new GameCreatorView(this);
    }


    // TO DO: PASSAR FUNCIONALITAT A CAPA DOMINI
    public static void saveGridToFile(String fileName, String gridString) {
        File savedKenken = new File("../DATA/" + fileName + ".txt");

        try {
            if (!savedKenken.exists()) {
                savedKenken.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(savedKenken));
            writer.write(gridString);
            writer.close();

            // Validar kenken
            if (ctrlDomain.openKenkenByFile(savedKenken)) {
                System.out.println("KENKEN VALIDO");
            }
            else {
                System.out.println("KENKEN NO VALIDO");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    /*public static void setCurrentGame(String content) {
        CtrlDomain.setCurrentGame(content);
    }

    public static String getCurrentGame() {
        return CtrlDomain.getCurrentGame();
    }*/

    /*public static String getCurrentGame() {
        // Returns path of current game
        return ctrlDomain.getCurrentGame();
    }*/


    public void initPresentation() {
        mainMenuView.makeVisible();
        //gameView.makeVisible();
        //gameCreatorView.makeVisible();
    }

    /////Gestion de vistas
    public void mainViewToPlayOptionView() {
        mainMenuView.makeInvisible();
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
        //gameView.startPlay();
        //gameView.makeVisible();
        gameCreatorView.startPlay();
        gameCreatorView.makeVisible();

    }

    public void gameCreatorViewToMainMenuView() {
        gameCreatorView.makeInvisible();
        playOptionView.makeVisible();
        //mainMenuView.makeVisible();
        //ctrlDomain.logoutCurrentUser();
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

    public boolean createKenken(int size, HashSet<Operation> operations, TypeDifficulty diff) {
        return ctrlDomain.generateKenkenByDifficulty(size,operations,diff);
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


    /*public getKenken() {
        return ctrlDomain.getKenken();
    }*/

    /*public boolean checkKenken() {

    }*/

}
