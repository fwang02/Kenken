package Presentation.Views;

import Presentation.CtrlPresentation;
import Presentation.DrawCell;

import javax.swing.*;
import java.awt.*;

public class GameCreatorView extends View {
    private CtrlPresentation ctrlPresentation;

    /*public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        Stroke s = new BasicStroke();
        g2D.set

        g.drawRect(50, 50, 30, 30);
    }*/
    public GameCreatorView(CtrlPresentation cp) {
        // Window
        setBounds(500, 300, 500, 300);
        setResizable(false);
        setLayout(null);
        setTitle("Kenken PROP");

        ctrlPresentation = cp;

        JPanel cell = new JPanel();
        cell.setBackground(Color.WHITE);


        add(cell);

    }

}
