/**
 * @author Javier Parcerisas Nisa
 */
package Domain;

import Domain.Operation.Operation;

import java.util.ArrayList;
import java.util.HashSet;


public class Kenken {
    private final int size;
    private final String name;
    private final TypeDifficulty dificult;
    private final HashSet<Operation> operations;
    private final int numberCages;
    private final int numberIndCells;
    private final ArrayList<KenkenCage> cages;
    private KenkenCell[][] cells;
    private int[][] board;
    private int hints;
    private int points;
    private boolean solved;

    public Kenken() {
        this.size = 0;
        this.name = null;
        this.dificult = TypeDifficulty.EASY;
        this.operations = new HashSet<>();
        this.numberCages = 0;
        this.numberIndCells = 0;
        this.cages = new ArrayList<>();
        iniCells();
        this.solved = false;
        this.board = new int[this.size][this.size];

    }

    // COPIAR UN KENKEN PERO CON LAS CASILLAS A 0
    public Kenken(Kenken k) {
        this.size = k.getSize();
        this.name = k.getName();
        this.dificult = k.getDificult();
        this.operations = k.getOperations();
        this.numberCages = k.numberCages;
        this.numberIndCells = k.numberIndCells;
        this.cages = new ArrayList<>(k.getAllCages());
        iniCells();
        this.board = new int[this.size][this.size];
        this.solved = false;
    }

    // PARA GENERAR Y RESOLVER KENKENS v1
    public Kenken(int size, HashSet<Operation> operations, TypeDifficulty dificult) {
        this.size = size;
        this.name = null;
        this.dificult = dificult;
        this.operations = operations;
        this.numberCages = 0;
        this.numberIndCells = 0;
        this.cages = new ArrayList<>();
        iniCells();
        this.board = new int[this.size][this.size];
        this.solved = false;
    }

    // PARA RESOLVER KENKEN DESDE UN FICHERO
    public Kenken(int size, HashSet<Operation> operations, TypeDifficulty dificult, ArrayList<KenkenCage> cages, KenkenCell[][] cells) {
        this.size = size;
        this.name = null;
        this.dificult = dificult;
        this.operations = operations;
        this.numberCages = 0;
        this.numberIndCells = 0;
        this.cages = cages;
        this.cells = cells;
        this.board = new int[this.size][this.size];
        this.solved = false;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public TypeDifficulty getDificult() {
        return dificult;
    }

    public HashSet<Operation> getOperations() {
        return operations;
    }

    public int getNumberCages() {
        return numberCages;
    }

    public int getNumberIndCells() {
        return numberIndCells;
    }

    public KenkenCell[][] getAllCells() {
        return cells;
    }

    public KenkenCell getCell(int i, int j) {
        return cells[i][j];
    }

    public KenkenCell getCell(Pos p) {
        return cells[p.posX][p.posY];
    }

    public ArrayList<KenkenCage> getAllCages() {
        return cages;
    }

    public KenkenCage getCage(int index) {
        return cages.get(index);
    }

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

    public int[][] getBoard() {
        return board;
    }

    public void addOpCage(Operation operation, int result, Pos[] posCells) {
        cages.add(new KenkenCage(operation, result, posCells));
    }

    public void addNoOpCage(Pos[] posCells) {
        cages.add(new KenkenCage(posCells));
    }

    public void incrHints() {
        ++hints;
    }

    public boolean alreadyInCage(int x, int y) {
        return this.getCell(x, y).isLocked();
    }

    public boolean rowCheck(int row, int val) {
        for (int col = 0; col < getSize(); ++col) {
            if (getCell(row, col).getValue() == val) return false;
        }
        return true;
    }

    public boolean colCheck(int col, int val) {
        for (int row = 0; row < getSize(); ++row) {
            if (getCell(row, col).getValue() == val) return false;
        }
        return true;
    }

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

    public boolean insertNumberBoard(int x, int y, int v) {
        if (x > getSize() || y > getSize() || x < 1 || y < 1) return false;
        else if (v > getSize() || v < 1) return false;
        else {
            board[x - 1][y - 1] = v;
            return true;
        }
    }

    public boolean deleteNumberBoard(int x, int y) {
        if (x > getSize() || y > getSize() || x < 1 || y < 1) return false;
        else {
            board[x - 1][y - 1] = 0;
            return true;
        }
    }

    public void resetBoard() {
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); ++j) {
                board[i][j] = 0;
            }
        }
    }

    //actualmente no usado, el equivalente esta en el driver
    public void showBoard() {
        System.out.print("Kenken\n");
        System.out.print("   ");
        for (int i = 0; i < getSize(); ++i) {
            System.out.print(i + 1 + "  ");
        }
        System.out.print("\n");

        for (int i = 0; i < getSize(); ++i) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < getSize(); ++j) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }


    //actualmente no usado, el equivalente esta en el driver
    public void showCages() {
        for(int i = 0; i < cages.size(); ++i) {
            System.out.print("Region " + i + "-> ");
            System.out.print("Size: " + cages.get(i).getCageSize() + " ");
            System.out.print("Casilla: ");
            for(int j = 0; j < cages.get(i).getCageSize(); ++j) {
                int x = cages.get(i).getPos(j).posX + 1;
                int y = cages.get(i).getPos(j).posY + 1;
                System.out.print(x + " ");
                System.out.print(y + " || ");
            }
            System.out.print(cages.get(i).getOperation() + " = ");
            System.out.print(cages.get(i).getResult() + "\n");
        }
    }

    //actualmente no usado, el equivalente esta en el driver
    public void showSolution() {
        System.out.println("Solucion del Kenken");
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); ++j) {
                System.out.print(getCell(i, j).getValue() + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    //actualmente no usado, el equivalente esta en el driver
    public void hint() {
        boolean hintGived = false;
        for (int i = 0; i < getSize() && !hintGived; ++i) {
            for (int j = 0; j < getSize() && !hintGived; ++j) {
                if (board[i][j] == 0) {
                    hintGived = true;
                    int v = getCell(i, j).getValue();
                    ++hints;
                    int x = i + 1;
                    int y = j + 1;
                    System.out.println("Prueba con poner " + v + " en la casilla x: " + x + " y: " + y + " Puede que funcione");
                }
            }
        }
    }

    public int check() {
        if (solved) return 0;
        int incorrect = 0;
        int correct = 0;
        int undefined = 0;
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); ++j) {
                if (board[i][j] == 0) ++undefined;
                if (board[i][j] != 0) {
                    int correctValue = getCell(i, j).getValue();
                    int x = i + 1;
                    int y = j + 1;
                    if (board[i][j] != correctValue) {
                        ++incorrect;
                    } else {
                        ++correct;
                    }
                }
            }
        }
        if (incorrect == 0 && correct == (getSize() * getSize()) && undefined == 0) {
            solved = true;
            return getPoints();
        } else if (incorrect != 0) return -1;
        else return -2;
    }

    public int getPoints() {
        if (!solved) return -1;
        else {
            int multiplier = 1;
            switch (dificult) {
                case EASY:
                    multiplier = 25;
                    break;
                case MEDIUM:
                    multiplier = 50;
                    break;
                case HARD:
                    multiplier = 75;
                    break;
                case EXPERT:
                    multiplier = 100;
                    break;
            }
            points = multiplier * size * size - hints * 2 * size * size;
            if (points < 0) points = 1;
            return points;
        }
    }

}
