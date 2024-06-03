package Domain;

import Domain.Operation.Operation;

/**
 * Esta clase representa una región de un Kenken. Contiene el tipo de operación
 * de la región, el tamaño de la región, el resultado de la región y las posiciones
 * de las casillas que pertenecen a la región.
 * 
 * @author Javier Parcerisas
 */
public class KenkenCage {
    private final Pos[] posCells;
    private final int size;
    private Operation operation;
    private int result;


    /**
	 * Constructora de la clase KenkenCage
	 */
    public KenkenCage() {
        posCells = null;
        size = 0;
        operation = null;
        result = 0;
    }


    /**
	 * Constructora de la clase KenkenCage que crea una región con operación, resultado
     * y casillas asignadas.
     * 
     * @param operation tipo de operación de la región
     * @param result resultado de la región
     * @param posCells posiciones de las casillas que pertenecen a la región
	 */
    public KenkenCage(Operation operation, int result, Pos[] posCells) {
        this.operation = operation;
        this.result = result;
        this.posCells = posCells;
        size = posCells.length;

    }

    /**
	 * Constructora de la clase KenkenCage que crea una región con casillas pero 
     * sin operación ni resultado
     * 
     * @param posCells posiciones de las casillas que pertenecen a la región
	 */
    public KenkenCage(Pos[] posCells) {
        this.operation = null;
        this.result = -1;
        this.posCells = posCells;
        size = posCells.length;
    }


    /**
     * Getter para obtener el tamaño de la región
     * 
     * @return número de casillas de una región
	 */
    public int getCageSize() {     
        return size;
    }


    /**
     * Getter para obtener la posición de la casilla 
     * en la posición x
     * 
     * @param x indica la posición de la casilla del vector Pos[]
     * @return posición de la casilla
	 */
    public Pos getPos(int x) {      
        assert posCells != null;
        return posCells[x];
    }


    /**
     * Setter para asignar el resultado de una región
     * 
     * @param x indica el resultado que se asigna a la región
	 */
    public void setResult(int x) {
        result = x;
    }

    /**
     * Getter para obtener el resultado de la región
     * 
     * @return devuelve el resultado de la región
	 */
    public int getResult() {
        return result;
    }

    /**
     * Setter para asignar una operación a la región
     * 
     * @param operator indica la operación a asignar
	 */
    public void setOperation(Operation operator) {
        operation = operator;
    }


    /**
     * Getter para obtener la operación de la región
     * 
     * @return devuelve la operación de la región
	 */
    public Operation getOperation() {
        return operation;
    }


    /**
     * Getter para obtener el carácter que representa la
     * operación de una región
     * 
     * @return carácter que equivale a la operación 
	 */
    public char getOperationAsChar() {
        return operation.getChar();
    }


    /**
     * Función para limpiar las casillas que pertenecen a la región
     * 
     * @param kk kenken al que pertenece la región
	 */
    public void clearCage(Kenken kk) {
        for(int i = 0; i < getCageSize(); ++i) {
            Pos pos = getPos(i);
            kk.getCell(pos).setValue(0);
        }
    }

    /**
     * Función para saber si todas las casillas de la región tienen un
     * valor asignado
     * 
     * @param kk kenken al que pertenece la región
     * @return cierto si todas las casillas tienen algún valor o falso en caso contrario
	 */
    public boolean isCageComplete(Kenken kk) {
        for(int i = 0; i < getCageSize(); ++i) {
            int x = getPos(i).posX;
            int y = getPos(i).posY;
            if(kk.getCell(x, y).getValue() == 0) return false;
        }
        return true;
    }


    /**
     * Función para comprobar si los valores actuales en las casillas tienen como
     * resultado si se operan entre ellos el resultado supuesto por la región
     * 
     * @param kk kenken al que pertenece la región
     * @return cierto si el resultado coincide o falso en caso contrario
	 */
    public boolean checkValueCage(Kenken kk) {
        int v = operation.checkResult(kk, this);
        return v == result;

    }
}