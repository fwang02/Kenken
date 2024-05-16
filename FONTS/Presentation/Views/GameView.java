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
        add(table, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
    }

    public void startPlay() {
        size = ctrlPresentation.getKenkenSize();
        cells = new JTextField[size][size];
        table.setLayout(new GridLayout(size,size));

        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[1-" + size + "]") && fb.getDocument().getLength() - length < 1) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                ((AbstractDocument) cells[i][j].getDocument()).setDocumentFilter(filter);
                table.add(cells[i][j]);
            }
        }

        JButton bExit = new JButton();
        sidePanel.add(bExit);

        setBounds(500, 300, 500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
