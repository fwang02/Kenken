import java.util.*;
import java.io.*;

public class KenkenPlay  {

	private Kenken k;
	private boolean end = false;
	private int indiv_cells;
	private int X[] = {0,1,0,-1};
	private int Y[] = {1,0,-1,0};
	


	KenkenPlay(Kenken k) {
		this.k = k;
	}

	private boolean check(int tmp, int row, int col) {
		if(!k.rowCheck(row, tmp)) return false;
		else if(!k.colCheck(col, tmp)) return false;
		else return true;
	}

	public void fillKenken(int i, int j) {
		if (i == k.getSize()) {end = true;}
		else if (j == k.getSize()) {fillKenken(i+1, 0);}
		else if (k.getCell(i,j).getValue() != -1) {fillKenken(i, j+1);}
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

			if (!end) {k.getCell(i,j).setValue(-1);}
		}
	}

	public void dificultCells() {
		indiv_cells = 0;
		switch(k.getDificult()) {
			case EASY:
				indiv_cells = (int)(k.getSize() * k.getSize() * 0.5);
				break;
			case MEDIUM:
				indiv_cells = (int)(k.getSize() * k.getSize() * 0.3);
				break;
			case HARD:
				indiv_cells = (int)(k.getSize() * k.getSize() * 0.1);
				break;
			case EXPERT:
				indiv_cells = 0;
				break;
		}
		for(int c = 0; c < indiv_cells; ++c) {
			int tmp_x = new Random().nextInt(k.getSize());
			int tmp_y = new Random().nextInt(k.getSize());

			while(k.AlreadyInCage(tmp_x, tmp_y)) {
				tmp_x = new Random().nextInt(k.getSize());
				tmp_y = new Random().nextInt(k.getSize());
			}
			Pos[] tmp_p = {new Pos(tmp_x, tmp_y)};
			k.getCell(tmp_x, tmp_y).setLocked();
			k.addCage(TypeOperation.ADD, k.getCell(tmp_x, tmp_y).getValue(), tmp_p);
		}
	}

	
	public void fillCages() {

	}
	

	public void printKenken () {
		System.out.println("Kenken");
		for (int i = 0; i < k.getSize(); ++i) {
			for (int j = 0; j < k.getSize(); ++j) {
				System.out.print(k.getCell(i,j).getValue() + " ");
			}
			System.out.print("\n");
		}

		System.out.print("\n");
		System.out.print("###################################\n");
		System.out.print("\n");

		ArrayList<KenkenCage> print_cages = new ArrayList<KenkenCage>();
		print_cages = k.getCages();
		for(int i = 0; i < print_cages.size(); ++i) {
			for(int j = 0; j < print_cages.get(i).getCageSize(); ++j) {
				System.out.print(print_cages.get(i).getPos(j).posX + " ");
				System.out.println(print_cages.get(i).getPos(j).posY);
			}
		}

	}

	public void generateKenken() {
		fillKenken(0,0);
		dificultCells();
		fillCages();
		printKenken();
	}
}
