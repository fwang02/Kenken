package FONTS.src;

import java.util.*;
import java.io.*;

public class KenkenPlay  {

	private Kenken k;	
	private boolean end = false;

	private boolean check(int tmp, int row, int col) {
		if(!k.rowCheck(row, tmp)) return false;
		else if(!k.colCheck(col, tmp)) return false;
		else return true;
	}

	public void fillKenken(int i, int j) {
		if (i == k.getSize()) {end = true;}
		else if (j == k.getSize()) {fillKenken(i+1, 0);}
		else if (k.getCell(i,j).getValue() != 0) {fillKenken(i, j+1);}
		else {
			Boolean[] tried = new Boolean[k.getSize()+1];
			for(int t = 0; t <= k.getSize(); ++t) tried[t] = false;

			int tmp = new Random().nextInt(k.getSize())+1;

			for(int u = 0;u < k.getSize() && !end; ++u) {
				while(tried[tmp]) {tmp = new Random().nextInt(k.getSize())+1;}
				tried[tmp] = true;
				if(check(tmp, i, j)) {
					k.getCell(i,j).setValue(tmp);
					fillKenken(i, j+1);
				}
			}
			if (!end) {k.getCell(i,j).setValue(0);}
		}
	}


	public void printKenken () {
		System.out.println("Kenken");
		for (int i = 0; i < k.getSize(); ++i) {
			for (int j = 0; j < k.getSize(); ++j) {
				System.out.print(k.getCell(i,j).getValue() + " ");
			}
			System.out.println("");
		}
	}
}
