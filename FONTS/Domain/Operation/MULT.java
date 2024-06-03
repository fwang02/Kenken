package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;
import Domain.Pos;

/**
 * @author Javier Parcerisas
 */
public class MULT extends Operation {

    /**
	 * Retorna el valor de las casillas operadas segun el tipo de operación de la región,
	 * en este caso una operación de multiplicación.
	 * 
	 * @param kk kenken al que pertenece la región
	 * @param kkc región del kenken donde calculamos
	 */
    @Override
	public int checkResult(Kenken kk, KenkenCage kkc) {
		int v = 1;
        for(int i = 0; i < kkc.getCageSize(); ++i) {
            Pos p = kkc.getPos(i);
            if(kk.getCell(p).getValue() != 0)
                v *= kk.getCell(p).getValue();
        }
        return v;
	}


    /**
	 * Retorna el operador en forma de carácter
	 */
    @Override
    public char getChar() {return '*';}
}