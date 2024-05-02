package Presentation;

import Domain.CtrlDomain;

/**
 * @author feiyang.wang
 */
public class CtrlPresentation {

    private CtrlDomain ctrlDomain;
    private ViewMainMenu viewMainMenu;

    public CtrlPresentation (){
        ctrlDomain = new CtrlDomain();
        viewMainMenu = new ViewMainMenu();
    }

    /**
     *
     */
    public static void initPresentation() {

    }

}
