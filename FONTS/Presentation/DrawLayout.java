package Presentation;

import javax.swing.*;
import java.awt.*;

public class DrawLayout extends JPanel {
    private int size;
    DrawCell[][] cells;


    public DrawLayout (int size) {
        super(new GridLayout(size, size));

        cells = new DrawCell[size][size];
        //cells[0][0].setSize(size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j] = new DrawCell(i, j);

                //cells[i][j].setNumber(i + j, (i + j) % 2 == 0);
                //cells[i][j].setOp("+", i+j);
                //Color[] border = new Color[4];
                //for (int k = 0; k < 3; ++k) border[k] = (i + j )%2 == 0 ? Color.GRAY : Color.BLACK;
                //cells[i][j].setBorder(new CustomBorder(border[0], 3, border[1], 1, border[2], 1, border[3], 3));

                add(cells[i][j]);
            }
        }
        cells[0][0].setSize(size);

        this.size = size;
    }

}
