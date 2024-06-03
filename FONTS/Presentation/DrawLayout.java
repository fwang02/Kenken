package Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Panel para dibujar el tablero del juego KenKen.
 * Se encarga de inicializar y gestionar las celdas del tablero.
 * @author Romeu Esteve
 */
public class DrawLayout extends JPanel {
    private static DrawCell[][] cells;
    private final int length;
    GridLayout grid;

    /**
     * Constructor para crear el panel de dibujo con el tamaño especificado.
     *
     * @param size    tamaño del tablero
     * @param playing indica si el juego está en modo de juego o no
     */
    public DrawLayout(int size, boolean playing) {
        super(new GridLayout(size, size));
        this.length = size;
        initLayout(size, playing);
    }

    /**
     * Inicializa el diseño del tablero.
     *
     * @param size    tamaño del tablero
     * @param playing indica si el juego está en modo de juego o no
     */
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

    /**
     * Establece el número en una celda específica.
     *
     * @param x   coordenada x de la celda
     * @param y   coordenada y de la celda
     * @param num número a establecer en la celda
     */
    public void setCell(int x, int y, int num, boolean userInput) {
        cells[x][y].setNumber(num, userInput);
    }

    /**
     * Configura una región en el tablero con las celdas y operación especificadas.
     *
     * @param cellsX coordenadas x de las celdas de la región
     * @param cellsY coordenadas y de las celdas de la región
     * @param op     operación de la región
     * @param res    resultado de la operación de la región
     */
    public static void setCage(int[] cellsX, int[] cellsY, char op, int res) {
        int l = cellsX.length;
        DrawCell[] selected = new DrawCell[l];
        for (int i = 0; i < l; ++i) {
            selected[i] = cells[cellsX[i]][cellsY[i]];
        }
        DrawCell.selectCells(selected);
        DrawCell.createCage(op, res);
    }

    /**
     * Obtiene la información de una región específica.
     *
     * @param index índice de la región
     * @return un arreglo con la información de la región: coordenadas x e y, operación y resultado
     */
    public Object[] getCage(int index) {
        Object[] r = new Object[4];
        r[0] = DrawCell.getCageCellsX(index);
        r[1] = DrawCell.getCageCellsY(index);
        r[2] = DrawCell.getCageOp(index);
        r[3] = DrawCell.getCageRes(index);
        return r;
    }

    /**
     * Obtiene el número de regiones en el tablero.
     *
     * @return número de regiones
     */
    public int getNCages() {
        return DrawCell.getNCages();
    }

    /**
     * Obtiene la longitud del tablero.
     *
     * @return longitud del tablero
     */
    public int getLength() {
        return length;
    }

    /**
     * Obtiene los valores actuales de las celdas en el tablero.
     *
     * @return un arreglo con los valores de las celdas
     */
    public int[] getValCells() {
        int s = getLength();
        int[] values = new int[s * s];

        for (int i = 0; i < s * s; ++i) {
            int x = i / s;
            int y = i % s;
            values[i] = cells[x][y].getNumber();
        }

        return values;
    }

    /**
     * Restablece el estado del tablero.
     */
    public void reset() {
        DrawCell.resetDrawCells();
    }
}
