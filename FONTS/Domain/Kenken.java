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
    private final ArrayList<KenkenCage> cages;
    private KenkenCell[][] cells;
    private int[][] board;
    private int hints;
    private int points;
    private boolean finished;

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

    // COPIAR UN KENKEN PERO CON LAS CASILLAS A 0
    public Kenken(Kenken k) {
        this.size = k.getSize();
        this.name = k.getName();
        this.dificult = k.getDificult();
        this.operations = k.getOperations();
        this.cages = new ArrayList<>(k.getAllCages());
        iniCells();
        this.board = new int[this.size][this.size];
        this.finished = false;
    }

    // PARA GENERAR Y RESOLVER KENKENS v1
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

    // PARA RESOLVER KENKEN DESDE UN FICHERO
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

    // PARA CONTINUAR UN KENKEN 
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


    public KenkenCell[][] getAllCells() {
        return cells;
    }

    public KenkenCell getCell(int i, int j) {
        return cells[i][j];
    }

    public KenkenCell getCell(Pos p) {
        return cells[p.posX][p.posY];
    }

    public int getNumberCages() {
        System.out.println(cages);
        return cages.size();
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

    public void setBoard(int[][] auxboard) {
        board = auxboard;
    }

    public void setFinished() { 
        finished = true; 
    }

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
