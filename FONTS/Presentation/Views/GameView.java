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
    private boolean solutionShowed;
    private boolean finished;

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
        p2.setPreferredSize(new Dimension(300, 450));
        JTextArea txt = new JTextArea(
                "·Selecciona la casilla con el cursor\n" +
                "·Utiliza el teclado para insertar numeros\n" +
                "·Utiliza 0 o delete para eliminar los numeros\n" +
                "\n" +
                "·Pulsa COMPROBAR para comprobar la respuesta\n" +
                "·Pulsa PISTA para recibir una pista\n" +
                "·Pulsa SOLUCIÓN para ver la solución\n"
        );
        txt.setAutoscrolls(true);
        txt.setEditable(false);
        txt.setBackground(null);
        txt.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        p2.add(txt, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        p2.add(centerPanel, BorderLayout.CENTER);

        // Timer label
        timerLabel = new JLabel("Tiempo: 00:00:00");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        p2.add(timerLabel, BorderLayout.CENTER);

        // South panel to hold the buttons
        JPanel southPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        p2.add(southPanel, BorderLayout.SOUTH);

        // Create buttons
        JButton submitButton = new JButton("Comprobar");
        JButton saveButton = new JButton("Guardar");
        JButton showSolutionButton = new JButton("Solución");
        JButton hintButton = new JButton("Pista");
        JButton exitButton = new JButton("Salir");

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
        timer = new Timer(1000, e -> updateTimer());

    }

    private void onExitButtonClicked() {
        // Handle exit button click
        if(finished){
            ctrlPresentation.gameViewToPlayOptionView();
            return;
        }

        int response = JOptionPane.showConfirmDialog(
                this,
                "¿Quieres GUARDAR antes de salir?",
                "Salir",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Handle the user's response
        if (response == JOptionPane.YES_OPTION) {
            // User chose to save, show save dialog and handle saving
            String gameName = JOptionPane.showInputDialog(
                    this,
                    "Nombre de la partida guardada:",
                    "Guardar Partida",
                    JOptionPane.PLAIN_MESSAGE
            );
        } else {
            remove(panel);
            ctrlPresentation.gameViewToPlayOptionView();
        }
    }

    private void onSaveButtonClicked() {
        if(finished) {
            JOptionPane.showMessageDialog(this, "Partida acabada, no se necesita guardar.", "Guardar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Handle save button click
        String gameName = JOptionPane.showInputDialog(
            null,
            "Nombre de la partida guardada:",
            "Guardar Partida",
            JOptionPane.PLAIN_MESSAGE
        );

        if (!gameName.trim().isEmpty()) {
            int[] values = panel.getValCells();
            if (ctrlPresentation.saveCurrentGame(gameName, values)){
                JOptionPane.showMessageDialog(this, "Partida acabada.", "Guardar", JOptionPane.INFORMATION_MESSAGE);
                ctrlPresentation.gameCreatorViewToPlayOptionView();
            }
            else {
                JOptionPane.showMessageDialog(this, "Se ha producido un error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, rellene el nombre del fichero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void onSubmitButtonClicked() {
        // Handle submit button click
        int[] values = panel.getValCells();
        if(solutionShowed) {
            JOptionPane.showMessageDialog(this, "Se ha mostrado la solución, no se puede contar puntos.", "Acabado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(finished) {
            JOptionPane.showMessageDialog(this, "La partida está acabada.", "Acabado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (ctrlPresentation.check(values)) {
            int gamePoints = ctrlPresentation.getGamePoints();
            if(gamePoints == -1) JOptionPane.showMessageDialog(this, "Todas las casillas rellenadas son correctas", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            else {
                if(ctrlPresentation.updateMaxPoint(gamePoints)) {
                    JOptionPane.showMessageDialog(this, "¡Has solucionado todo correctamente!\n Puntos: " + gamePoints + ", puntos max actualizados.", "Acabado", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(this, "¡Has solucionado todo correctamente!\n Puntos: " + gamePoints + ", no ha superado a puntos max del usuario.", "Acabado", JOptionPane.INFORMATION_MESSAGE);
                }
                finished = true;
                timer.stop();

            }
        } else {
            JOptionPane.showMessageDialog(this, "Algunas casillas no son correctas", "Incorrecta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onShowSolutionButtonClicked() {
        if(solutionShowed) return;
        if(finished) {
            JOptionPane.showMessageDialog(this, "Partida acabada.", "Solución", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Handle show solution button click
        showSolution();
        JOptionPane.showMessageDialog(this, "Solución mostrada.", "Solución", JOptionPane.INFORMATION_MESSAGE);
        solutionShowed = true;
        finished = true;
        timer.stop();
    }

    private void onHintButtonClicked() {
        if(finished) {
            JOptionPane.showMessageDialog(this, "Partida acabada.", "Pista", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Handle hint button click
        int[] values = panel.getValCells();
        int[] hint = ctrlPresentation.hint(values);
        if(hint[0] == 0 && hint[1] == 0 && hint[2] == 0) {
            JOptionPane.showMessageDialog(this, "Prueba a Resolver", "Pista", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "Prueba con " + hint[2] + " en la casilla x:" + (hint[1]+1)
            + " y:" + (hint[0]+1), "Pista", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Load Kenken from currentGame in Domain layer
     */
    private void loadKenken() {
        int ncages = ctrlPresentation.getNCages();
        for (int i = 0; i < ncages; ++i) { 
            int[] cellsX = ctrlPresentation.getCageCellsX(i);
            int[] cellsY = ctrlPresentation.getCageCellsY(i);
            char op = ctrlPresentation.getCageOp(i);
            int res = ctrlPresentation.getCageRes(i);

            DrawLayout.setCage(cellsX, cellsY, op, res);
        }
        getBoard();
    }

    private void getBoard() {
        int[] board = ctrlPresentation.getBoard();
        int size = ctrlPresentation.getKenkenSize();

        for (int i = 0; i < board.length; ++i) {
            int x = i / size;
            int y = i % size;
            panel.setCell(x, y, board[i]);
        }
    }

    public void startPlay() {
        if (panel != null) {
            panel.reset();
            remove(panel);
        }

        startTime = System.currentTimeMillis();
        timer.start();
        solutionShowed = false;
        finished = false;

        int size = ctrlPresentation.getKenkenSize();
        panel = new DrawLayout(size, true);
        loadKenken();
        add(panel, BorderLayout.CENTER);
    }

    private void showSolution() {
        int[] cells = ctrlPresentation.getCells();
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

        String timeString = String.format("Tiempo: %02d:%02d:%02d", hours, minutes, seconds);
        timerLabel.setText(timeString);
    }

    private boolean loadToCurrentGame() {
        ctrlPresentation.initCurrentGame(panel.getLenght());
        for (int i = 0; i < panel.getNCages(); ++i) {
            Object[] cage = panel.getCage(i);
            ctrlPresentation.setCage((int[]) cage[0], (int[]) cage[1], (int) cage[2], (int) cage[3]);
        }
        return ctrlPresentation.solve();
    }
}
