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
    private DrawCell[][] cells;

    // Constructora para panel editable
    public DrawLayout(int size) {
        super(new GridLayout(size, size));

        cells = new DrawCell[size][size];
        DrawCell.setPlaying(false);

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j] = new DrawCell(i, j);

                add(cells[i][j]);
            }
        }
        cells[0][0].setSize(size);
    }

    // Constructora para panel jugable
    public DrawLayout(String kenken) {
        super();

        //MOVE THIS TO ANOTHER CLASS//////////////////////
        /*StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String kenken = content.toString();*/
        //////////////////////////////////////////////////

        initializeFromContent(kenken);
    }

    private void initializeFromContent(String content) {
        String[] lines = content.split("\n");
        String[] firstLine = lines[0].split(" ");
        int size = Integer.parseInt(firstLine[0]);
        int regionCount = Integer.parseInt(firstLine[1]);

        setLayout(new GridLayout(size, size));
        cells = new DrawCell[size][size];
        DrawCell.setSize(size);
        DrawCell.setPlaying(true);

        // Initialize the grid
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j] = new DrawCell(i, j);
                add(cells[i][j]);
            }
        }

        // Parse each region line
        for (int i = 1; i <= regionCount; i++) {
            String[] parts = lines[i].split(" ");
            int op = Integer.parseInt(parts[0]);
            int res = Integer.parseInt(parts[1]);
            int e = Integer.parseInt(parts[2]);

            int index = 3;
            DrawCell[] cageCells = new DrawCell[e];

            for (int j = 0; j < e; j++) {
                int x = Integer.parseInt(parts[index]) - 1;
                int y = Integer.parseInt(parts[index + 1]) - 1;
                index += 2;

                int number = 0;
                if (index < parts.length && parts[index].startsWith("[")) {
                    number = Integer.parseInt(parts[index].substring(1, parts[index].length() - 1));
                    index++;
                }

                cells[x][y].setNumber(number, false);
                cageCells[j] = cells[x][y];
            }

            // Additional operations like setting the operation and result can be done here if needed
            // For example, you can set the operation and result to the DrawCells
            /*for (DrawCell cell : cageCells) {
                cell.setOperation(op, res);
            }*/
            DrawCell.selectCells(cageCells);
            DrawCell.createCage(op, res);
        }
    }

    public String convertGridToString() {
        System.out.print(DrawCell.convertGridToString());
        return DrawCell.convertGridToString();
    }

    public void setPlaying(boolean b) {
        DrawCell.setPlaying(b);
    }
}
