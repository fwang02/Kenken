package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;

/**
 * Esta clase indica el tipo de operación según el tipo de instancia de la clase
 * 
 * @author Javier Parcerisas
 */
public abstract class Operation {

	/**
	 * Retorna el valor de las casillas operadas segun el tipo de operación de la región
	 * 
	 * @param kk kenken al que pertenece la región
	 * @param kkc región del kenken donde calculamos
	 */
	public abstract int checkResult(Kenken kk, KenkenCage kkc);


	/**
	 * Retorna el operador en forma de carácter
	 */
	public abstract char getChar();

}