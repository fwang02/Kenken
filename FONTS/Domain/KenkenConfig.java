package Domain;

import java.util.*;


public class KenkenConfig  {

	private final Kenken kenken;
	private boolean filled;
	private int index;
	private final ArrayList<TypeOperation> two_cell_operator;
	private final ArrayList<TypeOperation> more_cell_operator;
	private boolean twoCellOperatorBool;
	private boolean moreCellOperatorBool;
	private static final int[] X = {0,1,0,-1};
	private static final int[] Y = {1,0,-1,0};
	

	KenkenConfig(Kenken kk) {
		this.kenken = kk;
		this.filled = false;
		this.index = 0;
		this.two_cell_operator = new ArrayList<>();
		this.more_cell_operator = new ArrayList<>();
	}


	// AUX FUNCTIONS //

	// esta funcion comprueba que no se repita el numero en filas y columnas
	private boolean check(int val, int row, int col) {
		if(!kenken.rowCheck(row, val)) return false;
		else return kenken.colCheck(col, val);
	}

	// esta funcion examina las operaciones que tenemos disponibles para asignarlas y para, por ejemplo, si unicamente tenemos operaciones de dos valores, definir
	// unicamente regiones de tamaño <= 2.
	private void checkOperations(HashSet<TypeOperation> current) {
		if(current.contains(TypeOperation.ADD)) {more_cell_operator.add(TypeOperation.ADD); two_cell_operator.add(TypeOperation.ADD); moreCellOperatorBool = true; twoCellOperatorBool = true;}
		if(current.contains(TypeOperation.MULT)) {more_cell_operator.add(TypeOperation.MULT); two_cell_operator.add(TypeOperation.MULT); moreCellOperatorBool = true; twoCellOperatorBool = true;}
		if(current.contains(TypeOperation.SUB))	{two_cell_operator.add(TypeOperation.SUB); twoCellOperatorBool = true;}
		if(current.contains(TypeOperation.DIV)) {two_cell_operator.add(TypeOperation.DIV); twoCellOperatorBool = true;}
		if(current.contains(TypeOperation.POW)) {two_cell_operator.add(TypeOperation.POW); twoCellOperatorBool = true;}
		if(current.contains(TypeOperation.MOD)) {two_cell_operator.add(TypeOperation.MOD); twoCellOperatorBool = true;}
	}



	// GENERAR Y RESOLVER UN KENKEN v1//

	// esta funcion rellena el tablero con numeros sin que se repitan por fila ni por columna
	private void fillKenken(int i, int j) {
		if (i == kenken.getSize()) {filled = true;}
		else if (j == kenken.getSize()) {fillKenken(i+1, 0);}
		//else if (k.getCell(i,j).getValue() != 0) {fillKenken(i, j+1);}
		else {
			Boolean[] tried = new Boolean[kenken.getSize()+1];
			for(int t = 0; t <= kenken.getSize(); ++t) tried[t] = false;

			int tmp = new Random().nextInt(kenken.getSize())+1;

			for(int u = 0; u < kenken.getSize() && !filled; ++u) {
				while(tried[tmp]) {tmp = new Random().nextInt(kenken.getSize())+1;}
				tried[tmp] = true;
				if(check(tmp, i, j)) {
					kenken.getCell(i,j).setValue(tmp);
					fillKenken(i, j+1);
				}
			}
			if (!filled) {kenken.getCell(i,j).setValue(0);}
		}
	}

	// esta funcion añade las casillas individuales dependiendo de la dificultad indicada por el usuario
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

			int tmpX = new Random().nextInt(kenken.getSize());
			int tmpY = new Random().nextInt(kenken.getSize());

