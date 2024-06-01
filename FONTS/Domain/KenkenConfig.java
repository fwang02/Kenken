/**
 * @author Javier Parcerisas Nisa
 */
package Domain;

import Domain.Operation.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;


public class KenkenConfig  {

	private final Kenken kenken;
	private boolean filled;
	private int index;
	private boolean twoCellOperatorBool;
	private boolean moreCellOperatorBool;
	private final ArrayList<String> twoCellOperator;
	private final ArrayList<String> moreCellOperator;
	private static final int[] X = {0,1,0,-1};
	private static final int[] Y = {1,0,-1,0};
	private static SecureRandom sr;
	

	public KenkenConfig(Kenken kenken) {
		this.kenken = kenken;
		this.filled = false;
		this.index = 0;
		this.twoCellOperatorBool = false;
		this.moreCellOperatorBool = false;
		this.twoCellOperator = new ArrayList<>();
		this.moreCellOperator = new ArrayList<>();
		sr = new SecureRandom();
	}


	private boolean check(int val, int row, int col) {
		if(!kenken.rowCheck(row, val)) return false;
		else return kenken.colCheck(col, val);
	}

	private void checkOperations(HashSet<Operation> current) {
		for(Operation o: current) {
			if(o instanceof ADD) {moreCellOperator.add("ADD"); twoCellOperator.add("ADD"); moreCellOperatorBool = true; twoCellOperatorBool = true;}
			if(o instanceof MULT) {moreCellOperator.add("MULT"); twoCellOperator.add("MULT"); moreCellOperatorBool = true; twoCellOperatorBool = true;}
			if(o instanceof SUB) {twoCellOperator.add("SUB"); twoCellOperatorBool = true;}
			if(o instanceof DIV) {twoCellOperator.add("DIV"); twoCellOperatorBool = true;}
			if(o instanceof POW) {twoCellOperator.add("POW"); twoCellOperatorBool = true;}
			if(o instanceof MOD) {twoCellOperator.add("MOD"); twoCellOperatorBool = true;}
		}
	}



	// GENERAR Y RESOLVER UN KENKEN v1//

	//esta funcion es un backtracking que rellena la matriz del kenken
	private void fillKenken(int i, int j) {
		if (i == kenken.getSize()) {filled = true;}
		else if (j == kenken.getSize()) {fillKenken(i+1, 0);}
		else {
			Boolean[] tried = new Boolean[kenken.getSize()];
			for(int t = 0; t < kenken.getSize(); ++t) tried[t] = false;

			int tmp = sr.nextInt(kenken.getSize())+1;

			for(int u = 0; u < kenken.getSize() && !filled; ++u) {
				while(tried[tmp-1]) {tmp = sr.nextInt(kenken.getSize())+1;}
				tried[tmp-1] = true;
				if(check(tmp, i, j)) {
					kenken.getCell(i,j).setValue(tmp);
					fillKenken(i, j+1);
				}
			}
			if (!filled) {kenken.getCell(i,j).setValue(0);}
		}
	}

	//esta funcion crea regiones de una sola casilla dependiendo de la dificultad que haya indicado el usuario
	private void fillCellsByDifficulty() {
		int individualCells = 0;
		switch(kenken.getDificult()) {
			case EASY:
				individualCells = (int)(kenken.getSize() * kenken.getSize() * 0.30);
				break;
			case MEDIUM:
				individualCells = (int)(kenken.getSize() * kenken.getSize() * 0.15);
				break;
			case HARD:
				individualCells = (int)(kenken.getSize() * kenken.getSize() * 0.05);
				break;
			case EXPERT:
                break;
		}
		for(int c = 0; c < individualCells; ++c) {

			int tmpX = sr.nextInt(kenken.getSize());
			int tmpY = sr.nextInt(kenken.getSize());

			while(kenken.alreadyInCage(tmpX, tmpY)) {
				tmpX = sr.nextInt(kenken.getSize());
				tmpY = sr.nextInt(kenken.getSize());
			}
			Pos[] tmpPos = {new Pos(tmpX, tmpY)};
			kenken.getCell(tmpX, tmpY).setLocked();
			kenken.addOpCage(new ADD(), 0, tmpPos);
		}
	}

