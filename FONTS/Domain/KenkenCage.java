package Domain;

public class KenkenCage {
    //private final Pos[] posCells;
    private final KenkenCell[] cells;
    private final int size;
    private TypeOperation operation;
    private int result;
    private boolean allCellSet;

    KenkenCage() {
        cells = null;
        size = 0;
        operation = null;
        result = 0;
        allCellSet = false;
    }

    /*
    KenkenCage(TypeOperation operation, int result, Pos[] posCells) {
        this.operation = operation;
        this.result = result;
        this.posCells = posCells;
        size = posCells.length;
        allCellSet = false;
    }
    */

    // Using Kenken Cells
    KenkenCage(TypeOperation operation, int result, KenkenCell[] cells) {
        this.cells = cells;
        this.operation = operation;
        this.result = result;
        //this.posCells = posCells;
        size = cells.length;
        allCellSet = false;
    }

    public int getCageSize() {
        return size;
    }

    public Pos getPos(int x) {
        assert cells != null;
        return cells[x].getPos();
    }

    public KenkenCell getCell(int i) {
        assert cells != null;
        return cells[i];
    }

    public void setResult(int x) {
        result = x;
    }

    public int getResult() {
        return result;
    }

    public boolean isAllCellSet() {
        return allCellSet;
    }

    public void setOperation(TypeOperation operator) {
        operation = operator;
    }

    public TypeOperation getOperation() {
        return operation;
    }

    public void clearCage(Kenken kk) {
        for(int i = 0; i < getCageSize(); ++i) {
            Pos pos = getPos(i);
            kk.getCell(pos).setValue(0);
        }
    }

    public boolean isCageComplete() {
        /*for(int i = 0; i < getCageSize(); ++i) {
            int x = getPos(i).posX;
            int y = getPos(i).posY;
            if(kk.getCell(x, y).getValue() == 0) return false;
        }*/
        assert cells != null;
        for(int i = 0; i < size; ++i) {
            if(cells[i].getValue() == 0) return false;
        }
        return true;
    }

    public boolean checkValueCage() {
        int v = checkResult();
        return v == result;

    }

    public void setValue(Pos pos, int val) {
        int i = getCellIndex(pos);

        cells[i].setValue(val);
    }

    private int getCellIndex(Pos pos) {
        for (int i = 0; i < size; ++i) {
            if (cells[i].getPos() == pos) return i;
        }
        return 0;
    }

    private int checkResult() {
        int v = 0;
        switch(operation) {
            case ADD:
                v = calcADD();
                break;
            case MULT:
                v = calcMULT();
                break;
            case SUB:
                v = calcSUB();
                break;
            case DIV:
                v = calcDIV();
                break;
            case POW:
                v = calcPOW();
                break;
            case MOD:
                v = calcMOD();
                break;
        }
        return v;
    }


    private int calcADD() {
        int v = 0;
        assert cells != null;
        for(int i = 0; i < size; ++i) {
            v += cells[i].getValue();
        }
        return v;
    }

    private int calcMULT() {
        int v = 1;
        assert cells != null;
        for(int i = 0; i < size; ++i) {
            int tmp = cells[i].getValue();
            if (tmp > 0) v *= tmp;
        }
        return v;
    }

    private int calcSUB() {
        assert cells != null;
        int v1 = cells[0].getValue();
        int v2 = cells[1].getValue();
        return Math.abs(v1 - v2);
    }

    private int calcDIV() {
        assert cells != null;
        int v1 = cells[0].getValue();
        int v2 = cells[1].getValue();
        if((v1/v2) >= 1 && (v1%v2)==0) {return v1/v2;}
        else {return v2/v1;}
    }

    private int calcPOW() {
        assert cells != null;
        int v1 = cells[0].getValue();
        int v2 = cells[1].getValue();
        return (int)Math.pow(v1,v2);

    }

    private int calcMOD() {
        assert cells != null;
        int v1 = cells[0].getValue();
        int v2 = cells[1].getValue();;
        if((v1%v2) != 0) {return v1%v2;}
        else {return v2%v1;}
    }

}