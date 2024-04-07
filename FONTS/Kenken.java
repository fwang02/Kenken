import java.util.ArrayList;
import java.util.HashSet;

public class Kenken {
    private final int size;
    private String name;
    private final TypeDificult dificult;
    private final HashSet<TypeOperation> operations;
    private ArrayList<KenkenCage> cages;
    private ArrayList<KenkenCell> cells;


    Kenken(Kenken k) {
        this.size = k.getSize();
        this.name = k.getName();
        this.dificult = k.getDificult();
        this.operations = k.getOperations();
        this.cages = new ArrayList<KenkenCage>(k.getCages());
        this.cells = new ArrayList<KenkenCell>(k.getCells());
    }
    
    Kenken(){
        this.dificult = TypeDificult.MEDIUM;
        size = 0;
        operations = new HashSet<>();
        cages = new ArrayList<>();
        iniCells();
    }

    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult){
        this.size = size;
        this.operations = operations;
        this.dificult = dificult;
        cages = new ArrayList<KenkenCage>();
        iniCells();
        this.name = null;
    }

    Kenken(int size, HashSet<TypeOperation> operations, TypeDificult dificult, String name){
        this.size = size;
        this.operations = operations;
        this.dificult = dificult;
        cages = new ArrayList<KenkenCage>();
        iniCells();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public TypeDificult getDificult() {
        return dificult;
    }

    public HashSet<TypeOperation> getOperations() {
        return operations;
    }

    public ArrayList<KenkenCell> getCells() {
        return cells;
    }

    public KenkenCell getCell(int i, int j) {
        return cells.get(i*getSize()+j);
    }

    public KenkenCell getCell(Pos p) {
        return cells.get(p.posX*getSize()+p.posY);
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

    public ArrayList<KenkenCage> getCages() {
        return cages;
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
        if(this.getCell(x, y).isLocked()) return true;
        else return false;
    }


    private void iniCells() {
        cells = new ArrayList<>();
        KenkenCell tmp;
        for(int i = 0; i < size; ++i) {
            for(int j = 0; j < size; ++j) {
                tmp = new KenkenCell(i,j,0, false);
                cells.add(tmp);
            }
        }
    }

}