	private boolean stop_2 (double p, int s) {
		if(twoCellOperatorBool) {
			return ((p < sr.nextDouble()) && s < 2);
		}
		else return false;
	}

	private boolean stop_4 (double p, int s) {
		if (moreCellOperatorBool) {
			return ((p < sr.nextDouble()) && s < 4);
		}
		else return false;
	}

	//esta funcion genera las regiones del kenken de forma aleatoria
	private void fillCages() {
		ArrayList<KenkenCell> cageCells;
		double probToStop4 = 0.0;
		double probToStop2 = 0.0;

		for(int i = 0; i < kenken.getSize(); ++i) {
			for(int j = 0; j < kenken.getSize(); ++j) {
				if(!kenken.alreadyInCage(i, j)) {
					
					kenken.getCell(i,j).setLocked();
					cageCells = new ArrayList<>();
					KenkenCell aux = kenken.getCell(i,j);
					cageCells.add(aux);

					while(stop_2(probToStop2, cageCells.size()) || stop_4(probToStop4, cageCells.size())){
						int v = sr.nextInt(4);
						int x = cageCells.get(cageCells.size()-1).getPosX() + X[v];  
						int y = cageCells.get(cageCells.size()-1).getPosY() + Y[v];

						if(x < kenken.getSize() && x >= 0 && y < kenken.getSize() && y >= 0 && !kenken.alreadyInCage(x, y)) {
							kenken.getCell(x,y).setLocked();
							aux = kenken.getCell(x, y);
							cageCells.add(aux);
						}
						probToStop4 += 0.001;
						probToStop2 += 0.000001;
					}

					int s = cageCells.size();
					Pos[] cageCellsPos = new Pos[s];
					for(int ii = 0; ii < s; ++ii) {
						cageCellsPos[ii] = new Pos(cageCells.get(ii).getPosX(), cageCells.get(ii).getPosY());
					}
					kenken.addNoOpCage(cageCellsPos);
				}
			}
		}
	}

