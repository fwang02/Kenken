package Domain;

import Domain.Operation.Operation;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Javier Parcerisas
 */
public class Kenken {
    private final int size;
    private final String name;
    private final TypeDifficulty dificult;
    private final HashSet<Operation> operations;
    private final ArrayList<KenkenCage> cages;
    private KenkenCell[][] cells;
    private int[][] board;
    private int hints;
    private int points;
    private boolean finished;

    /**
     * Constructora de la clase Kenken
     */
    public Kenken() {
        this.size = 0;
        this.name = null;
        this.dificult = TypeDifficulty.EASY;
        this.operations = new HashSet<>();
        this.cages = new ArrayList<>();
        iniCells();
        this.finished = false;
        this.board = new int[this.size][this.size];

    }

    /**
     * Constructora de la clase Kenken. Crea un Kenken copiando los parámetros de otro Kenken dado.
     * @param k Instancia de Kenken del cual copia sus parámetros.
     */
    public Kenken(Kenken k) {
        this.size = k.getSize();
        this.name = k.getName();
        this.dificult = k.getDificult();
        this.operations = k.getOperations();
        this.cages = new ArrayList<>(k.getAllCages());
        iniCells();
        this.board = new int[this.size][this.size];
        this.finished = false;
        this.hints = k.hints;
        this.points = k.points;
    }


    /**
	 * Constructora de la clase Kenken. Crea un Kenken según la dificultad, operaciones y tamaño
     * 
     * @param size tamaño del Kenken
     * @param operations distintas operaciones que contiene el Kenken
     * @param dificult dificultad del Kenken
	 */
    public Kenken(int size, HashSet<Operation> operations, TypeDifficulty dificult) {
        this.size = size;
        this.name = null;
        this.dificult = dificult;
        this.operations = operations;
        this.cages = new ArrayList<>();
        iniCells();
        this.board = new int[this.size][this.size];
        this.finished = false;
    }


    /**
	 * Constructora de la clase Kenken. Crea un Kenken completamente o parcialmente definido
     * 
     * @param size tamaño del Kenken
     * @param operations distintas operaciones que contiene el Kenken
     * @param dificult dificultad del Kenken
     * @param cages regiones del Kenken
     * @param cells casillas del kenken, pueden contener o no la solución.
	 */
    public Kenken(int size, HashSet<Operation> operations, TypeDifficulty dificult, ArrayList<KenkenCage> cages, KenkenCell[][] cells) {
        this.size = size;
        this.name = null;
        this.dificult = dificult;
        this.operations = operations;
        this.cages = cages;
        this.cells = cells;
        this.board = new int[this.size][this.size];
        this.finished = false;
    }


    /**
	 * Constructora de la clase Kenken. Crea un Kenken con un estado de juego avanzado
     * 
     * @param size tamaño del Kenken
     * @param operations distintas operaciones que contiene el Kenken
     * @param dificult dificultad del Kenken
     * @param cages regiones del Kenken
     * @param cells casillas del kenken, pueden contener o no la solución.
     * @param board tablero que contiene los valores introducidos por el usuario
	 */
    public Kenken(int size, HashSet<Operation> operations, TypeDifficulty dificult, ArrayList<KenkenCage> cages, KenkenCell[][] cells, int[][] board) {
        this.size = size;
        this.name = null;
        this.dificult = dificult;
        this.operations = operations;
        this.cages = cages;
        this.cells = cells;
        this.board = board;
        this.finished = false;
    }

    
    /**
	 * Getter para obtener el tamaño del kenken
     * 
     * @return devuelve el tamaño del kenken
	 */
    public int getSize() {
        return size;
    }


    /**
	 * Getter para obtener el nombre del kenken
     * 
     * @return devuelve el nombre del kenken
	 */
    public String getName() {
        return name;
    }


    /**
	 * Getter para obtener la dificultad del kenken
     * 
     * @return devuelve la dificultad del kenken
	 */
    public TypeDifficulty getDificult() {
        return dificult;
    }

    /**
	 * Getter para obtener las operaciones del kenken
     * 
     * @return devuelve las operaciones del kenken
	 */
    public HashSet<Operation> getOperations() {
        return operations;
    }

    /**
	 * Getter para obtener las casillas del kenken
     * 
     * @return devuelve las casillas del kenken
	 */
    public KenkenCell[][] getAllCells() {
        return cells;
    }


    /**
	 * Getter para obtener una casilla del kenken
     * 
     * @param i posición i de la casilla
     * @param j posición j de la casilla
     * @return devuelve la casilla en la posición (i, j)
	 */
    public KenkenCell getCell(int i, int j) {
        return cells[i][j];
    }


