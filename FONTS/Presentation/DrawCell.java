package Presentation;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * This class represents a cell on the Kenken
 *
 * @author Romeu Esteve
 */
public class DrawCell extends JPanel {
    private static boolean leftClickHeld = false;
    private boolean selected = false;
    private static List<DrawCell> allCells = new ArrayList<DrawCell>();

    private static int size;
    private int x;      // X position in game.
    private int y;      // Y position in game.
    private JLabel mainLabel;
    private JLabel smallLabel;



    /**
     * Constructs the label and sets x and y positions in game
     *
     * @param x     X position in game
     * @param y     Y position in game
     */
    public DrawCell(int x, int y) {
        this.x = x;
        this.y = y;
        allCells.add(this);

        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        mainLabel = new JLabel("", JLabel.CENTER);
        mainLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        mainLabel.setOpaque(false);

        smallLabel = new JLabel("");
        smallLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 8));
        smallLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(smallLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(2, 2, 0, 0);
        add(mainLabel, gbc);

        //setBorder(new CustomBorder(Color.GRAY, 1, Color.GRAY, 1, Color.GRAY, 1, Color.GRAY, 1));

        // Add mouse listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected && !leftClickHeld) {
                    setBackground(Color.LIGHT_GRAY);
                } else if (leftClickHeld) {
                    setBackground(Color.YELLOW);
                    selected = true;
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected && !leftClickHeld) setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                resetYellowCells();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    setBackground(Color.YELLOW);
                    selected = true;
                    leftClickHeld = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //setBackground(Color.WHITE); // Reset color when mouse is released
                //setLeftClickHeld(false);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    leftClickHeld = false;
                }
            }
        });

        // Add key listener to handle number input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                int keyCode = e.getKeyCode();
                if (Character.isDigit(keyChar) && keyChar - '0' <= size) {
                    setNumberInAllYellowCells(Character.getNumericValue(keyChar));
                }
                else if (keyChar == '\b') { //Backspace
                    setNumberInAllYellowCells(0); // set to blank
                }
                else if (keyChar == ' ') { //Enter
                    setCage();
                }

            }
        });

        setFocusable(true);
    }

    public static void resetYellowCells() {
        for (DrawCell c : allCells) {
            if (c.selected) {
                c.setBackground(Color.WHITE);
                c.selected = false;
            }
        }
    }

    /**
     * Sets number and foreground color according to userInput
     *
     * @param number        Number to be set
     * @param userInput     Boolean indicating number is user input or not
     */
    public void setNumber(int number, boolean userInput) {
        mainLabel.setForeground(userInput ? Color.BLUE : Color.BLACK);
        mainLabel.setText(number > 0 ? number + "" : "");
    }

    public void setOp(String op, int res) {
        smallLabel.setText(op + res);
    }

    /**
     * Sets the given number in all yellow cells.
     *
     * @param number Number to set in all yellow cells.
     */
    public static void setNumberInAllYellowCells(int number) {
        for (DrawCell cell : allCells) {
            if (cell.selected) {
                cell.mainLabel.setText(number > 0 ? number + "" : "");
            }
        }
    }

    /**
     * Sets the borders of selected cells to create a cage.
     */
    public static void setCage() {
        System.out.println("SET CAGE");

        DrawCell opCell = findTopLeftCell();

        for (DrawCell cell : allCells) {
            if (cell.selected) {
                if (cell == opCell) {
                    cell.smallLabel.setText("+0");
                }
                else {
                    cell.smallLabel.setText("");
                }
                boolean top = true;
                boolean bottom = true;
                boolean left = true;
                boolean right = true;

                for (DrawCell neighbor : allCells) {
                    if (neighbor.selected) {
                        if (neighbor.x == cell.x && neighbor.y == cell.y - 1) {
                            left = false;
                        }
                        if (neighbor.x == cell.x && neighbor.y == cell.y + 1) {
                            right = false;
                        }
                        if (neighbor.x == cell.x - 1 && neighbor.y == cell.y) {
                            top = false;
                        }
                        if (neighbor.x == cell.x + 1 && neighbor.y == cell.y) {
                            bottom = false;
                        }
                    }
                }

                CustomBorder border = new CustomBorder( top ? Color.BLACK : Color.LIGHT_GRAY, top ? 3 : 0,
                                                        left ? Color.BLACK : Color.LIGHT_GRAY, left ? 3 : 0,
                                                        bottom ? Color.BLACK : Color.LIGHT_GRAY, bottom ? 3 : 0,
                                                        right ? Color.BLACK : Color.LIGHT_GRAY, right ? 3 : 0);
                cell.setBorder(border);
            }
        }
    }

    private static DrawCell findTopLeftCell() {
        DrawCell topLeftCell = null;

        for (DrawCell cell : allCells) {
            if (cell.selected) {
                if (topLeftCell == null) {
                    topLeftCell = cell;
                } else {
                    if (cell.x < topLeftCell.x || (cell.x == topLeftCell.x && cell.y < topLeftCell.y)) {
                        topLeftCell = cell;
                    }
                }
            }
        }

        return topLeftCell;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns x position in game
     *
     * @return  X position in game
     */
    public int getPosX() {
        return x;
    }

    /**
     * Return y position in game
     *
     * @return  Y position in game
     */
    public int getPosY() {
        return y;
    }
}
