package Presentation.Views;

import Domain.PlayerScore;
import Presentation.CtrlPresentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.PriorityQueue;

public class RankingPanel extends JPanel {
    private JTable rankingTable;
    private JScrollPane scrollPane;
    private CtrlPresentation ctrlPresentation;

    public RankingPanel(CtrlPresentation ctrlPresentation) {
        this.ctrlPresentation = ctrlPresentation;
        //setPreferredSize(new Dimension(500, 500));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        tableModel.addColumn("Rank");
        tableModel.addColumn("Username");
        tableModel.addColumn("Max point");

        PriorityQueue<PlayerScore> rankingData =  new PriorityQueue<>(ctrlPresentation.getRanking());
        // Format the ranking data as a string
        StringBuilder rankingDataString = new StringBuilder();
        rankingDataString.append("Rank   Username  MaxPoint\n");

        int rank = 1;
        while (!rankingData.isEmpty()) {
            PlayerScore player = rankingData.poll();
            System.out.println("Rank " + rank + ": " + player.getName() + " with score " + player.getMaxScore());
            tableModel.addRow(new Object[]{rank,player.getName(),player.getMaxScore()});
            rank++;
        }
        rankingTable = new JTable(tableModel);
        scrollPane = new JScrollPane(rankingTable);
        add(scrollPane);
        setVisible(true);
    }
}
