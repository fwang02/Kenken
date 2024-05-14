package Presentation.Views;

import Presentation.CtrlPresentation;

import javax.swing.*;

public class GameView extends JFrame {
    private CtrlPresentation ctrlPresentation;

    public GameView(CtrlPresentation cp) {
        ctrlPresentation = cp;
    }

    public void makeVisible() {
        setVisible(true);
    }
}
