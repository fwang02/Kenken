package Presentation;

import Domain.Controllers.CtrlDomain;

/**
 * @author feiyang.wang
 */
public class CtrlPresentation {

    private CtrlDomain ctrlDomain;
    private static ViewMainMenu viewMainMenu;

    public CtrlPresentation (){
        ctrlDomain = new CtrlDomain();
        //viewMainMenu = new ViewMainMenu();
    }

    /**
     *
     */
    public void initPresentation() {
        viewMainMenu = new ViewMainMenu();
    }

}
