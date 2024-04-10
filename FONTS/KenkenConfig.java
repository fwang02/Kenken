import java.util.*;
import java.io.*;


public class KenkenConfig  {

	private Kenken k;
	private boolean end;
	private final ArrayList<TypeOperation> two_cell_operator;
	private final ArrayList<TypeOperation> more_cell_operator;
	private boolean b_two_cell_operator;
	private boolean b_more_cell_operator;
	private int X[] = {0,1,0,-1};
	private int Y[] = {1,0,-1,0};
	

	KenkenConfig(Kenken kk) {
		k = kk;
		end = false;
		two_cell_operator = new ArrayList<TypeOperation>();
		more_cell_operator = new ArrayList<TypeOperation>();
	}


	// AUX FUNCTIONS //

	// esta funcion comprueba que no se repita el numero en filas y columnas
	private boolean check(int tmp, int row, int col) {
		if(!k.rowCheck(row, tmp)) return false;
		else if(!k.colCheck(col, tmp)) return false;
		else return true;
	}

	// esta funcion examina las operaciones que tenemos disponibles para asignarlas y para, por ejemplo, si unicamente tenemos operaciones de dos valores, definir
	// unicamente regiones de tamaño <= 2.
	private void checkOperations(HashSet<TypeOperation> current) {
		if(current.contains(TypeOperation.ADD)) {more_cell_operator.add(TypeOperation.ADD); two_cell_operator.add(TypeOperation.ADD); b_more_cell_operator = true; b_two_cell_operator = true;}
		if(current.contains(TypeOperation.MULT)) {more_cell_operator.add(TypeOperation.MULT); two_cell_operator.add(TypeOperation.MULT); b_more_cell_operator = true; b_two_cell_operator = true;}
		if(current.contains(TypeOperation.SUB))	{two_cell_operator.add(TypeOperation.SUB); b_two_cell_operator = true;}
		if(current.contains(TypeOperation.DIV)) {two_cell_operator.add(TypeOperation.DIV); b_two_cell_operator = true;}
		if(current.contains(TypeOperation.POW)) {two_cell_operator.add(TypeOperation.POW); b_two_cell_operator = true;}
		if(current.contains(TypeOperation.MOD)) {two_cell_operator.add(TypeOperation.MOD); b_two_cell_operator = true;}
	}


	// GENERAR UN KENKEN Y RESOLVER UN KENKEN v1//


