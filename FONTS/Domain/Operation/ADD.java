package Domain;

import java.util.*;

public class ADD extends Operation {
	
	int checkResult(Kenken kk, KenkenCage kkc) {
		int v = 0;
        for(int i = 0; i < kkc.getCageSize(); ++i) {
            int x = kkc.getPos(i).posX;
            int y = kkc.getPos(i).posY;
            v += kk.getCell(x, y).getValue();
        }
        return v;
	}	
}