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

    KenkenCage(Operation operation, int result, Pos[] posCells) {
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
    /*
    private int checkResult(Kenken kk) {
        int v = 0;
        switch(operation) {
            case ADD:
                v = calcADD(kk);
                break;
            case MULT:
                v = calcMULT(kk);
                break;
            case SUB:
                v = calcSUB(kk);
                break;
            case DIV:
                v = calcDIV(kk);
                break;
            case POW:
                v = calcPOW(kk);
                break;
            case MOD:
                v = calcMOD(kk);
                break;
        }
        return v;
    }


    private int calcADD(Kenken kk) {
        int v = 0;
        for(int i = 0; i < getCageSize(); ++i) {
            int x = getPos(i).posX;
            int y = getPos(i).posY;
            v += kk.getCell(x, y).getValue();
        }
        return v;
    }

    private int calcMULT(Kenken kk) {
        int v = 1;

        for(int i = 0; i < getCageSize(); ++i) {
            Pos p = getPos(i);
            if(kk.getCell(p).getValue() != 0)
                v *= kk.getCell(p).getValue();
        }
        return v;
    }

    private int calcSUB(Kenken kk) {
        int v1 = kk.getCell(getPos(0)).getValue();
        int v2 = kk.getCell(getPos(1)).getValue();
        return Math.abs(v1 - v2);
    }

    private int calcDIV(Kenken kk) {
        int v1 = kk.getCell(getPos(0)).getValue();
        int v2 = kk.getCell(getPos(1)).getValue();
        if((v1/v2) >= 1 && (v1%v2)==0) {return v1/v2;}
        else {return v2/v1;}
    }

    private int calcPOW(Kenken kk) {
        int v1 = kk.getCell(getPos(0)).getValue();
        int v2 = kk.getCell(getPos(1)).getValue();
        return (int)Math.pow(v1,v2);

    }

    private int calcMOD(Kenken kk) {
        int v1 = kk.getCell(getPos(0)).getValue();
        int v2 = kk.getCell(getPos(1)).getValue();
        if((v1%v2) != 0) {return v1%v2;}
        else {return v2%v1;}
    }
    */
}