    /**
     * Getter para obtener una casilla del kenken
     * 
     * @param p posición p de la casilla mediante la clase Pos
     * @return devuelve la casilla en la posición (p)
     */
    public KenkenCell getCell(Pos p) {
        return cells[p.posX][p.posY];
    }

    /**
     * Getter para obtener el número de regiones del kenken
     * 
     * @return devuelve el número de regiones del kenken
     */
    public int getNumberCages() {
        return cages.size();
    }

    /**
     * Getter para obtener las regiones del kenken
     * 
     * @return devuelve las regiones del kenken
     */
    public ArrayList<KenkenCage> getAllCages() {
        return cages;
    }


    /**
     * Getter para obtener una región del kenken
     * 
     * @param index posición de la región en el ArrayList de regiones
     * @return devuelve la región con la posición indicada por el indice
     */
    public KenkenCage getCage(int index) {
        return cages.get(index);
    }


    /**
     * Getter para obtener la región a la cual pertenece una casilla
     * 
     * @param row posición de fila de la casilla
     * @param col posición de columna de la casilla
     * @return devuelve la región a la que pertenece la casilla con posición (row, col)
     */
    public KenkenCage getCage(int row, int col) {
        for (KenkenCage cage : cages) {
            for (int j = 0; j < cage.getCageSize(); ++j) {
                int x = cage.getPos(j).posX;
                int y = cage.getPos(j).posY;
                if (x == row && y == col) {
                    return cage;
                }
            }
        }
        return null;
    }

    /**
     * Getter para obtener el tablero del kenken
     * 
     * @return devuelve el tablero del kenken
     */
    public int[][] getBoard() {
        return board;
    }


    /**
     * Función para añadir una región al kenken
     * 
     * @param operation indica el tipo de operación de la región
     * @param result indica el resultado que tiene esa región
     * @param posCells indica las posiciones de las casillas que pertenecen a esa región
     */
    public void addOpCage(Operation operation, int result, Pos[] posCells) {
        cages.add(new KenkenCage(operation, result, posCells));
    }

    /**
     * Función para añadir una región al kenken
     * 
     * @param posCells indica las posiciones de las casillas que pertenecen a esa región
     */
    public void addNoOpCage(Pos[] posCells) {
        cages.add(new KenkenCage(posCells));
    }

    /**
     * Función para incrementar el numero de pistas utilizadas en la resolución del kenken
     */
    public void incrHints() {
        ++hints;
    }


    /**
     * Función que comprueba si una casilla ya pertenece a una región o no
     * 
     * @param x posición en x de la casilla
     * @param y posición en y de la casilla
     * @return cierto si pertenece a una región o falso si no pertenece
     */
    public boolean alreadyInCage(int x, int y) {
        return this.getCell(x, y).isLocked();
    }


    /**
     * Función que comprueba si un valor se repite en una fila
     * 
     * @param row fila que queremos comprobar
     * @param val valor que queremos comprobar
     * @return cierto si no se repite o falso si se repite
     */
    public boolean rowCheck(int row, int val) {
        for (int col = 0; col < getSize(); ++col) {
            if (getCell(row, col).getValue() == val) return false;
        }
        return true;
    }

    /**
     * Función que comprueba si un valor se repite en una columna
     * 
     * @param col columna que queremos comprobar
     * @param val valor que queremos comprobar
     * @return cierto si no se repite o falso si se repite
     */
    public boolean colCheck(int col, int val) {
        for (int row = 0; row < getSize(); ++row) {
            if (getCell(row, col).getValue() == val) return false;
        }
        return true;
    }


    /**
     * Función que inicializa las casillas de un kenken
     */
    public void iniCells() {
        if (cells == null) {
            cells = new KenkenCell[size][size];

            KenkenCell tmp;
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    tmp = new KenkenCell(i, j);
                    cells[i][j] = tmp;
                }
            }
        }
    }

    /**
     * Setter que modifica el tablero
     * 
     * @param auxboard nuevo tablero
     */
    public void setBoard(int[][] auxboard) {
        board = auxboard;
    }

    /**
     * Setter que modifica el estado del kenken y lo pone a finalizado
     */
    public void setFinished() { 
        finished = true; 
    }


    /**
     * Función que calcula los puntos en función de diversos factores.
     */
    public int getPoints() {
        if (!finished) return -1;
        else {
            int multiplier = 1;
            switch (dificult) {
                case EASY:
                    multiplier = 25;
                    break;
                case MEDIUM:
                case CUSTOM:
                    multiplier = 50;
                    break;
                case HARD:
                    multiplier = 75;
                    break;
                case EXPERT:
                    multiplier = 100;
                    break;
            }
            int numOp = operations.size();
            points = (numOp * size + multiplier * size * size) - hints * size * size;
            if (points < 0) points = 1;
            return points;
        }
    }
}
