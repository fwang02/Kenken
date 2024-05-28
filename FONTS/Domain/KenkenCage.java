/**
 * @author Javier Parcerisas Nisa
 */
package Domain;

import Domain.Operation.Operation;

public class KenkenCage {
    private final Pos[] posCells;
    private final int size;
    private Operation operation;
    private int result;

    public KenkenCage() {
        posCells = null;
        size = 0;
        operation = null;
        result = 0;
    }

    public KenkenCage(Operation operation, int result, Pos[] posCells) {
        this.operation = operation;
        this.result = result;
        this.posCells = posCells;
        size = posCells.length;

    }

    KenkenCage(Pos[] posCells) {
        this.operation = null;
        this.result = -1;
        this.posCells = posCells;
        size = posCells.length;
    }

    public int getCageSize() {     
        return size;
    }

    public Pos getPos(int x) {      
        assert posCells != null;
        return posCells[x];
    }

    public void setResult(int x) {
        result = x;
    }

    public int getResult() {
        return result;
    }

    public void setOperation(Operation operator) {
        operation = operator;
    }

    public Operation getOperation() {
        return operation;
    }

    public char getOperationAsChar() {
        return operation.getChar();
    }

    public void clearCage(Kenken kk) {
        for(int i = 0; i < getCageSize(); ++i) {
            Pos pos = getPos(i);
            kk.getCell(pos).setValue(0);
        }
    }

    public boolean isCageComplete(Kenken kk) {
        for(int i = 0; i < getCageSize(); ++i) {
            int x = getPos(i).posX;
            int y = getPos(i).posY;
            if(kk.getCell(x, y).getValue() == 0) return false;
        }
        return true;
    }

    public boolean checkValueCage(Kenken kk) {
        int v = operation.checkResult(kk, this);
        return v == result;

    }
}