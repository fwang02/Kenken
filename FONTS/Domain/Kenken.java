package Domain;

import java.util.ArrayList;
import java.util.HashSet;

public class Kenken {
    private final int size;
    private String name;
    private final TypeDificult dificult;
    private final HashSet<TypeOperation> operations;
    private int ncages;
    private int nicells;
    private ArrayList<KenkenCage> cages;
    private KenkenCell[][] cells;

    Kenken(){
        this.dificult = TypeDificult.EASY;
        this.size = 0;
        this.operations = new HashSet<>();
        this.cages = new ArrayList<>();

    }

    Kenken(Kenken k) {
        this.size = k.getSize();
        this.name = k.getName();
        this.dificult = k.getDificult();
        this.operations = k.getOperations();
        this.ncages = k.ncages;
        this.nicells = k.nicells;
        this.cages = new ArrayList<>(k.getAllCages());
        iniCells();
    }
    
    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult){         // PARA GENERAR Y RESOLVER KENKENS v1
        this.size = size;
        this.name = null;
        this.dificult = dificult;
        this.operations = operations;
        this.ncages = 0;
        this.nicells = 0;
        this.cages = new ArrayList<>();
        iniCells();
    }

    Kenken(int size, HashSet<TypeOperation> operations, int ncages){         // PARA GENERAR Y RESOLVER KENKENS v2
        this.size = size;
        this.name = null;
        this.dificult = TypeDificult.MEDIUM;
        this.operations = operations;
        this.ncages = ncages;
        this.cages = new ArrayList<>();
        iniCells();
    }

    // PARA GENERAR Y RESOLVER KENKENS v2
    Kenken(int size, HashSet<TypeOperation> operations, int number_cages, int number_i_cells){
        this.size = size;
        this.name = null;
        this.dificult = null;
        this.operations = operations;
        this.ncages = number_cages;
        this.nicells = number_i_cells;
        this.cages = new ArrayList<>();
        iniCells();
    }

    // PARA RESOLVER KENKEN DESDE UN FICHERO
     Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult, ArrayList<KenkenCage> cages){
        this.size = size;
        this.cages = cages;
        this.dificult = dificult;
        this.operations = operations;
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

    public int getNCages() {
        return ncages;
    }

    public int getNICells() {
        return nicells;
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

    public void addCage(TypeOperation operation, int result, Pos[] posCells) {
        KenkenCage tmp = new KenkenCage(operation, result, posCells);
        cages.add(tmp);
    }

    public void addLockedCage(TypeOperation operation, int result, Pos[] posCells) {
        KenkenCage tmp = new KenkenCage(operation, result, posCells);
        tmp.setLocked();
        cages.add(tmp);
    }

    public boolean AlreadyInCage(int x, int y) {
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
        KenkenCell tmp;
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                tmp = new KenkenCell(i,j);
                cells[i][j] = tmp;
            }
        }
    }
}
