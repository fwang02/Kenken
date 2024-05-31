package Presentation.Views;

import Presentation.CtrlPresentation;
import Presentation.DrawLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCreatorView extends View {
    private final CtrlPresentation ctrlPresentation;
    DrawLayout panel;


    public GameCreatorView(CtrlPresentation cp) {
        // Window
        getContentPane().setLayout(new BorderLayout());

        ctrlPresentation = cp;

        JPanel p2 = new JPanel(new BorderLayout());
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
        p2.add(txt, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        p2.add(centerPanel, BorderLayout.CENTER);

        JTextField name = new JTextField(10);
        centerPanel.add(name);

        JButton play = new JButton("Play");
        centerPanel.add(play);
        JButton save = new JButton("Save");
        centerPanel.add(save);

        JButton exit = new JButton("Exit");
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(exit);
        p2.add(southPanel, BorderLayout.SOUTH);

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

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExitButtonClicked();
            }
        });
    }

    private void onPlayButtonClicked() {
        // Handle play button click
        System.out.println("Play button clicked");
        // You can add more logic here to start the game or switch to game play view

        loadToCurrentGame();
        ctrlPresentation.gameCreatorViewToGameView();
    }

    private void onSaveButtonClicked(String gameName) {
        // Handle save button click
        if (gameName == null || gameName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "The game name cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Checks it's valid
        if (CtrlPresentation.isValid());
    }

    private void onExitButtonClicked() {
        // Handle exit button click
        int response = JOptionPane.showConfirmDialog(
                this,
                "Do you want to save before exiting?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Handle the user's response
        if (response == JOptionPane.OK_OPTION) {
            // User chose to save, show save dialog and handle saving
            String gameName = JOptionPane.showInputDialog(
                    this,
                    "Enter the game name to save:",
                    "Save Game",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (gameName != null && !gameName.trim().isEmpty()) {
                if (CtrlPresentation.isValid()) {
                    //CtrlPresentation.saveGridToFile(gameName, panel.convertGridToString());
                    JOptionPane.showMessageDialog(this, "Game saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
                    ctrlPresentation.gameCreatorViewToPlayOptionView();
                } else {
                    JOptionPane.showMessageDialog(this, "The game is not valid and cannot be saved.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (gameName != null) {
                JOptionPane.showMessageDialog(this, "The game name cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            remove(panel);
            ctrlPresentation.gameCreatorViewToPlayOptionView();
        }

    }

    private int setGridSize() {
        // Create a JLabel
        JLabel label = new JLabel("Choose grid size:");

        // Create a SpinnerNumberModel with the grid sizes
        SpinnerNumberModel model = new SpinnerNumberModel(3, 3, 9, 1);
        JSpinner spinner = new JSpinner(model);

        // Create a JPanel and add the label and spinner to it
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(label);
        panel.add(spinner);

        // Create options for JOptionPane
        Object[] options = {"OK", "Cancel"};

        // Show the JOptionPane
        int option = JOptionPane.showOptionDialog(
                null,
                panel,
                "Grid Size Chooser",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        // Handle the user's choice
        if (option == JOptionPane.OK_OPTION) {
            // Return the selected grid size
            System.out.println("GRID " + (int) spinner.getValue());
            return (int) spinner.getValue();
        } else {
            System.out.println("User cancelled the dialog.");
            remove(panel);
            return 0;
        }
    }

    public void initGameCreator(int gridSize) {
        panel = new DrawLayout(gridSize, false);
        add(panel);

        ctrlPresentation.initCurrentGame(gridSize);
    }

    private void loadToCurrentGame() {
        for (int i = 0; i < panel.getNCages(); ++i) {
            Object[] cage = panel.getCage(i);
            ctrlPresentation.setCage((int[]) cage[0], (int[]) cage[1], (int) cage[2], (int) cage[3]);
        }
    }
}