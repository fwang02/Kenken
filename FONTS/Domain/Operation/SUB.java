/**
 * @author Javier Parcerisas Nisa
 */
package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;

public class SUB extends Operation {
	
	public int checkResult(Kenken kk, KenkenCage kkc) {
		int v1 = kk.getCell(kkc.getPos(0)).getValue();
        int v2 = kk.getCell(kkc.getPos(1)).getValue();
        return Math.abs(v1 - v2);
	}	
}