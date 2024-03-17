package FONTS.src;
import java.util.ArrayList;
import java.util.HashSet;

public class Kenken {
    private final int size;
    private final HashSet<TypeOperation> operations;
    private final ArrayList<KenkenCage> cages;
    private ArrayList<KenkenCell> cells;
    private final TypeDificult dificult;
    private String name;


    Kenken(){
        this.dificult = TypeDificult.MEDIUM;
        size = 0;
        operations = new HashSet<TypeOperation>();
        cages = new ArrayList<KenkenCage>();
        iniCells();
    }

    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult){
        this.size = size;
        this.operations = operations;
        this.dificult = dificult;
        cages = new ArrayList<KenkenCage>();
        iniCells();
    }

    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult, String name){
        this.size = size;
        this.operations = operations;
        this.dificult = dificult;
        cages = new ArrayList<KenkenCage>();
        iniCells();
        this.name = name;
    }


    public int getWidth() {
        return size;
    }


    public int getHeight() {
        return size;
    }


    public KenkenCell getCell(int i, int j) {
        return cells.get(i*getWidth()+j);
    }

    
    private void iniCells() {
        cells = new ArrayList<KenkenCell>();
        KenkenCell tmp = null;
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                tmp = new KenkenCell(i,j,-1, false);
                cells.add(tmp);
            }
        }
    }

    public boolean rowCheck(int row, int val) {
        for(int col = 0; col < getWidth(); ++col) {
            if(getCell(row, col).getValue() == val) return false;
        }
        return true;
    }

    public boolean colCheck(int col, int val) {
        for(int row = 0; row < getHeight(); ++row) {
            if(getCell(row, col).getValue() == val) return false;
        }
        return true;
    }

    /*public boolean isValid() {

    }*/
}