			while(kenken.alreadyInCage(tmpX, tmpY)) {
				tmpX = new Random().nextInt(kenken.getSize());
				tmpY = new Random().nextInt(kenken.getSize());
			}
			Pos[] tmpPos = {new Pos(tmpX, tmpY)};
			kenken.getCell(tmpX, tmpY).setLocked();
			kenken.addCage(TypeOperation.ADD, 0, tmpPos);
		}
	}

	// esta funcion no llega a servir pero bueno, tipo es necesario pero siempre devolvera el if()
	private boolean stop_2 (double p, int s) {
		if(twoCellOperatorBool) {
			return ((p < new Random().nextDouble()) && s < 2);
		}
		else return false;
	}

	// esta funcion sirve para saber si hay operadores de ADD o MULT, si no hay nunca crearemos regiones de tamaño > 2
	private boolean stop_4 (double p, int s) {
		if (moreCellOperatorBool) {
			return ((p < new Random().nextDouble()) && s < 4);
		}
		else return false;
	}

	// esta funcion crea las regiones
	private void fillCages() {

		ArrayList<KenkenCell> cageCells;
		double probToStop4 = 0.0;
		double probToStop2 = 0.0;

		for(int i = 0; i < kenken.getSize(); ++i) {
			for(int j = 0; j < kenken.getSize(); ++j) {
				if(!kenken.alreadyInCage(i, j)) {
					
					kenken.getCell(i,j).setLocked();
					cageCells = new ArrayList<KenkenCell>();
					KenkenCell aux = kenken.getCell(i,j);
					cageCells.add(aux);

					while(stop_2(probToStop2, cageCells.size()) || stop_4(probToStop4, cageCells.size())){
						int v = new Random().nextInt(4);
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
					kenken.addCage(TypeOperation.ADD, 0, cageCellsPos);
				}
			}
		}
	}

	// esta funcion asigna operacion y resultado a las regiones
	private void fillCagesResult() {
		int v1 ,v2, v3, v4 = 0;
		for(int i = 0; i < kenken.getAllCages().size(); ++i) {
			kenken.getCage(i).setLocked();
			switch(kenken.getCage(i).getCageSize()) {
				case 1:
					kenken.getCage(i).setOperation(TypeOperation.ADD);
					int result_add = kenken.getCell(kenken.getCage(i).getPos(0)).getValue();
					kenken.getCage(i).setResult(result_add);
					break;
				case 2:
					int pos_2 = new Random().nextInt(two_cell_operator.size());
					TypeOperation operator = two_cell_operator.get(pos_2);
					v1 = kenken.getCell(kenken.getCage(i).getPos(0)).getValue();
					v2 = kenken.getCell(kenken.getCage(i).getPos(1)).getValue();
					switch(operator) {
						case SUB:
							kenken.getCage(i).setOperation(TypeOperation.SUB);
							int result_sub = Math.abs(v1 - v2);
							kenken.getCage(i).setResult(result_sub);
							break;
						case DIV:
							kenken.getCage(i).setOperation(TypeOperation.DIV);
							int result1_div = v1/v2;
							int result2_div = v2/v1;
							if(result1_div >= 1 && (v1%v2)==0) {
								kenken.getCage(i).setResult(result1_div);
							}
							else {
								kenken.getCage(i).setResult(result2_div);
							}
							break;
						case POW:
							kenken.getCage(i).setOperation(TypeOperation.POW);
							int result_pow = (int)Math.pow(v1, v2);
							kenken.getCage(i).setResult(result_pow);
							break;
						case MOD:
							kenken.getCage(i).setOperation(TypeOperation.MOD);
							int result1_mod = v1%v2;
							int result2_mod = v2%v1;
							if(result1_mod != 0) {
								kenken.getCage(i).setResult(result1_mod);
							}
							else {
								kenken.getCage(i).setResult(result2_mod);
							}
							break;
						case ADD:
							kenken.getCage(i).setOperation(TypeOperation.ADD);
							int result_add_2 = v1+v2;
							kenken.getCage(i).setResult(result_add_2);
							break;
						case MULT:
							kenken.getCage(i).setOperation(TypeOperation.MULT);
							int result_mult_2 = v1*v2;
							kenken.getCage(i).setResult(result_mult_2);
							break;
						default:
							break;
					}
					break; 
				case 3:
					int pos_3 = new Random().nextInt(more_cell_operator.size());
					TypeOperation operator_3 = more_cell_operator.get(pos_3);
					v1 = kenken.getCell(kenken.getCage(i).getPos(0)).getValue();
					v2 = kenken.getCell(kenken.getCage(i).getPos(1)).getValue();
					v3 = kenken.getCell(kenken.getCage(i).getPos(2)).getValue();
					switch(operator_3) {
						case ADD:
							kenken.getCage(i).setOperation(TypeOperation.ADD);
							int result_add_3 = v1+v2+v3;
							kenken.getCage(i).setResult(result_add_3);
							break;
						case MULT:
							kenken.getCage(i).setOperation(TypeOperation.MULT);
							int result_mult_3 = v1*v2*v3;
							kenken.getCage(i).setResult(result_mult_3);
							break;
					}
					break;
				case 4:
					int pos_4 = new Random().nextInt(more_cell_operator.size());
					TypeOperation operator_4 = more_cell_operator.get(pos_4);
					v1 = kenken.getCell(kenken.getCage(i).getPos(0)).getValue();
					v2 = kenken.getCell(kenken.getCage(i).getPos(1)).getValue();
					v3 = kenken.getCell(kenken.getCage(i).getPos(2)).getValue();
					v4 = kenken.getCell(kenken.getCage(i).getPos(3)).getValue();
					switch(operator_4) {
						case ADD:
							kenken.getCage(i).setOperation(TypeOperation.ADD);
							int result_add_4 = v1+v2+v3+v4;
							kenken.getCage(i).setResult(result_add_4);
							break;
						case MULT:
							kenken.getCage(i).setOperation(TypeOperation.MULT);
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



	// GENEREAR Y RESOLVER v2 //   <- FALTA ESTE, que es el que se especifica tamaño, operaciones, n regiones y n casillas

	private void generateKenkenFromCages() {
		int size = kenken.getSize();
		int ncages = kenken.getNCages();

		int randX, randY;
		randX = new Random().nextInt(size);
		randY = new Random().nextInt(size);

		// generar regiones aleatorias
		for (int i = 0; i < ncages; ++i) {
			// cambiar posicion hasta que haya alguna disponible
			while (kenken.getCage(randX, randY) != null) {
				randX = new Random().nextInt(size);
				randY = new Random().nextInt(size);
			}
			Pos[] iniCell = new Pos[1];
			iniCell[0] = new Pos(randX, randY);
			kenken.addCage(TypeOperation.ADD, 0, iniCell);
		}

	}

	private void fillIndividualCells() {
		int indiv_cells = kenken.getNICells();
		for(int c = 0; c < indiv_cells; ++c) {

			int tmp_x = new Random().nextInt(kenken.getSize());
			int tmp_y = new Random().nextInt(kenken.getSize());

			while(kenken.alreadyInCage(tmp_x, tmp_y)) {
				tmp_x = new Random().nextInt(kenken.getSize());
				tmp_y = new Random().nextInt(kenken.getSize());
			}
			Pos[] tmp_p = {new Pos(tmp_x, tmp_y)};
			kenken.getCell(tmp_x, tmp_y).setLocked();
			kenken.addLockedCage(TypeOperation.ADD, kenken.getCell(tmp_x, tmp_y).getValue(), tmp_p);
		}
	}


	


	// SOLUCIONAR UN KENKEN MEDIANTE LAS CAJAS (un kenken de fichero) //

    private void fillKenkenByCages(KenkenCage cage, int ii) {
        if (cage.isCageComplete(kenken)) {
            if (cage.checkValueCage(kenken)) {
            	++index;
            	if(index == kenken.getAllCages().size()) filled = true;
                if(!filled) fillKenkenByCages(kenken.getCage(index), 0);
                --index; 
            }
            else {
            	Pos pos = cage.getPos(cage.getCageSize()-1);
            	kenken.getCell(pos).setValue(0);
            	return;
            }
        } 
        else {
            for (int i = ii; i < cage.getCageSize(); ++i) {
                int x = cage.getPos(i).posX;
                int y = cage.getPos(i).posY;
                for (int v = 1; v <= kenken.getSize(); ++v) {
                    if (check(v, x, y)) {
                        kenken.getCell(x, y).setValue(v);
                        fillKenkenByCages(cage, i + 1);
                    }
                }

                if(!filled) kenken.getCell(x,y).setValue(0);
                return;
            }
        }
    }
		

	// GENERAR/RESOLVER UN KENKEN //

	//Esto genera un kenken mediante el tamaño, la dificultad y las operaciones indicadas por el usuario
	public void generateKenkenv1() {
		checkOperations(kenken.getOperations());
		fillKenken(0,0);
		fillCellsByDifficulty();
		fillCages();
		fillCagesResult();
	}

	//Esto genera un kenken mediante el tamaño, las operaciones, el numero de regiones y el numero de casillas individuales indicadas por el usuario (NO ACABADO)
	public void generateKenkenv2() {
		checkOperations(kenken.getOperations());
		fillKenken(0, 0);
		//funcion donde el kenken cree las regiones antes que los numeros
		generateKenkenFromCages();

	}

	// Resuelve un kenken con las regiones ya definidas, es decir, uno importado desde fichero
	public void solveKenken() {
		fillKenkenByCages(kenken.getCage(0), 0);
	}


}
