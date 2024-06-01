package Presentation;

import javax.swing.*;
import java.awt.*;

public class DrawLayout extends JPanel {
    private static DrawCell[][] cells;
    private int lenght;
    GridLayout grid;

    public DrawLayout(int size, boolean playing) {
        super(new GridLayout(size, size));
        this.lenght = size;
        initLayout(size, playing);
    }

    public void initLayout(int size, boolean playing) {
        reset();
        grid = new GridLayout(size, size);
        cells = new DrawCell[size][size];
        DrawCell.setPlaying(playing);
        DrawCell.setSize(size);

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j] = new DrawCell(i, j);

                add(cells[i][j]);
            }
        }
    }

    public void setCell(int x, int y, int num) {
        cells[x][y].setNumber(num, false);
    }

    public static void setCage(int[] cellsX, int[] cellsY, char op, int res) {
        int l = cellsX.length;
        DrawCell[] selected = new DrawCell[l];
        for (int i = 0; i < l; ++i) {
            selected[i] = cells[cellsX[i]][cellsY[i]];
        }
        DrawCell.selectCells(selected);
        DrawCell.createCage(op, res);
    }

    public Object[] getCage(int index) {
        Object[] r = new Object[4];
        r[0] = DrawCell.getCageCellsX(index);
        r[1] = DrawCell.getCageCellsY(index);
        r[2] = DrawCell.getCageOp(index);
        r[3] = DrawCell.getCageRes(index);
        return r;
    }

    public int getNCages() {
        return DrawCell.getNCages();
    }

    public int getLenght() {
        return lenght;
    }

    public int[] getValCells() {
        
        int s = getLenght();
        int[] values = new int[s*s];

        for(int i = 0; i < s*s; ++i) {
            int x = i / s;
            int y = i % s;
            values[i] = cells[x][y].getNumber();
        }

        return values;
    }

    public void reset() {
        DrawCell.resetDrawCells();
    }
}