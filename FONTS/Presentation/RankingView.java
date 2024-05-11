package Presentation;

import Domain.PlayerScore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.PriorityQueue;

public class RankingView extends JFrame {
    private JTable rankingTable;
    private CtrlPresentation ctrlPresentation;

    public RankingView() {
        // Create a new JFrame to represent the ranking view
        ctrlPresentation = new CtrlPresentation();
        setTitle("Ranking");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        PriorityQueue<PlayerScore> rankingData = ctrlPresentation.getRanking();
        // Format the ranking data as a string
        StringBuilder rankingDataString = new StringBuilder();
        rankingDataString.append("Rank   Username  MaxPoint\n");
        int count = 1;
        for (PlayerScore user : rankingData) {
            tableModel.addRow(new Object[]{count,user.getName(),user.getMaxScore()});
            count++;
        }
        rankingTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(rankingTable);
        add(scrollPane);
        // Make the JFrame visible
        setVisible(true);
    }
}
