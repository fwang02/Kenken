package Presentation.Views;

import Presentation.CtrlPresentation;
import Presentation.DrawLayout;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Vista del juego de KenKen.
 * Proporciona la interfaz gráfica para interactuar con el juego, incluyendo temporizador, botones y área de juego.
 * Permite comprobar, guardar, recibir pistas y mostrar la solución del juego.
 *
 * @author Romeu Esteve
 */
public class GameView extends View {
    private final CtrlPresentation ctrlPresentation;
    private DrawLayout panel;
    private final JLabel timerLabel;
    private final Timer timer;
    private long startTime;
    private boolean solutionShowed;
    private boolean finished;

    /**
     * Constructor para crear una vista del juego.
     *
     * @param cp controlador de presentación que maneja la lógica del juego
     */
    public GameView(CtrlPresentation cp) {
        // Configuración de la ventana
        setBounds(750, 450, 750, 450);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Asegura que la ventana se pueda cerrar
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        ctrlPresentation = cp;

        JPanel p2 = new JPanel(new BorderLayout());
        add(p2, BorderLayout.EAST);
        p2.setPreferredSize(new Dimension(300, 450));
        JTextArea txt = getjTextArea();
        p2.add(txt, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        p2.add(centerPanel, BorderLayout.CENTER);

        // Etiqueta del temporizador
        timerLabel = new JLabel("Tiempo: 00:00:00");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        p2.add(timerLabel, BorderLayout.CENTER);

        // Panel sur para los botones
        JPanel southPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        p2.add(southPanel, BorderLayout.SOUTH);

        // Crear botones
        JButton submitButton = new JButton("Comprobar");
        JButton saveButton = new JButton("Guardar");
        JButton showSolutionButton = new JButton("Solución");
        JButton hintButton = new JButton("Pista");
        JButton exitButton = new JButton("Salir");

        // Agregar botones al panel sur
        southPanel.add(submitButton);
        southPanel.add(saveButton);
        southPanel.add(showSolutionButton);
        southPanel.add(hintButton);
        southPanel.add(exitButton);

        // Agregar listeners para los botones
        exitButton.addActionListener(e -> onExitButtonClicked());

        submitButton.addActionListener(e -> onSubmitButtonClicked());

        saveButton.addActionListener(e -> onSaveButtonClicked());

        showSolutionButton.addActionListener(e -> onShowSolutionButtonClicked());

        hintButton.addActionListener(e -> onHintButtonClicked());

        // Inicializar y empezar el temporizador
        timer = new Timer(1000, e -> updateTimer());
    }

    private static JTextArea getjTextArea() {
        JTextArea txt = new JTextArea(
                "·Selecciona la casilla con el cursor\n" +
                        "·Utiliza el teclado para insertar números\n" +
                        "·Utiliza 0 o delete para eliminar los números\n" +
                        "\n" +
                        "·Pulsa COMPROBAR para comprobar la respuesta\n" +
                        "·Pulsa PISTA para recibir una pista\n" +
                        "·Pulsa SOLUCIÓN para ver la solución\n"
        );
        txt.setAutoscrolls(true);
        txt.setEditable(false);
        txt.setBackground(null);
        txt.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        return txt;
    }

    /**
     * Maneja el clic en el botón de salir.
     */
    private void onExitButtonClicked() {
        if (finished) {
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

        if (response == JOptionPane.YES_OPTION) {
            onSaveButtonClicked();
        } else {
            remove(panel);
            ctrlPresentation.gameViewToPlayOptionView();
        }
    }

    /**
     * Maneja el clic en el botón de guardar.
     */
    private void onSaveButtonClicked() {
        if (finished) {
            JOptionPane.showMessageDialog(this, "Partida acabada, no se necesita guardar.", "Guardar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String gameName = JOptionPane.showInputDialog(
                null,
                "Nombre de la partida guardada:",
                "Guardar Partida",
                JOptionPane.PLAIN_MESSAGE
        );

        if (!gameName.trim().isEmpty()) {
            int[] values = panel.getValCells();
            if (ctrlPresentation.saveCurrentGame(gameName, values)) {
                JOptionPane.showMessageDialog(this, "Partida guardada correctamente.", "Guardar", JOptionPane.INFORMATION_MESSAGE);
                ctrlPresentation.gameCreatorViewToPlayOptionView();
            } else {
                JOptionPane.showMessageDialog(this, "Se ha producido un error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, rellene el nombre del fichero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Maneja el clic en el botón de comprobar.
     */
    private void onSubmitButtonClicked() {
        int[] values = panel.getValCells();
        if (solutionShowed) {
            JOptionPane.showMessageDialog(this, "Se ha mostrado la solución, no se puede contar puntos.", "Acabado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (finished) {
            JOptionPane.showMessageDialog(this, "La partida está acabada.", "Acabado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (ctrlPresentation.check(values)) {
            int gamePoints = ctrlPresentation.getGamePoints();
            if (gamePoints == -1) {
                JOptionPane.showMessageDialog(this, "Todas las casillas rellenadas son correctas", "Correcto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                timer.stop();
                finished = true;
                if (ctrlPresentation.updateMaxPoint(gamePoints)) {
                    JOptionPane.showMessageDialog(this, "¡Has solucionado todo correctamente!\n Puntos: " + gamePoints + ", puntos max actualizados.", "Acabado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "¡Has solucionado todo correctamente!\n Puntos: " + gamePoints + ", no ha superado a puntos max del usuario.", "Acabado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Algunas casillas no son correctas", "Incorrecta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Maneja el clic en el botón de mostrar solución.
     */
    private void onShowSolutionButtonClicked() {
        if (solutionShowed) return;
        if (finished) {
            JOptionPane.showMessageDialog(this, "Partida acabada.", "Solución", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        showSolution();
        solutionShowed = true;
        finished = true;
        timer.stop();
        JOptionPane.showMessageDialog(this, "Solución mostrada.", "Solución", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Maneja el clic en el botón de pista.
     */
    private void onHintButtonClicked() {
        if (finished) {
            JOptionPane.showMessageDialog(this, "Partida acabada.", "Pista", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int[] values = panel.getValCells();
        int[] hint = ctrlPresentation.hint(values);
        if (hint[0] == 0 && hint[1] == 0 && hint[2] == 0) {
            JOptionPane.showMessageDialog(this, "Prueba a Resolver", "Pista", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Prueba con " + hint[2] + " en la casilla x:" + (hint[1] + 1)
                    + " y:" + (hint[0] + 1), "Pista", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Carga el KenKen desde el juego actual en la capa de dominio.
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
        getLockedCells();
    }

    /**
     * Obtiene el tablero actual del juego.
     */
    private void getBoard() {
        int[] board = ctrlPresentation.getBoard();
        int size = ctrlPresentation.getKenkenSize();

        for (int i = 0; i < board.length; ++i) {
            int x = i / size;
            int y = i % size;
            panel.setCell(x, y, board[i], true);
        }
    }

    /**
     * Inicia el juego.
     */
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

    /**
     * Muestra la solución del juego.
     */
    private void showSolution() {
        ctrlPresentation.solve(); // no hace falta comprobar si es valido porque el usuario solo puede jugar si ya lo es
        int[] cells = ctrlPresentation.getCells();
        int size = ctrlPresentation.getKenkenSize();

        for (int i = 0; i < cells.length; ++i) {
            int x = i / size;
            int y = i % size;
            panel.setCell(x, y, cells[i], false);
        }
    }

    /**
     * Obtiene las celdas que no cambian.
     */
    private void getLockedCells() {
        int[] cells = ctrlPresentation.getLockedCells();
        int size = ctrlPresentation.getKenkenSize();

        for (int i = 0; i < cells.length; ++i) {
            int x = i / size;
            int y = i % size;
            if (cells[i] > 0) panel.setCell(x, y, cells[i], false);
        }
    }

    /**
     * Actualiza el temporizador.
     */
    private void updateTimer() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;

        String timeString = String.format("Tiempo: %02d:%02d:%02d", hours, minutes, seconds);
        timerLabel.setText(timeString);
    }
}
