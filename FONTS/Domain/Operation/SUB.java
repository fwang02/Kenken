package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;
import Domain.Pos;

/**
 * @author Javier Parcerisas Nisa
 */
public class SUB extends Operation {

	/**
	 * Retorna el valor de las casillas operadas segun el tipo de operación de la región,
	 * en este caso una operación de resta.
	 * 
	 * @param kk kenken al que pertenece la región
	 * @param kkc región del kenken donde calculamos
	 */
	@Override
	public int checkResult(Kenken kk, KenkenCage kkc) {
		int v1 = kk.getCell(kkc.getPos(0)).getValue();
        int v2 = kk.getCell(kkc.getPos(1)).getValue();
        return Math.abs(v1 - v2);
	}


	/**
	 * Retorna el operador en forma de carácter
	 */
	@Override
	public char getChar() {return '-';}
}