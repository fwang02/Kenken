package Presentation.Views;

import Presentation.CtrlPresentation;
import Domain.Controllers.CtrlDomainUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
* Class to define common attributes within views
 */
public class View extends JFrame {
    private final ImageIcon icon = new ImageIcon("./DATA/resource/kenkenicon.png");


    public View() {
        // Set logo in all views
        setIconImage(icon.getImage());
    }

    public void makeVisible() {
        setVisible(true);
    }

    public void makeInvisible() {
        setVisible(false);
    }
}
