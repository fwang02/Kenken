package Presentation.Views;

import Presentation.CtrlPresentation;
import Presentation.DrawLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * @author Romeu Esteve
 */
public class GameView extends View {
    private CtrlPresentation ctrlPresentation;
    private DrawLayout panel;
    private JLabel timerLabel;
    private Timer timer;
    private long startTime;

    public GameView(CtrlPresentation cp) {
        // Window
        setBounds(750, 450, 750, 450);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the window can be closed
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        ctrlPresentation = cp;

        JPanel p2 = new JPanel(new BorderLayout());
        add(p2, BorderLayout.EAST);
        p2.setPreferredSize(new Dimension(200, 300));
        JTextArea txt = new JTextArea(
                "Select area with mouse\n" +
                        "Use keyboard to input numbers\n" +
                        "Use 0 or backspace to delete numbers\n"
        );
        txt.setAutoscrolls(true);
        txt.setEditable(false);
        txt.setBackground(null);
        txt.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        p2.add(txt, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        p2.add(centerPanel, BorderLayout.CENTER);

        // Timer label
        timerLabel = new JLabel("Time: 00:00:00");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        p2.add(timerLabel, BorderLayout.CENTER);

        // South panel to hold the buttons
        JPanel southPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        p2.add(southPanel, BorderLayout.SOUTH);

        // Create buttons
        JButton submitButton = new JButton("Submit");
        JButton saveButton = new JButton("Save");
        JButton showSolutionButton = new JButton("Solve");
        JButton hintButton = new JButton("Hint");
        JButton exitButton = new JButton("Exit");

        // Add buttons to the south panel
        southPanel.add(submitButton);
        southPanel.add(saveButton);
        southPanel.add(showSolutionButton);
        southPanel.add(hintButton);
        southPanel.add(exitButton);

        // Add action listeners for buttons
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExitButtonClicked();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSubmitButtonClicked();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveButtonClicked();
            }
        });

        showSolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onShowSolutionButtonClicked();
            }
        });

        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onHintButtonClicked();
            }
        });

        // Initialize and start the timer
        startTime = System.currentTimeMillis();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timer.start();
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
        if (response == JOptionPane.YES_OPTION) {
            // User chose to save, show save dialog and handle saving
            String gameName = JOptionPane.showInputDialog(
                    this,
                    "Enter the game name to save:",
                    "Save Game",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (gameName != null && !gameName.trim().isEmpty()) {
                if (CtrlPresentation.isValid()) {
                    // CtrlPresentation.saveGridToFile(gameName, panel.convertGridToString());
                    JOptionPane.showMessageDialog(this, "Game saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
                    ctrlPresentation.gameViewToPlayOptionView();
                } else {
                    JOptionPane.showMessageDialog(this, "The game is not valid and cannot be saved.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (gameName != null) {
                JOptionPane.showMessageDialog(this, "The game name cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            remove(panel);
            ctrlPresentation.gameViewToPlayOptionView();
        }
    }

    private void onSaveButtonClicked() {
        // Handle save button click
        String gameName = JOptionPane.showInputDialog(
                this,
                "Enter the game name to save:",
                "Save Game",
                JOptionPane.PLAIN_MESSAGE
        );

        if (gameName != null && !gameName.trim().isEmpty()) {
            if (CtrlPresentation.isValid()) {
                // CtrlPresentation.saveGridToFile(gameName, panel.convertGridToString());
                JOptionPane.showMessageDialog(this, "Game saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "The game is not valid and cannot be saved.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (gameName != null) {
            JOptionPane.showMessageDialog(this, "The game name cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSubmitButtonClicked() {
        // Handle submit button click
        if (/*ctrlPresentation.()*/true) {
            JOptionPane.showMessageDialog(this, "Congratulations! The solution is correct.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "The solution is incorrect. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onShowSolutionButtonClicked() {
        // Handle show solution button click
        showSolution();
        JOptionPane.showMessageDialog(this, "Solution displayed.", "Solution", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onHintButtonClicked() {
        // Handle hint button click
        //ctrlPresentation.showHint();
        JOptionPane.showMessageDialog(this, "Hint displayed.", "Hint", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Load Kenken from currentGame in Domain layer
     */
    private void loadKenken() {
        int ncages = ctrlPresentation.getNCages();
        for (int i = 0; i < ncages; ++i) { // i = index of cages of currentGame
            int[] cellsX = ctrlPresentation.getCageCellsX(i);
            int[] cellsY = ctrlPresentation.getCageCellsY(i);
            char op = ctrlPresentation.getCageOp(i);
            int res = ctrlPresentation.getCageRes(i);

            DrawLayout.setCage(cellsX, cellsY, op, res);
        }
    }

    public void startPlay() {
        System.out.println("ENTERS StartPlay");
        int size = ctrlPresentation.getKenkenSize();
        panel = new DrawLayout(size, true);
        loadKenken();
        add(panel, BorderLayout.CENTER);
    }

    private void showSolution() {
        int[] cells = ctrlPresentation.getSolutionCells();
        int size = ctrlPresentation.getKenkenSize();

        for (int i = 0; i < cells.length; ++i) {
            int x = i / size;
            int y = i % size;
            panel.setCell(x, y, cells[i]);
        }
    }

    private void updateTimer() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;

        String timeString = String.format("Time: %02d:%02d:%02d", hours, minutes, seconds);
        timerLabel.setText(timeString);
    }
}
