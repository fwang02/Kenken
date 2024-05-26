package Presentation.Views;

import Presentation.CtrlPresentation;
import Presentation.DrawLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCreatorView extends View {
    private final CtrlPresentation ctrlPresentation;
    final DrawLayout panel;

    final String exampleKenken = "6 15\n" +
            "1 11 2 1 1 2 1\n" +
            "4 2 2 1 2 1 3\n" +
            "3 20 2 1 4 2 4\n" +
            "3 6 4 1 5 1 6 2 6 3 6\n" +
            "2 3 2 2 2 2 3\n" +
            "4 3 2 2 5 3 5\n" +
            "3 240 4 3 1 3 2 4 1 4 2\n" +
            "3 6 2 3 3 3 4\n" +
            "3 6 2 4 3 5 3\n" +
            "1 7 3 4 4 5 4 5 5\n" +
            "3 30 2 4 5 4 6\n" +
            "3 6 2 5 1 5 2\n" +
            "1 9 2 5 6 6 6\n" +
            "1 8 3 6 1 6 2 6 3\n" +
            "4 2 2 6 4 6 5\n" +
            "5 6 3 4 1 2 6 1 4 5 2 3 4 5 2 3 6 1 3 4 1 2 5 6 2 3 6 1 4 5 1 2 5 6 3 4\n";

    public GameCreatorView(CtrlPresentation cp) {
        // Window
        setBounds(0, 0, 500, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the window can be closed
        getContentPane().setLayout(new BorderLayout());

        ctrlPresentation = cp;

        //Draw KenKen
        /*int size = 5; // example
        panel = new DrawLayout(size);*/

        panel = new DrawLayout(exampleKenken);

        add(panel, BorderLayout.CENTER);

        JPanel p2 = new JPanel();
        add(p2, BorderLayout.EAST);
        p2.setPreferredSize(new Dimension(200, 300));
        JTextArea txt = new JTextArea(
                "Select area with mouse\n" +
                        "Use keyboard to input numbers\n" +
                        "Use 0 or backspace to delete numbers\n" +
                        "Use spacebar to add a cage\n" +
                        "Double click on a cage to edit\n\n" +
                        "Kenken Name:"
        );
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

        // Add action listeners for buttons
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPlayButtonClicked();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveButtonClicked(name.getText());
            }
        });
    }

    private void onPlayButtonClicked() {
        // Handle play button click
        System.out.println("Play button clicked");
        // You can add more logic here to start the game or switch to game play view
        //ctrlPresentation.startGame(panel.getKenKenGrid());
    }

    private void onSaveButtonClicked(String gameName) {
        // Handle save button click
        if (gameName == null || gameName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "The game name cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //CtrlPresentation.saveGridToFile(gameName, panel.convertGridToString());

        //System.out.println("Save button clicked with name: " + gameName);
        // You can add more logic here to save the game
        //ctrlPresentation.checkKenken(panel.getCells());
        //ctrlPresentation.saveGame(gameName, panel.getKenKenGrid());
    }
}
