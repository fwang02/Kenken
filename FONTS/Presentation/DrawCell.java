package Presentation;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * This class represents a cell on the Kenken
 *
 * @author Romeu Esteve
 */
public class DrawCell extends JPanel {
    private static boolean leftClickHeld = false;

    private static boolean playing;
    private boolean selected = false;
    private static final List<DrawCell> allCells = new ArrayList<>();

    private static int size;
    //private static int cages = 0;
    private final int x;      // X position in game.
    private final int y;      // Y position in game.
    private final JLabel mainLabel;
    private final JLabel smallLabel;
    private Cage cage;



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

                    if (!playing && hasCage() && e.getClickCount() == 2) {
                        leftClickHeld = false;
                        // select all cells in cage
                        for (DrawCell c : cage.getCells()) {
                            c.setBackground(Color.YELLOW);
                            c.selected = true;
                        }
                        cage.cageConfig();
                    }
                }

                // If the user is in another field and clicks the editor regains focus
                requestFocus();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
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
                if (Character.isDigit(keyChar) && keyChar - '0' <= size) {
                    setNumberInAllYellowCells(Character.getNumericValue(keyChar));
                }
                else if (keyChar == '\b') { //Backspace
                    setNumberInAllYellowCells(0); // set to blank
                }
                else if (!playing && keyChar == ' ') { //Spacebar
                    createCage(' ', 0);
                }

            }
        });

        setFocusable(true);
    }

    private static void resetYellowCells() {
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

    public void setOp(String op) {
        smallLabel.setText(op);
    }

    /**
     * Sets the given number in all yellow cells.
     *
     * @param number Number to set in all yellow cells.
     */
    public static void setNumberInAllYellowCells(int number) {
        for (DrawCell cell : allCells) {
            if (cell.selected) {
                // Skip black cells if in playing mode
                if (playing && cell.mainLabel.getForeground() == Color.BLACK && cell.mainLabel.getText() != "") continue;
                cell.setNumber(number, playing);
            }
        }
    }


    public void setCage(Cage c) {
        cage = c;
    }
    /**
     * Sets the borders of selected cells to create a cage.
     */
    public static void createCage(char op, int res) {
        // Check it's valid
        for (DrawCell cell : allCells) {

            if (cell.selected) {
                if (cell.hasCage()) {
                    JOptionPane.showMessageDialog(null, "Cell already in a cage.");
                    return;
                }
            }
        }
        //DFS to check the cells are connected
        if (!areYellowCellsConnected()) {
            JOptionPane.showMessageDialog(null, "Cells are not connected.");
            return;
        }
        // Draw cage
        Cage cage = new Cage();
        cage.setOpCell(findTopLeftCell());

        for (DrawCell cell : allCells) {
            if (cell.selected) {
                cage.addCell(cell);

                CustomBorder border = getCustomBorder(cell);
                cell.setBorder(border);
            }
        }
        if (op == ' ') cage.cageConfig();
        else {
            cage.setCage(op, res);
        }
    }

    private static CustomBorder getCustomBorder(DrawCell cell) {
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

        return new CustomBorder( top ? Color.BLACK : Color.LIGHT_GRAY, top ? 3 : 0,
                                                left ? Color.BLACK : Color.LIGHT_GRAY, left ? 3 : 0,
                                                bottom ? Color.BLACK : Color.LIGHT_GRAY, bottom ? 3 : 0,
                                                right ? Color.BLACK : Color.LIGHT_GRAY, right ? 3 : 0);
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

    public static void setSize(int s) {
        size = s;
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

    private static boolean areYellowCellsConnected() {
        Set<DrawCell> visited = new HashSet<>();
        DrawCell startCell = null;

        // Find the starting cell (any yellow cell)
        for (DrawCell cell : allCells) {
            if (cell.selected) {
                startCell = cell;
                break;
            }
        }

        if (startCell == null) {
            // No yellow cells found
            return true;
        }

        // Use a stack for DFS
        Stack<DrawCell> stack = new Stack<>();
        stack.push(startCell);

        while (!stack.isEmpty()) {
            DrawCell current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);

                // Check neighbors
                for (DrawCell neighbor : getYellowNeighbors(current)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        // Check if all yellow cells are visited
        for (DrawCell cell : allCells) {
            if (cell.selected && !visited.contains(cell)) {
                return false;
            }
        }

        return true;
    }

    // Helper method to get yellow neighbors
    private static List<DrawCell> getYellowNeighbors(DrawCell cell) {
        List<DrawCell> neighbors = new ArrayList<>();

        for (DrawCell neighbor : allCells) {
            if (neighbor.selected) {
                if ((neighbor.x == cell.x && (neighbor.y == cell.y - 1 || neighbor.y == cell.y + 1)) ||
                        (neighbor.y == cell.y && (neighbor.x == cell.x - 1 || neighbor.x == cell.x + 1))) {
                    neighbors.add(neighbor);
                }
            }
        }

        return neighbors;
    }

    private boolean hasCage() {
        return cage != null;
    }

    public static String convertGridToString() {
        List<Cage> allCages = new ArrayList<>();
        for (DrawCell c : allCells) {
            if (c.cage == null) continue; // POSAR Q SALTI ERROR
            if (!allCages.contains(c.cage)) allCages.add(c.cage);
        }

        StringBuilder sb = new StringBuilder();
        int cagesCount = allCages.size();

        sb.append(size).append(" ").append(cagesCount).append("\n");

        // Iterate over regions
        for (Cage c : allCages) {
            int operation = c.getOperatorAsNum();
            int result = c.getResult();
            int elementCount = c.countCells();
            sb.append(operation).append(" ").append(result).append(" ").append(elementCount).append(" ");

            for (DrawCell cell : c.getCells()) {
                sb.append(cell.getPosX() + 1).append(" ").append(cell.getPosY() + 1);
                String content = cell.mainLabel.getText();
                if (!content.isEmpty()) {
                    sb.append(" [").append(content).append("]");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

     public static void setPlaying(boolean b) {
        playing = b;
        System.out.println(playing);
    }

    public static void selectCells(DrawCell[] cells) {
        resetYellowCells();
        for (DrawCell c : cells) {
            c.selected = true;
        }
    }
}