package Presentation.Views;

import Domain.PlayerScore;
import Presentation.CtrlPresentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.PriorityQueue;

/**
 * Esta clase representa un panel de clasificación en la interfaz de usuario.
 * Muestra una tabla con el ranking de los jugadores.
 *
 * @author Feiyang Wang
 */
public class RankingPanel extends JPanel {
    private JTable rankingTable;
    private JScrollPane scrollPane;
    private CtrlPresentation ctrlPresentation;

    /**
     * Constructor de RankingPanel. Inicializa el controlador de presentación, la tabla de ranking y el panel de desplazamiento.
     *
     * @param ctrlPresentation El controlador de presentación.
     */
    public RankingPanel(CtrlPresentation ctrlPresentation) {
        this.ctrlPresentation = ctrlPresentation;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Modelo de tabla que hace que todas las celdas no sean editables
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Añadir columnas al modelo de la tabla
        tableModel.addColumn("Rango");
        tableModel.addColumn("Usuario");
        tableModel.addColumn("Puntos");

        // Obtener los datos de ranking de los jugadores
        PriorityQueue<PlayerScore> rankingData =  new PriorityQueue<>(ctrlPresentation.getRanking());
        // Formatear los datos de ranking como un string
        StringBuilder rankingDataString = new StringBuilder();
        rankingDataString.append("Rango   Usuario  Puntos\n");

        // Rellenar el modelo de la tabla con los datos de ranking
        int rank = 1;
        while (!rankingData.isEmpty()) {
            PlayerScore player = rankingData.poll();
            System.out.println("Rango " + rank + ": " + player.getName() + " con puntuación " + player.getMaxScore());
            tableModel.addRow(new Object[]{rank,player.getName(),player.getMaxScore()});
            rank++;
        }
        rankingTable = new JTable(tableModel);
        scrollPane = new JScrollPane(rankingTable);
        add(scrollPane);
        setVisible(true);
    }
}
