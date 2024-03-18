package FONTS.src;
import java.util.ArrayList;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class KenkenCage {
    private final ArrayList<KenkenCell> cells;
    private final TypeOperation operation;
    private final int result;
    private boolean allLocked;


    KenkenCage() {
        cells = new ArrayList<>();
        operation = null;
        result = 0;
        allLocked = false;
    }

    KenkenCage(TypeOperation operation, int result) {
        cells = new ArrayList<>();
        this.operation = operation;
        this.result = result;
        allLocked = false;
    }

    //Constructora para creado por fichero
    KenkenCage(TypeOperation operation, int result, ArrayList<KenkenCell> cells) {
        this.cells = cells;
        this.operation = operation;
        this.result = result;
        allLocked = false;
    }

    public boolean isCageValid() {
        if(operation == TypeOperation.ADD) {
            int sum = 0;
            for (KenkenCell cell : cells) {
                if (cell.isLocked()) {
                    sum += cell.getValue();
                }
            }
            if(allLocked) return result == sum;
            else return result > sum;
        } else if (operation == TypeOperation.SUB) {
            if(allLocked) return abs(cells.get(0).getValue() - cells.get(1).getValue()) == result;
        } else if (operation == TypeOperation.MULT) {
            int mult = 1;
            for (KenkenCell cell : cells) {
                if(cell.isLocked()) {
                    mult = mult * cell.getValue();
                }
            }
            if(allLocked) return result == mult;
            else return result > mult;
        } else if (operation == TypeOperation.DIV) {
            if(allLocked) {
                if(cells.get(0).getValue() > cells.get(1).getValue()) return cells.get(0).getValue() / cells.get(1).getValue() == result;
                else return cells.get(1).getValue() / cells.get(0).getValue() == result;
            }
        } else if (operation == TypeOperation.MOD) {
            if(allLocked) {
                return cells.get(0).getValue() % cells.get(1).getValue() == result || cells.get(1).getValue() % cells.get(0).getValue() == result;
            }
        } else if (operation == TypeOperation.POW) {
            if(allLocked) {
                return pow(cells.get(0).getValue(),cells.get(1).getValue()) == result || pow(cells.get(1).getValue(),cells.get(0).getValue()) == result;
            }
        }
        return true;
    }

    public int getResult() {
        return result;
    }

    public boolean isAllLocked() {
        return allLocked;
    }

    public TypeOperation getOperation() {
        return operation;
    }

    public ArrayList<KenkenCell> getCells() {
        return cells;
    }
}