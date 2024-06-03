package Presentation;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * Esta clase representa una celda en el juego Kenken.
 * Se encarga de gestionar el comportamiento de las celdas, incluyendo la selección,
 * la entrada de números, y la creación de regiones (cages).
 *
 * @author Romeu Esteve
 */
public class DrawCell extends JPanel {
    private static boolean leftClickHeld = false;
    private static boolean playing;
    private boolean selected = false;
    private static final List<DrawCell> allCells = new ArrayList<>();
    private static final ArrayList<Cage> cages = new ArrayList<>();
    private static int size;
    private final int x; // Posición X en el juego
    private final int y; // Posición Y en el juego
    private final JLabel mainLabel;
    private final JLabel smallLabel;
    private Cage cage;

    /**
     * Construye la celda y establece las posiciones x e y en el juego.
     *
     * @param x Posición X en el juego.
     * @param y Posición Y en el juego.
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
                        for (DrawCell c : cage.getCells()) {
                            c.setBackground(Color.YELLOW);
                            c.selected = true;
                        }
                        cage.cageConfig();
                    }
                }
                requestFocus();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    leftClickHeld = false;
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isDigit(keyChar) && keyChar - '0' <= size) {
                    setNumberInAllYellowCells(Character.getNumericValue(keyChar));
                } else if (keyChar == '\b') { //Backspace
                    setNumberInAllYellowCells(0); // set to blank
                } else if (!playing && keyChar == ' ') { //Spacebar
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

    public static void eraseCage(Cage cage) {
        cages.remove(cage);
    }

    /**
     * Establece el número y el color del texto según la entrada del usuario.
     *
     * @param number    Número a establecer.
     * @param userInput Booleano que indica si el número es una entrada del usuario o no.
     */
    public void setNumber(int number, boolean userInput) {
        mainLabel.setForeground(userInput ? Color.BLUE : Color.BLACK);
        mainLabel.setText(number > 0 ? number + "" : "");
    }

    public int getNumber() {
        String n = mainLabel.getText().trim();
        return n.isEmpty() ? 0 : Integer.parseInt(n);
    }

    public void setOp(String op) {
        smallLabel.setText(op);
    }

    /**
     * Establece el número dado en todas las celdas amarillas.
     *
     * @param number Número a establecer en todas las celdas amarillas.
     */
    private static void setNumberInAllYellowCells(int number) {
        for (DrawCell cell : allCells) {
            if (cell.selected) {
                if (playing && cell.mainLabel.getForeground() == Color.BLACK && !Objects.equals(cell.mainLabel.getText(), ""))
                    continue;
                cell.setNumber(number, playing);
            }
        }
    }

    /**
     * Establece la región para la celda.
     * @param c La celda que se establece.
     */
    public void setCage(Cage c) {
        cage = c;
    }

    /**
     * Establece los bordes de las celdas seleccionadas para crear una región (cage).
     */
    public static void createCage(char op, int res) {
        for (DrawCell cell : allCells) {
            if (cell.selected && cell.hasCage()) {
                JOptionPane.showMessageDialog(null, "Esta casilla ya está en una región");
                return;
            }
        }
        if (!areYellowCellsConnected()) {
            JOptionPane.showMessageDialog(null, "Las casillas no están conectadas");
            return;
        }
        Cage cage = new Cage();
        cages.add(cage);
        cage.setOpCell(findTopLeftCell());

        for (DrawCell cell : allCells) {
            if (cell.selected) {
                cage.addCell(cell);
                CustomBorder border = getCustomBorder(cell);
                cell.setBorder(border);
            }
        }
        if (op == ' ') cage.cageConfig();
        else cage.setCage(op, res);
    }

    /**
     * Obtiene un borde personalizado para la celda dada.
     *
     * @param cell La celda para la que se está obteniendo el borde.
     * @return Un borde personalizado para la celda.
     */
    private static CustomBorder getCustomBorder(DrawCell cell) {
        boolean top = true;
        boolean bottom = true;
        boolean left = true;
        boolean right = true;

        for (DrawCell neighbor : allCells) {
            if (neighbor.selected) {
                if (neighbor.x == cell.x && neighbor.y == cell.y - 1) left = false;
                if (neighbor.x == cell.x && neighbor.y == cell.y + 1) right = false;
                if (neighbor.x == cell.x - 1 && neighbor.y == cell.y) top = false;
                if (neighbor.x == cell.x + 1 && neighbor.y == cell.y) bottom = false;
            }
        }

        return new CustomBorder(top ? Color.BLACK : Color.LIGHT_GRAY, top ? 3 : 0,
                left ? Color.BLACK : Color.LIGHT_GRAY, left ? 3 : 0,
                bottom ? Color.BLACK : Color.LIGHT_GRAY, bottom ? 3 : 0,
                right ? Color.BLACK : Color.LIGHT_GRAY, right ? 3 : 0);
    }

