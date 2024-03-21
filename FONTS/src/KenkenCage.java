package FONTS.src;
import java.util.ArrayList;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

class Pos {
    int posX;
    int posY;
}

public class KenkenCage {
    private final ArrayList<Pos> cells;
    private final TypeOperation operation;
    private final int result;
    private boolean allLocked;
    private Kenken k;

    KenkenCage() {
        cells = new ArrayList<>();
        operation = null;
        k = null;
        result = 0;
        allLocked = false;
    }

    KenkenCage(Kenken k, TypeOperation operation, int result) {
        this.k = k;
        cells = new ArrayList<>();
        this.operation = operation;
        this.result = result;
        allLocked = false;
    }


    public boolean isCageValid() {
        if(operation == TypeOperation.ADD) {
            int sum = 0;
            for (Pos pos_cell : cells) {
                if (k.getCell(pos_cell).isLocked()) {
                    sum += k.getCell(pos_cell).getValue();
                }
            }
            if(allLocked) return result == sum;
            else return result > sum;
        } else if (operation == TypeOperation.SUB) {
            if(allLocked) return abs(k.getCell(cells.get(0)).getValue() - k.getCell(cells.get(1)).getValue()) == result;
        } else if (operation == TypeOperation.MULT) {
            int mult = 1;
            for (Pos pos_cell : cells) {
                if(k.getCell(pos_cell).isLocked()) {
                    mult = mult * k.getCell(pos_cell).getValue();
                }
            }
            if(allLocked) return result == mult;
            else return result > mult;
        } else if (operation == TypeOperation.DIV) {
            if(allLocked) {
                if(k.getCell(cells.get(0)).getValue() > k.getCell(cells.get(1)).getValue()) return k.getCell(cells.get(0)).getValue() / k.getCell(cells.get(1)).getValue() == result;
                else return k.getCell(cells.get(1)).getValue() / k.getCell(cells.get(0)).getValue() == result;
            }
        } else if (operation == TypeOperation.MOD) {
            if(allLocked) {
                return k.getCell(cells.get(0)).getValue() % k.getCell(cells.get(1)).getValue() == result || k.getCell(cells.get(1)).getValue() % k.getCell(cells.get(0)).getValue() == result;
            }
        } else if (operation == TypeOperation.POW) {
            if(allLocked) {
                return pow(k.getCell(cells.get(0)).getValue(),k.getCell(cells.get(1)).getValue()) == result || pow(k.getCell(cells.get(1)).getValue(),k.getCell(cells.get(0)).getValue()) == result;
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

}