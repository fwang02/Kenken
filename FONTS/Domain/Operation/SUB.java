package Domain;

import java.util.*;

public class SUB extends Operation {
	
	int checkResult(Kenken kk, KenkenCage kkc) {
		int v1 = kk.getCell(kkc.getPos(0)).getValue();
        int v2 = kk.getCell(kkc.getPos(1)).getValue();
        return Math.abs(v1 - v2);
	}	
}