    /**
     * Encuentra la celda en la esquina superior izquierda entre las celdas seleccionadas.
     *
     * @return La celda en la esquina superior izquierda.
     */
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

    /**
     * Establece el tamaño del tablero de juego.
     *
     * @param s El tamaño del tablero de juego.
     */
    public static void setSize(int s) {
        size = s;
    }

    /**
     * Verifica si las celdas seleccionadas están conectadas.
     *
     * @return true si las celdas seleccionadas están conectadas, de lo contrario false.
     */
    private static boolean areYellowCellsConnected() {
        Set<DrawCell> visited = new HashSet<>();
        DrawCell startCell = null;
        for (DrawCell cell : allCells) {
            if (cell.selected) {
                startCell = cell;
                break;
            }
        }
        if (startCell == null) return true;

        Stack<DrawCell> stack = new Stack<>();
        stack.push(startCell);

        while (!stack.isEmpty()) {
            DrawCell current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                for (DrawCell neighbor : getYellowNeighbors(current)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        for (DrawCell cell : allCells) {
            if (cell.selected && !visited.contains(cell)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene los vecinos amarillos (seleccionados) de una celda.
     *
     * @param cell La celda para la que se están obteniendo los vecinos.
     * @return Una lista de celdas vecinas amarillas.
     */
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

    /**
     * Verifica si la celda tiene una región (cage) asignada.
     *
     * @return true si la celda tiene una región, de lo contrario false.
     */
    private boolean hasCage() {
        return cage != null;
    }

    /**
     * Establece si el juego está en modo de juego.
     *
     * @param b true si el juego está en modo de juego, de lo contrario false.
     */
    public static void setPlaying(boolean b) {
        playing = b;
    }

    /**
     * Selecciona un conjunto de celdas.
     *
     * @param cells Las celdas a seleccionar.
     */
    public static void selectCells(DrawCell[] cells) {
        resetYellowCells();
        for (DrawCell c : cells) {
            c.selected = true;
        }
    }

    /**
     * Obtiene el número de regiones (cages) en el tablero.
     *
     * @return El número de regiones.
     */
    public static int getNCages() {
        return cages.size();
    }

    /**
     * Obtiene las posiciones X de las celdas en una región específica.
     *
     * @param index El índice de la región.
     * @return Un array con las posiciones X de las celdas.
     */
    public static int[] getCageCellsX(int index) {
        List<DrawCell> cells = cages.get(index).getCells();
        int s = cells.size();
        int[] cellsX = new int[s];
        for (int i = 0; i < s; ++i) {
            cellsX[i] = cells.get(i).x;
        }
        return cellsX;
    }

    /**
     * Obtiene las posiciones Y de las celdas en una región específica.
     *
     * @param index El índice de la región.
     * @return Un array con las posiciones Y de las celdas.
     */
    public static int[] getCageCellsY(int index) {
        List<DrawCell> cells = cages.get(index).getCells();
        int s = cells.size();
        int[] cellsY = new int[s];
        for (int i = 0; i < s; ++i) {
            cellsY[i] = cells.get(i).y;
        }
        return cellsY;
    }

    /**
     * Obtiene el operador de una región específica.
     *
     * @param index El índice de la región.
     * @return El operador de la región.
     */
    public static int getCageOp(int index) {
        return cages.get(index).getOperatorAsNum();
    }

    /**
     * Obtiene el resultado de una región específica.
     *
     * @param index El índice de la región.
     * @return El resultado de la región.
     */
    public static int getCageRes(int index) {
        return cages.get(index).getResult();
    }

    /**
     * Reinicia todas las celdas y regiones.
     */
    public static void resetDrawCells() {
        if (!allCells.isEmpty()) allCells.clear();
        if (!cages.isEmpty()) cages.clear();
    }
}