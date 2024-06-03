package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;
import Domain.Pos;

/**
 * @author Javier Parcerisas
 */
public class ADD extends Operation {

    /**
	 * Retorna el valor de las casillas operadas segun el tipo de operación de la región,
	 * en este caso una operación de suma.
	 * 
	 * @param kk kenken al que pertenece la región
	 * @param kkc región del kenken donde calculamos
	 */
    @Override
	public int checkResult(Kenken kk, KenkenCage kkc) {
		int v = 0;
        for(int i = 0; i < kkc.getCageSize(); ++i) {
            int x = kkc.getPos(i).posX;
            int y = kkc.getPos(i).posY;
            v += kk.getCell(x, y).getValue();
        }
        return v;
	}

    @Override
    public char getChar() {return '+';}
}