	// esta funcion, se necesita previamente haber generado la matriz del kenken y creado las regiones, asigna una operacion y resultado
	// a la region
	private void fillCagesResult() {
		int v1 ,v2, v3, v4 = 0;
		for(int i = 0; i < kenken.getAllCages().size(); ++i) {
			switch(kenken.getCage(i).getCageSize()) {
				case 1:
					kenken.getCage(i).setOperation(new ADD());
					int result_add = kenken.getCell(kenken.getCage(i).getPos(0)).getValue();
					kenken.getCage(i).setResult(result_add);
					break;
				case 2:
					int pos_2 = sr.nextInt(twoCellOperator.size());
					String operator = twoCellOperator.get(pos_2);
					v1 = kenken.getCell(kenken.getCage(i).getPos(0)).getValue();
					v2 = kenken.getCell(kenken.getCage(i).getPos(1)).getValue();
					switch(operator) {
						case "SUB":
							kenken.getCage(i).setOperation(new SUB());
							int result_sub = Math.abs(v1 - v2);
							kenken.getCage(i).setResult(result_sub);
							break;
						case "DIV":
							kenken.getCage(i).setOperation(new DIV());
							int result1_div = v1/v2;
							int result2_div = v2/v1;
							if(result1_div >= 1 && (v1%v2)==0) {
								kenken.getCage(i).setResult(result1_div);
							}
							else {
								if(result2_div >= 1 && (v2%v1) == 0)
									kenken.getCage(i).setResult(result2_div);
								else {
									if(result1_div == 0)kenken.getCage(i).setResult(result1_div);
									else kenken.getCage(i).setResult(result2_div);
								}
							}
							break;
						case "POW":
							kenken.getCage(i).setOperation(new POW());
							int result_pow = (int)Math.pow(v1, v2);
							kenken.getCage(i).setResult(result_pow);
							break;
						case "MOD":
							kenken.getCage(i).setOperation(new MOD());
							int result1_mod = v1%v2;
							int result2_mod = v2%v1;
							if(result1_mod != 0) {
								kenken.getCage(i).setResult(result1_mod);
							}
							else {
								kenken.getCage(i).setResult(result2_mod);
							}
							break;
						case "ADD":
							kenken.getCage(i).setOperation(new ADD());
							int result_add_2 = v1+v2;
							kenken.getCage(i).setResult(result_add_2);
							break;
						case "MULT":
							kenken.getCage(i).setOperation(new MULT());
							int result_mult_2 = v1*v2;
							kenken.getCage(i).setResult(result_mult_2);
							break;
						default:
							break;
					}
					break; 
				case 3:
					int pos_3 = sr.nextInt(moreCellOperator.size());
					String operator_3 = moreCellOperator.get(pos_3);
					v1 = kenken.getCell(kenken.getCage(i).getPos(0)).getValue();
					v2 = kenken.getCell(kenken.getCage(i).getPos(1)).getValue();
					v3 = kenken.getCell(kenken.getCage(i).getPos(2)).getValue();
					switch(operator_3) {
						case "ADD":
							kenken.getCage(i).setOperation(new ADD());
							int result_add_3 = v1+v2+v3;
							kenken.getCage(i).setResult(result_add_3);
							break;
						case "MULT":
							kenken.getCage(i).setOperation(new MULT());
							int result_mult_3 = v1*v2*v3;
							kenken.getCage(i).setResult(result_mult_3);
							break;
					}
					break;
				case 4:
					int pos_4 = sr.nextInt(moreCellOperator.size());
					String operator_4 = moreCellOperator.get(pos_4);
					v1 = kenken.getCell(kenken.getCage(i).getPos(0)).getValue();
					v2 = kenken.getCell(kenken.getCage(i).getPos(1)).getValue();
					v3 = kenken.getCell(kenken.getCage(i).getPos(2)).getValue();
					v4 = kenken.getCell(kenken.getCage(i).getPos(3)).getValue();
					switch(operator_4) {
						case "ADD":
							kenken.getCage(i).setOperation(new ADD());
							int result_add_4 = v1+v2+v3+v4;
							kenken.getCage(i).setResult(result_add_4);
							break;
						case "MULT":
							kenken.getCage(i).setOperation(new MULT());
							int result_mult_4 = v1*v2*v3*v4;
							kenken.getCage(i).setResult(result_mult_4);
							break;

						default:
							break;
					}
					break;
				default:
					break;
			}	
		}
	}

	// esta funcion es un backtracking que resuelve el kenken importado desde fichero u otros lados
	private void solveKenkenByCages(KenkenCage cage, int ii) {
		if (cage.isCageComplete(kenken)) {
			if (cage.checkValueCage(kenken)) {
				++index;
				if (index == kenken.getAllCages().size()) filled = true;
				if (!filled) solveKenkenByCages(kenken.getCage(index), 0);
				--index;
			} else {
				Pos pos = cage.getPos(cage.getCageSize() - 1);
				kenken.getCell(pos).setValue(0);
			}
		} else {
			for (int i = ii; i < cage.getCageSize(); ++i) {
				int x = cage.getPos(i).posX;
				int y = cage.getPos(i).posY;
				if (kenken.getCell(x, y).isLocked()) continue; // if the number is locked go to the next cell
				for (int v = 1; v <= kenken.getSize(); ++v) {
					if (check(v, x, y)) {
						kenken.getCell(x, y).setValue(v);
						solveKenkenByCages(cage, i + 1);
						if (filled) return; // Add this to exit if solution is found
						kenken.getCell(x, y).setValue(0); // Backtrack
					}
				}
				return; // Return only after all values are tried for current cell
			}
		}
	}



	// GENERAR/RESOLVER UN KENKEN //

	//Esta funcion genera un kenken mediante el tamaño, la dificultad y las operaciones indicadas por el usuario
	public void generateKenkenV1() {
		checkOperations(kenken.getOperations());
		fillKenken(0,0);
		fillCellsByDifficulty();
		fillCages();
		fillCagesResult();
	}

	// Esta funcion resuelve un kenken con las regiones previamente definidas
	public boolean solveKenken() {
		try {
			solveKenkenByCages(kenken.getCage(0), 0);
		}
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		return filled;
	}
}
