import java.util.ArrayList;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

class Pos {
    int posX;
    int posY;

    public Pos(int x, int y) {
        posX = x;
        posY = y;
    }
}

public class KenkenCage {
    private final Pos[] posCells;
    private final int size;
    private TypeOperation operation;
    private int result;
    private boolean allLocked;
    private Kenken k;

    private boolean locked;     //@javi, hola feiyang. he añadido esto pq no se si podia utilizar otra. Es basicamente para: como primero creo las regiones de casillas individuales
                                //y despues las regiones totales, para no volver a asignarles un resultado y operacion ya que ya lo tienen.

    KenkenCage() {
        posCells = null;
        size = 0;
        operation = null;
        k = null;
        result = 0;
        allLocked = false;

        locked = false;
    }

    KenkenCage(Kenken k, TypeOperation operation, int result) {
        this.k = k;
        posCells = null;
        size = 0;
        this.operation = operation;
        this.result = result;
        allLocked = false;

        locked = false;
    }

    KenkenCage(TypeOperation operation, int result, Pos[] posCells) {
        this.operation = operation;
        this.result = result;
        this.posCells = posCells;
        size = posCells.length;
        allLocked = false;
        k = null;

        locked = false;
    }




    public boolean isCageValid() {
        assert posCells != null;
        assert k != null;
        if(operation == TypeOperation.ADD) {
            int sum = 0;
            for (Pos pos_cell : posCells) {
                if (k.getCell(pos_cell).isLocked()) {
                    sum += k.getCell(pos_cell).getValue();
                }
            }
            if(allLocked) return result == sum;
            else return result > sum;
        } else if (operation == TypeOperation.SUB) {
            if(allLocked) return abs(k.getCell(posCells[0]).getValue() - k.getCell(posCells[1]).getValue()) == result;
        } else if (operation == TypeOperation.MULT) {
            int mult = 1;
            for (Pos pos_cell : posCells) {
                if(k.getCell(pos_cell).isLocked()) {
                    mult = mult * k.getCell(pos_cell).getValue();
                }
            }
            if(allLocked) return result == mult;
            else return result > mult;
        } else if (operation == TypeOperation.DIV) {
            if(allLocked) {
                if(k.getCell(posCells[0]).getValue() > k.getCell(posCells[1]).getValue()) return k.getCell(posCells[0]).getValue() / k.getCell(posCells[1]).getValue() == result;
                else return k.getCell(posCells[1]).getValue() / k.getCell(posCells[0]).getValue() == result;
            }
        } else if (operation == TypeOperation.MOD) {
            if(allLocked) {
                return k.getCell(posCells[0]).getValue() % k.getCell(posCells[1]).getValue() == result || k.getCell(posCells[1]).getValue() % k.getCell(posCells[0]).getValue() == result;
            }
        } else if (operation == TypeOperation.POW) {
            if(allLocked) {
                return pow(k.getCell(posCells[0]).getValue(),k.getCell(posCells[1]).getValue()) == result || pow(k.getCell(posCells[1]).getValue(),k.getCell(posCells[0]).getValue()) == result;
            }
        }
        return true;
    }



    public int getCageSize() {      //@javi, la he añadido para poder consultar el tamaño de la cage
        return size;
    }

    public Pos getPos(int x) {      //@javi, la he añadido para poder consultar el vector de posiciones :D
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

    public boolean isAllLocked() {
        return allLocked;
    }

    public void setOperation(TypeOperation operator) {
        operation = operator;
    }

    public TypeOperation getOperation() {
        return operation;
    }

}