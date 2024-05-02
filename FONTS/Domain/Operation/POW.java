package Domain;

import java.util.*;

public class POW extends Operation {
	
	int checkResult(Kenken kk, KenkenCage kkc) {
		int v1 = kk.getCell(kkc.getPos(0)).getValue();
        int v2 = kk.getCell(kkc.getPos(1)).getValue();
        return (int)Math.pow(v1,v2);
	}	
}