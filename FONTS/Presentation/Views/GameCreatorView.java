package Presentation.Views;

import Presentation.CtrlPresentation;
import Presentation.DrawCell;
import Presentation.DrawLayout;

import javax.swing.*;
import java.awt.*;

public class GameCreatorView extends View {
    private CtrlPresentation ctrlPresentation;
    final DrawLayout panel;

    public GameCreatorView(CtrlPresentation cp) {
        // Window
        setBounds(0, 0, 500, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the window can be closed
        setTitle("Kenken PROP");
        getContentPane().setLayout(new BorderLayout());

        ctrlPresentation = cp;

        //Draw KenKen
        int size = 5; // example
        panel = new DrawLayout(size);

        add(panel, BorderLayout.CENTER);

        JPanel p2 = new JPanel();
        add(p2, BorderLayout.EAST);
        p2.setPreferredSize(new Dimension(200, 300));
        JTextArea txt = new JTextArea(          "Select area with mouse\n" +
                                                "Use keyboard to input numbers\n" +
                                                "Use 0 or backspace to delete numbers\n" +
                                                "Use spacebar to add a cage\n" +
                                                "Double click on a cage to edit\n\n" + "Kenken Name:");
        txt.setAutoscrolls(true);
        txt.setEditable(false);
        txt.setBackground(null);
        txt.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        p2.add(txt);

        JTextField name = new JTextField(10);
        p2.add(name);

        JButton play = new JButton("Play");
        p2.add(play);
        JButton save = new JButton("Save");
        p2.add(save);
    }

}
