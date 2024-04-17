package Domain;

import java.util.ArrayList;
import java.util.HashSet;

public class Kenken {
    private final int size;
    private final String name;
    private final TypeDificult dificult;
    private final HashSet<TypeOperation> operations;
    private final int numberCages;
    private final int numberIndCells;
    private final ArrayList<KenkenCage> cages;
    private KenkenCell[][] cells;

    Kenken(){
        this.size = 0;
        this.name = null;
        this.dificult = TypeDificult.EASY;
        this.operations = new HashSet<>();
        this.numberCages = 0;
        this.numberIndCells = 0;
        this.cages = new ArrayList<>();
        iniCells();

    }

    // COPIAR UN KENKEN PERO CON LAS CASILLAS A 0
    Kenken(Kenken k) {
        this.size = k.getSize();
        this.name = k.getName();
        this.dificult = k.getDificult();
        this.operations = k.getOperations();
        this.numberCages = k.numberCages;
        this.numberIndCells = k.numberIndCells;
        this.cages = new ArrayList<>(k.getAllCagesEmpty());
        iniCells();
    }
    
    // PARA GENERAR Y RESOLVER KENKENS v1
    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult){         
        this.size = size;
        this.name = null;
        this.dificult = dificult;
        this.operations = operations;
        this.numberCages = 0;
        this.numberIndCells = 0;
        this.cages = new ArrayList<>();
        iniCells();
    }

    // PARA GENERAR Y RESOLVER KENKENS v2
    Kenken(int size, HashSet<TypeOperation> operations, int number_cages, int number_i_cells){
        this.size = size;
        this.name = null;
        this.dificult = null;
        this.operations = operations;
        this.numberCages = number_cages;
        this.numberIndCells = number_i_cells;
        this.cages = new ArrayList<>();
        iniCells();
    }

    // PARA RESOLVER KENKEN DESDE UN FICHERO
    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult, ArrayList<KenkenCage> cages){
        this.size = size;
        this.name = null;
        this.dificult = dificult;
        this.operations = operations;
        this.numberCages = 0;
        this.numberIndCells = 0;
        this.cages = cages;
        iniCells();
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public TypeDificult getDificult() {
        return dificult;
    }

    public HashSet<TypeOperation> getOperations() {
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

    public ArrayList<KenkenCage> getAllCagesEmpty() {
        ArrayList<KenkenCage> emptyCages = new ArrayList<>();

        for (KenkenCage c : cages) {
            KenkenCell[] emptyCells = new KenkenCell[c.getCageSize()];
            for (int i = 0; i < emptyCells.length; ++i) {
                KenkenCell cell = c.getCell(i);
                int v = 0;
                // if (cell.isLocked()) v = cell.getValue();
                emptyCells[i] = new KenkenCell(cell.getPosX(), cell.getPosY(), v, cell.isLocked());
            }
            KenkenCage cage = new KenkenCage(c.getOperation(), c.getResult(), emptyCells);
            emptyCages.add(cage);
        }
        return emptyCages;
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

    public void addCage(TypeOperation operation, int result, KenkenCell[] cells) {
        KenkenCage tmp = new KenkenCage(operation, result, cells);
        cages.add(tmp);
    }

    public void addCagePos(TypeOperation operation, int result, Pos[] pos) {
        KenkenCell[] cells = new KenkenCell[pos.length];
        for (int i = 0; i < pos.length; ++i) {
            cells[i] = getCell(pos[i]);
        }
        KenkenCage tmp = new KenkenCage(operation, result, cells);
        cages.add(tmp);
    }

    public boolean alreadyInCage(int x, int y) {
        return this.getCell(x, y).isLocked();
    }

    public boolean rowCheck(int row, int val) {
        for(int col = 0; col < getSize(); ++col) {
            if(getCell(row, col).getValue() == val) return false;
        }
        return true;
    }

    public boolean colCheck(int col, int val) {
        for(int row = 0; row < getSize(); ++row) {
            if(getCell(row, col).getValue() == val) return false;
        }
        return true;
    }

    public void iniCells() {
        cells = new KenkenCell[size][size];
        if (cages.isEmpty()) {
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    cells[i][j] = new KenkenCell(i, j);
                }
            }
            return;
        }

        for (KenkenCage cage : cages) {
            int s = cage.getCageSize();

            for (int i = 0; i < s; ++i) {
                KenkenCell c = cage.getCell(i);
                cells[c.getPosX()][c.getPosY()] = c;
            }
        }
    }
}