	// esta funcion rellena el tablero con numeros sin que se repitan por fila ni por columna
	private void fillKenken(int i, int j) {
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

	// esta funcion añade las casillas individuales dependiendo de la dificultad indicada por el usuario
	private void filldificultCells() {

		int indiv_cells = 0;

		switch(k.getDificult()) {
			case EASY:
				indiv_cells = (int)(k.getSize() * k.getSize() * 0.30);
				break;
			case MEDIUM:
				indiv_cells = (int)(k.getSize() * k.getSize() * 0.15);
				break;
			case HARD:
				indiv_cells = (int)(k.getSize() * k.getSize() * 0.05);
				break;
			case EXPERT:
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
			k.addLockedCage(TypeOperation.ADD, k.getCell(tmp_x, tmp_y).getValue(), tmp_p);
		}
	}

	// esta funcion no llega a servir pero bueno, tipo es necesario pero siempre devolvera el if()
	private boolean stop_2 (double p, int s) {
		if(b_two_cell_operator) {
			return ((p < new Random().nextDouble()) && s < 2);
		}
		else return false;
	}

	// esta funcion sirve para saber si hay operadores de ADD o MULT, si no hay nunca crearemos regiones de tamaño > 2
	private boolean stop_4 (double p, int s) {
		if(b_more_cell_operator) {
			return ((p < new Random().nextDouble()) && s < 4);
		}
		else return false;
	}

	// esta funcion crea las regiones
	private void fillCages() {

		ArrayList<KenkenCell> cageCells;
		double prob_to_stop_4 = 0.0;
		double prob_to_stop_2 = 0.0;

		for(int i = 0; i < k.getSize(); ++i) {
			for(int j = 0; j < k.getSize(); ++j) {
				if(!k.AlreadyInCage(i, j)) {
					
					k.getCell(i,j).setLocked();
					cageCells = new ArrayList<KenkenCell>();
					KenkenCell aux = k.getCell(i,j);
					cageCells.add(aux);

					while(stop_2(prob_to_stop_2, cageCells.size()) || stop_4(prob_to_stop_4, cageCells.size())){
						int m = new Random().nextInt(4);
						int x = cageCells.get(cageCells.size()-1).getPosX() + X[m];  
						int y = cageCells.get(cageCells.size()-1).getPosY() + Y[m];

						if(x < k.getSize() && x >= 0 && y < k.getSize() && y >= 0 && !k.AlreadyInCage(x, y)) {
							k.getCell(x,y).setLocked();
							aux = k.getCell(x, y);
							cageCells.add(aux);
						}
						prob_to_stop_4 += 0.01;
						prob_to_stop_2 += 0.0001;
					}

					int s = cageCells.size();
					Pos[] cageCellsPos = new Pos[s];
					for(int ii = 0; ii < s; ++ii) {
						cageCellsPos[ii] = new Pos(cageCells.get(ii).getPosX(), cageCells.get(ii).getPosY());
					}

					k.addCage(TypeOperation.ADD, 0, cageCellsPos);
				}
			}
		}
	}

	// esta funcion asigna operacion y resultado a las regiones
	private void fillCagesResult() {
		int v1 ,v2, v3, v4 = 0;
		for(int i = 0; i < k.getCages().size(); ++i) {
			if(!k.getCages().get(i).isLocked()) {
				k.getCages().get(i).setLocked();
				switch(k.getCages().get(i).getCageSize()) {
					case 1:
						k.getCages().get(i).setOperation(TypeOperation.ADD);
						int result_add = k.getCell(k.getCages().get(i).getPos(0)).getValue();
						k.getCages().get(i).setResult(result_add);
						break;
					case 2:
						int pos_2 = new Random().nextInt(two_cell_operator.size());
						TypeOperation operator = two_cell_operator.get(pos_2);
						v1 = k.getCell(k.getCages().get(i).getPos(0)).getValue();
						v2 = k.getCell(k.getCages().get(i).getPos(1)).getValue();
						switch(operator) {
							case SUB:
								k.getCages().get(i).setOperation(TypeOperation.SUB);
								int result_sub = Math.abs(v1 - v2);
								k.getCages().get(i).setResult(result_sub);
								break;

							case DIV:
								k.getCages().get(i).setOperation(TypeOperation.DIV);
								int result1_div = v1/v2;
								int result2_div = v2/v1;
								if(result1_div >= 1 && (v1%v2)==0) {
									k.getCages().get(i).setResult(result1_div);
								}
								else {
									k.getCages().get(i).setResult(result2_div);
								}
								break;

							case POW:
								k.getCages().get(i).setOperation(TypeOperation.POW);
								int result_pow = (int)Math.pow(v1, v2);
								k.getCages().get(i).setResult(result_pow);
								break;

							case MOD:
								k.getCages().get(i).setOperation(TypeOperation.MOD);
								int result1_mod = v1%v2;
								int result2_mod = v2%v1;
								if(result1_mod != 0) {
									k.getCages().get(i).setResult(result1_mod);
								}
								else {
									k.getCages().get(i).setResult(result2_mod);
								}
								break;

							case ADD:
								k.getCages().get(i).setOperation(TypeOperation.ADD);
								int result_add_2 = v1+v2;
								k.getCages().get(i).setResult(result_add_2);
								break;

							case MULT:
								k.getCages().get(i).setOperation(TypeOperation.MULT);
								int result_mult_2 = v1*v2;
								k.getCages().get(i).setResult(result_mult_2);
								break;

							default:
								break;
						}
						break; 
					case 3:
						int pos_3 = new Random().nextInt(more_cell_operator.size());
						TypeOperation operator_3 = more_cell_operator.get(pos_3);
						v1 = k.getCell(k.getCages().get(i).getPos(0)).getValue();
						v2 = k.getCell(k.getCages().get(i).getPos(1)).getValue();
						v3 = k.getCell(k.getCages().get(i).getPos(2)).getValue();
						switch(operator_3) {
							case ADD:
								k.getCages().get(i).setOperation(TypeOperation.ADD);
								int result_add_3 = v1+v2+v3;
								k.getCages().get(i).setResult(result_add_3);
								break;

							case MULT:
								k.getCages().get(i).setOperation(TypeOperation.MULT);
								int result_mult_3 = v1*v2*v3;
								k.getCages().get(i).setResult(result_mult_3);
								break;
						}
						break;
					case 4:
						int pos_4 = new Random().nextInt(more_cell_operator.size());
						TypeOperation operator_4 = more_cell_operator.get(pos_4);
						v1 = k.getCell(k.getCages().get(i).getPos(0)).getValue();
						v2 = k.getCell(k.getCages().get(i).getPos(1)).getValue();
						v3 = k.getCell(k.getCages().get(i).getPos(2)).getValue();
						v4 = k.getCell(k.getCages().get(i).getPos(3)).getValue();
						switch(operator_4) {
							case ADD:
								k.getCages().get(i).setOperation(TypeOperation.ADD);
								int result_add_4 = v1+v2+v3+v4;
								k.getCages().get(i).setResult(result_add_4);
								break;

							case MULT:
								k.getCages().get(i).setOperation(TypeOperation.MULT);
								int result_mult_4 = v1*v2*v3*v4;
								k.getCages().get(i).setResult(result_mult_4);
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

	}



	// GENEREAR Y RESOLVER v2 //   <- FALTA ESTE, que es el que se especifica tamaño, operaciones, n regiones y n casillas

	private void fillIndividualCells() {
		int indiv_cells = k.getNICells();
		for(int c = 0; c < indiv_cells; ++c) {

			int tmp_x = new Random().nextInt(k.getSize());
			int tmp_y = new Random().nextInt(k.getSize());

			while(k.AlreadyInCage(tmp_x, tmp_y)) {
				tmp_x = new Random().nextInt(k.getSize());
				tmp_y = new Random().nextInt(k.getSize());
			}
			Pos[] tmp_p = {new Pos(tmp_x, tmp_y)};
			k.getCell(tmp_x, tmp_y).setLocked();
			k.addLockedCage(TypeOperation.ADD, k.getCell(tmp_x, tmp_y).getValue(), tmp_p);
		}
	}

	private void fillNCages() {
		int i_cells = k.getNICells();
		int total_cages = k.getNCages();
		int n_cages = total_cages - i_cells;
		int remaining_cells = (k.getSize()*k.getSize()) - i_cells;
		ArrayList<integer> sizes = new ArrayList<integer>()

		if((b_two_cell_operator && b_more_cell_operator) || (!b_two_cell_operator && b_more_cell_operator)) {	//in this case cages can be size 2,3 or 4

		}
		else if(b_two_cell_operator && !b_more_cell_operator) { //in this case cages can be size 2

		}
	}


	// SOLUCIONAR UN KENKEN MEDIANTE LAS CAJAS (un kenken de fichero) //

	private void fillIndividualCellsFromCages() {
		for(int i = 0; i < k.getCages().size(); ++i) {
			if(k.getCages().get(i).getCageSize() == 1) {
				int v = k.getCages().get(i).getResult();
				int x = k.getCages().get(i).getPos(0).posX;
				int y = k.getCages().get(i).getPos(0).posY;
				k.getCell(x,y).setValue(v);
				k.getCell(x,y).setLocked();
			}
		}
	}

	private void fillKenkenFromCages(int i, int j) {
		if (i == k.getSize()) {end = true;}
		else if (j == k.getSize()) {fillKenkenFromCages(i+1, 0);}
		else if (k.getCell(i,j).isLocked()) {fillKenkenFromCages(i, j+1);}
		else if (k.getCell(i,j).getValue() != 0) {fillKenkenFromCages(i, j+1);}
		else {
			Boolean[] tried = new Boolean[k.getSize()+1];
			for(int t = 0; t <= k.getSize(); ++t) tried[t] = false;

			int tmp = new Random().nextInt(k.getSize())+1;

			for(int u = 0;u < k.getSize() && !end; ++u) {
				while(tried[tmp]) {tmp = new Random().nextInt(k.getSize())+1;}
				tried[tmp] = true;
				if(check(tmp, i, j)) {
					k.getCell(i,j).setValue(tmp);
					KenkenCage kg = k.getCage(i, j);
					if((kg.checkCompleteCage(k) && kg.checkTotalCage(k)) || (!kg.checkCompleteCage(k) && kg.checkPartialCage(k))) {
						fillKenken(i, j+1);
					}
				}
			}

			if (!end) {k.getCell(i,j).setValue(0);}
		}

	}
	

	// ESCRIBIR EL KENKEN POR PANTALLA //

	private void printKenken () {

		System.out.println("Kenken");
		for (int i = 0; i < k.getSize(); ++i) {
			for (int j = 0; j < k.getSize(); ++j) {
				System.out.print(k.getCell(i,j).getValue() + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
		System.out.print("<--## CAGE LIST ##-->\n");
		System.out.print("\n");
		ArrayList<KenkenCage> print_cages = new ArrayList<KenkenCage>();
		print_cages = k.getCages();
		for(int i = 0; i < print_cages.size(); ++i) {
			System.out.print("Cage " + i + "-> ");
			System.out.print("Size: " + print_cages.get(i).getCageSize() + " ");
			System.out.print("Cells: ");
			for(int j = 0; j < print_cages.get(i).getCageSize(); ++j) {
				System.out.print(print_cages.get(i).getPos(j).posX + " ");
				System.out.print(print_cages.get(i).getPos(j).posY + " || ");
			}
			System.out.print(print_cages.get(i).getOperation() + " = ");
			System.out.print(print_cages.get(i).getResult() + "\n");
		}
	}

	

	// GENERAR/RESOLVER UN KENKEN //

	//Esto genera un kenken mediante el tamaño, la dificultad y las operaciones indicadas por el usuario
	public void generateKenkenv1() {
		checkOperations(k.getOperations());
		fillKenken(0,0);
		filldificultCells();
		fillCages();
		fillCagesResult();
	}

	//Esto genera un kenken mediante el tamaño, las operaciones, el numero de regiones y el numero de casillas individuales indicadas por el usuario (NO ACABADO)
	public void generateKenkenv2() {
		checkOperations(k.getOperations());
		//poner funciones aqui
	}

	// Resuelve un kenken con las regiones ya definidas, es decir, uno importado desde fichero
	public void solveKenken() {
		fillIndividualCellsFromCages();
		fillKenkenFromCages(0,0);
	}
}
