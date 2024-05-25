package Presentation;

import Domain.Controllers.CtrlDomain;
import Domain.Controllers.CtrlDomainKenken;
import Domain.PlayerScore;
import Presentation.Views.GameCreatorView;
import Presentation.Views.GameView;
import Presentation.Views.PlayOptionView;
import Presentation.Views.MainMenuView;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 * @author feiyang.wang
 */
public class CtrlPresentation {

    private static CtrlDomain ctrlDomain;
    private MainMenuView mainMenuView;
    private PlayOptionView playOptionView;
    private GameView gameView;

    private GameCreatorView gameCreatorView;


    public CtrlPresentation (){
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
            if (ctrlDomain.importKenkenByFile(savedKenken)) {
                System.out.println("KENKEN VALIDO");
            }
            else {
                System.out.println("KENKEN NO VALIDO");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void initPresentation() {
        mainMenuView.makeVisible();
        //gameView.makeVisible();
        gameCreatorView.makeVisible();
    }

    public void mainViewToPlayOptionView() {
        mainMenuView.makeInvisible();
        playOptionView.makeVisible();
        //gameView.makeVisible();
    }

    public void playOptionViewToGameView() {
        playOptionView.makeInvisible();
        gameView.startPlay();
        gameView.makeVisible();

    }

    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomain.getRanking();
    }

    public boolean openKenkenByFile(File file) {
        return ctrlDomain.importKenkenByFile(file);
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

    /*public getKenken() {
        return ctrlDomain.getKenken();
    }*/

    /*public boolean checkKenken() {

    }*/

}
