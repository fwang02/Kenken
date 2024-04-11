public class KenkenCage {
    private final Pos[] posCells;
    private final int size;
    private TypeOperation operation;
    private int result;
    private boolean allCellSet;
    private boolean locked;     //@javi, hola feiyang. he añadido esto pq no se si podia utilizar otra. Es basicamente para: como primero creo las regiones de casillas individuales
                                //y despues las regiones totales, para no volver a asignarles un resultado y operacion ya que ya lo tienen.
                                //VIGILA PQ LA CREACIÓN DE CASILLAS DEPENDEN DE ESTE BOOL

    KenkenCage() {
        posCells = null;
        size = 0;
        operation = null;
        result = 0;
        allCellSet = false;
        locked = false;
    }

    KenkenCage(TypeOperation operation, int result, Pos[] posCells) {
        this.operation = operation;
        this.result = result;
        this.posCells = posCells;
        size = posCells.length;
        allCellSet = false;
        locked = false;
    }




    /*
    public boolean isCageValid(Kenken kk) {
        assert posCells != null;
        assert kk != null;
        if(operation == TypeOperation.ADD) {
            int sum = 0;
            for (Pos pos_cell : posCells) {
                if (kk.getCell(pos_cell).isLocked()) {
                    sum += kk.getCell(pos_cell).getValue();
                }
            }
            if(allLocked) return result == sum;
            else return result > sum;
        } else if (operation == TypeOperation.SUB) {
            if(allLocked) return abs(kk.getCell(posCells[0]).getValue() - kk.getCell(posCells[1]).getValue()) == result;
        } else if (operation == TypeOperation.MULT) {
            int mult = 1;
            for (Pos pos_cell : posCells) {
                if(kk.getCell(pos_cell).isLocked()) {
                    mult = mult * kk.getCell(pos_cell).getValue();
                }
            }
            if(allLocked) return result == mult;
            else return result > mult;
        } else if (operation == TypeOperation.DIV) {
            if(allLocked) {
                if(kk.getCell(posCells[0]).getValue() > kk.getCell(posCells[1]).getValue()) return kk.getCell(posCells[0]).getValue() / kk.getCell(posCells[1]).getValue() == result;
                else return kk.getCell(posCells[1]).getValue() / kk.getCell(posCells[0]).getValue() == result;
            }
        } else if (operation == TypeOperation.MOD) {
            if(allLocked) {
                return kk.getCell(posCells[0]).getValue() % kk.getCell(posCells[1]).getValue() == result || kk.getCell(posCells[1]).getValue() % kk.getCell(posCells[0]).getValue() == result;
            }
        } else if (operation == TypeOperation.POW) {
            if(allLocked) {
                return pow(kk.getCell(posCells[0]).getValue(),kk.getCell(posCells[1]).getValue()) == result || pow(kk.getCell(posCells[1]).getValue(),kk.getCell(posCells[0]).getValue()) == result;
            }
        }
        return true;
    }
     */

    public int getCageSize() {      //@javi, la he añadido para poder consultar el tamaño de la cage
        return size;
    }

    public Pos getPos(int x) {      //@javi, la he añadido para poder consultar el vector de posiciones :D
        assert posCells != null;
        return posCells[x];
    }

    public void setResult(int x) {
        result = x;
    }

    public int getResult() {
        return result;
    }

    public void setLocked() {   
        locked = true;
    }

    public void setUnlocked() {
        locked = false;
    }

    public boolean isLocked() {
        return locked;
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

    //@javi, feiyang he añadido esto que me permite solucionar los kenken de los ficheros

    public boolean isCageComplete(Kenken kk) {
        for(int i = 0; i < getCageSize(); ++i) {
            int x = getPos(i).posX;
            int y = getPos(i).posY;
            if(kk.getCell(x, y).getValue() == 0) return false;
        }
        return true;
    }

    public boolean checkTotalCage(Kenken kk) {
        int v = checkResult(kk);
        return v == result;

    }

    public boolean checkPartialCage(Kenken kk) {
        if(operation == TypeOperation.ADD || operation == TypeOperation.MULT) {
            int v = checkResult(kk);
            return v < result;
        }
        return true;
    }


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
            int x = getPos(i).posX;
            int y = getPos(i).posY;
            if(kk.getCell(x, y).getValue() != 0)
                v *= kk.getCell(x, y).getValue();
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

}