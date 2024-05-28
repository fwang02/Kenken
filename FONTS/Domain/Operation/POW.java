/**
 * @author Javier Parcerisas Nisa
 */
package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;

public class POW extends Operation {

	@Override
	public int checkResult(Kenken kk, KenkenCage kkc) {
		int v1 = kk.getCell(kkc.getPos(0)).getValue();
        int v2 = kk.getCell(kkc.getPos(1)).getValue();
        return (int)Math.pow(v1,v2);
	}

	@Override
	public char getChar() {return '^';}
}