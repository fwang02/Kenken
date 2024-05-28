package Presentation;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DrawLayout extends JPanel {
    private static DrawCell[][] cells;
    GridLayout grid;

    // Constructora para panel editable
    public DrawLayout(int size, boolean playing) {
        super(new GridLayout(size, size));

        initLayout(size, playing);

    }

    public void initLayout(int size, boolean playing) {
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

    public String convertGridToString() {
        System.out.print(DrawCell.convertGridToString());
        return DrawCell.convertGridToString();
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
}
