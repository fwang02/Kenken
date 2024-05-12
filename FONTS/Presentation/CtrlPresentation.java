package Presentation;

import Domain.Controllers.CtrlDomain;
import Domain.PlayerScore;
import Presentation.Views.ViewMainMenu;

import java.util.PriorityQueue;

/**
 * @author feiyang.wang
 */
public class CtrlPresentation {

    private CtrlDomain ctrlDomain;
    private static ViewMainMenu viewMainMenu;

    public CtrlPresentation (){
        ctrlDomain = new CtrlDomain();
        viewMainMenu = new ViewMainMenu();
    }

    /**
     *
     */
    public void initPresentation() {
        viewMainMenu.makeVisible();
    }

    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomain.getRanking();
    }

}
