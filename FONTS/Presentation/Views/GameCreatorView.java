package Presentation.Views;

import Presentation.CtrlPresentation;
import Presentation.DrawLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Clase para la vista de creación de juego.
 * Permite al usuario crear un nuevo juego de KenKen.
 * @author Romeu Esteve
 */
public class GameCreatorView extends View {
    private final CtrlPresentation ctrlPresentation;
    DrawLayout panel;

    /**
     * Construye una nueva instancia de GameCreatorView.
     * @param cp El controlador de presentación.
     */
    public GameCreatorView(CtrlPresentation cp) {
        // Ventana
        setBounds(750, 450, 750, 450);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        ctrlPresentation = cp;

        JPanel p2 = new JPanel(new BorderLayout());
        add(p2, BorderLayout.EAST);
        p2.setPreferredSize(new Dimension(300, 450));
        JTextArea txt = new JTextArea(
                "·Selecciona las casillas con el ratón\n" +
                        "·Pulsa 0 o delete para borrar números\n" +
                        "·Pulsa espacio para añadir una región con las casillas seleccionadas\n" +
                        "·Haz doble clic en una región para editarla\n"
        );
        txt.setAutoscrolls(true);
        txt.setEditable(false);
        txt.setBackground(null);
        txt.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
        p2.add(txt, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        p2.add(centerPanel, BorderLayout.CENTER);

        JButton play = new JButton("Jugar");
        centerPanel.add(play);
        JButton save = new JButton("Guardar");
        centerPanel.add(save);

        JButton exit = new JButton("Salir");
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(exit);
        p2.add(southPanel, BorderLayout.SOUTH);

        // Agrega action listeners para los botones
        play.addActionListener(e -> onPlayButtonClicked());

        save.addActionListener(e -> onSaveButtonClicked());

        exit.addActionListener(e -> onExitButtonClicked());
    }

    /**
     * Controla los eventos del botón "Jugar"
     */
    private void onPlayButtonClicked() {
        // Maneja el clic en el botón de jugar
        if (loadToCurrentGame()) {
            ctrlPresentation.gameCreatorViewToGameView();
        }
        else {
            JOptionPane.showMessageDialog(null, "El KenKen no tiene solución","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Controla los eventos del botón "Guardar"
     */
    private void onSaveButtonClicked() {
        // Maneja el clic en el botón de guardar
        if (loadToCurrentGame()) {
            String gameName = JOptionPane.showInputDialog(
                    null,
                    "Nombre de la partida guardada:",
                    "Guardar Partida",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (!gameName.trim().isEmpty()) {
                int[] values = new int[panel.getLength()*panel.getLength()];
                if (ctrlPresentation.saveCurrentGame(gameName, values)) {
                    JOptionPane.showMessageDialog(this, "Juego guardado.", "Guardar", JOptionPane.INFORMATION_MESSAGE);
                    ctrlPresentation.gameCreatorViewToPlayOptionView();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Se ha producido un error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, rellene el nombre del fichero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "El KenKen no tiene solución", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Controla los eventos del botón "Salir"
     */
    private void onExitButtonClicked() {
        // Maneja el clic en el botón de salir
        int response = JOptionPane.showConfirmDialog(
                this,
                "¿Quieres GUARDAR antes de salir?",
                "Salir",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Maneja la respuesta del usuario
        if (response == JOptionPane.OK_OPTION) {
            // El usuario eligió guardar
            onSaveButtonClicked();
        }
        else {
            remove(panel);
            ctrlPresentation.gameCreatorViewToPlayOptionView();
        }
    }

    /**
     * Inicializa la vista del creador de juego con un tamaño de rejilla dado.
     * @param gridSize El tamaño de la rejilla del juego.
     */
    public void initGameCreator(int gridSize) {
        if (panel != null) remove(panel);

        panel = new DrawLayout(gridSize, false);
        add(panel);

        ctrlPresentation.initCurrentGame(gridSize);
    }

    /**
     * Carga como CurrentGame el estado en que se encuentra en el DrawLayout
     * @return true si se ha cargado correctamente, false de lo contrario.
     */
    private boolean loadToCurrentGame() {
        ctrlPresentation.initCurrentGame(panel.getLength());
        for (int i = 0; i < panel.getNCages(); ++i) {
            Object[] cage = panel.getCage(i);
            ctrlPresentation.setCage((int[]) cage[0], (int[]) cage[1], (int) cage[2], (int) cage[3]);
        }
        ctrlPresentation.setLockedCells(panel.getValCells());
        return ctrlPresentation.solve();
    }
}