package FONTS.src;

import java.util.ArrayList;

public class KenkenCage {
    private ArrayList<KenkenCell> cells;
    private TypeOperation operation;
    private int result;

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
}
