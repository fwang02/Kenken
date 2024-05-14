package Presentation;

import Domain.Controllers.CtrlDomain;
import Domain.PlayerScore;
import Presentation.Views.GameView;
import Presentation.Views.PlayOptionView;
import Presentation.Views.ViewMainMenu;

import java.io.File;
import java.util.PriorityQueue;

/**
 * @author feiyang.wang
 */
public class CtrlPresentation {

    private CtrlDomain ctrlDomain;
    private ViewMainMenu viewMainMenu;
    private PlayOptionView playOptionView;
    private GameView gameView;


    public CtrlPresentation (){
        ctrlDomain = new CtrlDomain();
        viewMainMenu = new ViewMainMenu(this);
        playOptionView = new PlayOptionView(this);
        gameView = new GameView(this);
    }

    public void initPresentation() {
        viewMainMenu.makeVisible();
    }

    public void mainViewToPlayOptionView() {
        viewMainMenu.makeInvisible();
        playOptionView.makeVisible();
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
