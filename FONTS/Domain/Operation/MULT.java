/**
 * @author Javier Parcerisas Nisa
 */
package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;
import Domain.Pos;

public class MULT extends Operation {

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
}