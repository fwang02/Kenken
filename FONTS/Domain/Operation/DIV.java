package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;
import Domain.Pos;

/**
 * @author Javier Parcerisas
 */
public class DIV extends Operation {

    /**
	 * Retorna el valor de las casillas operadas segun el tipo de operación de la región,
	 * en este caso una operación de división.
	 * 
	 * @param kk kenken al que pertenece la región
	 * @param kkc región del kenken donde calculamos
	 */
    @Override
	public int checkResult(Kenken kk, KenkenCage kkc) {
		int v1 = kk.getCell(kkc.getPos(0)).getValue();
        int v2 = kk.getCell(kkc.getPos(1)).getValue();
        if((v1/v2) >= 1 && (v1%v2)==0) {return v1/v2;}
        else {return v2/v1;}
	}


    /**
	 * Retorna el operador en forma de carácter
	 */
    @Override
    public char getChar() {return '/';}
}