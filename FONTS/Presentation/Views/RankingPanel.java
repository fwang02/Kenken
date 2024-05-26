package Presentation.Views;

import Domain.PlayerScore;
import Presentation.CtrlPresentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.PriorityQueue;

public class RankingPanel extends JPanel {
    private JTable rankingTable;
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
        setVisible(true);
    }
}
