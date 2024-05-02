package Domain;

import java.util.*;

public class MULT extends Operation {
	
	int checkResult(Kenken kk, KenkenCage kkc) {
		int v = 1;
        for(int i = 0; i < kkc.getCageSize(); ++i) {
            Pos p = kkc.getPos(i);
            if(kk.getCell(p).getValue() != 0)
                v *= kk.getCell(p).getValue();
        }
        return v;
	}	
}