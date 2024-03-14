package FONTS.src;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class KenkenCage {
    private final ArrayList<KenkenCell> cells;
    private final TypeOperation operation;
    private final int result;


    KenkenCage() {
        cells = new ArrayList<KenkenCell>();
        operation = null;
        result = 0;
    }

    KenkenCage(TypeOperation operation, int result) {
        cells = new ArrayList<KenkenCell>();
        this.operation = operation;
        this.result = result;
    }

    public boolean isCageValid() {
        int res = 0;
        int count = 0;
        if(operation == TypeOperation.ADD) {
            for (KenkenCell cell : cells) {
                if (cell.isLocked()) {
                    res += cell.getValue();
                    count++;
                }
            }
            if(res > result || (count == cells.size() && res != result)) return false;
        } else if (operation == TypeOperation.SUB) {
            if(abs(cells.get(0).getValue() - cells.get(1).getValue());

        }
        return true;
    }
}
