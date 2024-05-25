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

    public GameCreatorView(CtrlPresentation cp) {
        // Window
        setBounds(0, 0, 500, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the window can be closed
        getContentPane().setLayout(new BorderLayout());

        ctrlPresentation = cp;

        //Draw KenKen
        int size = 5; // example
        panel = new DrawLayout(size);

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

        CtrlPresentation.saveGridToFile(gameName, panel.convertGridToString());

        //System.out.println("Save button clicked with name: " + gameName);
        // You can add more logic here to save the game
        //ctrlPresentation.checkKenken(panel.getCells());
        //ctrlPresentation.saveGame(gameName, panel.getKenKenGrid());
    }
}
