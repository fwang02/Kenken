package Presentation.Views;

import Presentation.CtrlPresentation;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class GameView extends View {
    private CtrlPresentation ctrlPresentation;
    private JTextField[][] cells;
    private int size;
    private JPanel table;
    private JPanel sidePanel;


    public GameView(CtrlPresentation cp) {
        ctrlPresentation = cp;
        table = new JPanel();
        sidePanel = new JPanel();
        setLayout(new BorderLayout());
        add(table, BorderLayout.WEST);
        add(sidePanel, BorderLayout.EAST);
    }

    public void startPlay() {
        size = ctrlPresentation.getKenkenSize();
        cells = new JTextField[size][size];
        table.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        //Un filtro para que solo se puede poner valores permitidos
        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[1-" + size + "]") && fb.getDocument().getLength() - length < 1) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j] = new JTextField() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(50, 50);
                    }
                };
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                ((AbstractDocument) cells[i][j].getDocument()).setDocumentFilter(filter);

                if ((i == 0 && j == 0) || (i == 0 && j == 1) || (i == 1 && j == 1)) {
                    cells[i][j].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "3+"));
                } else if ((i == 2 && j == 2) || (i == 2 && j == 3) || (i == 3 && j == 2) || (i == 3 && j == 3)) {
                    cells[i][j].setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "4-"));
                } else {
                    cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
                gbc.gridx = j;
                gbc.gridy = i;
                table.add(cells[i][j], gbc);
            }
        }


        JButton bExit = new JButton("Exit");
        sidePanel.add(bExit);

        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
