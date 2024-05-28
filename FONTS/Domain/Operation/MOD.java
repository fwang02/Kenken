package Domain.Operation;

import Domain.Kenken;
import Domain.KenkenCage;

public class MOD extends Operation {

    @Override
	public int checkResult(Kenken kk, KenkenCage kkc) {
		int v1 = kk.getCell(kkc.getPos(0)).getValue();
        int v2 = kk.getCell(kkc.getPos(1)).getValue();
        if((v1%v2) != 0) {return v1%v2;}
        else {return v2%v1;}
	}

    @Override
    public char getChar() {return '%';}
}

