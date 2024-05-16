package Presentation;

import Domain.Controllers.CtrlDomain;
import Domain.PlayerScore;
import Presentation.Views.GameCreatorView;
import Presentation.Views.GameView;
import Presentation.Views.PlayOptionView;
import Presentation.Views.MainMenuView;

import java.io.File;
import java.util.PriorityQueue;

/**
 * @author feiyang.wang
 */
public class CtrlPresentation {

    private CtrlDomain ctrlDomain;
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

    public void initPresentation() {
        mainMenuView.makeVisible();
        //gameView.makeVisible();
        //gameCreatorView.makeVisible();
    }

    public void mainViewToPlayOptionView() {
        mainMenuView.makeInvisible();
        playOptionView.makeVisible();
        //gameView.makeVisible();
    }

    public void playOptionViewToGameView() {
        playOptionView.makeInvisible();
        gameView.makeVisible();

    }

    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomain.getRanking();
    }

    public boolean openKenkenByFile(File file) {
        return ctrlDomain.importKenkenByFile(file);
    }
}
