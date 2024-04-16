package Domain;

import java.util.*;

public class KenkenPlay {

	private final Scanner command = new Scanner(System.in);
	private final Kenken solution;
	private final Kenken playground;
	private boolean solved;
	private boolean finish;

	KenkenPlay(Kenken k1, Kenken k2) {
		solution = k1;
		playground = k2;
		solved = false;
		finish = false;
	}


	private void insertNumber(int x, int y, int v) {
		if (x > playground.getSize()-1 || y > playground.getSize()-1 || x < 0 || y < 0) System.out.print("Porfavor, inserte coordenadas validas");
		else if (v > playground.getSize() || v < 1) System.out.print("Porfavor, inserte valores entre 1 i " + playground.getSize());
		else {
			playground.getCell(x, y).setValue(v);
		}
	}

	private void deleteNumber(int x, int y) {
		if (x > playground.getSize()-1 || y > playground.getSize()-1 || x < 0 || y < 0) System.out.print("Porfavor, inserte coordenadas validas");
		else {
			playground.getCell(x, y).setValue(0);
		}
	}

	private void reset() {
		for(int i = 0; i < playground.getSize(); ++i)  {
			for(int j = 0; j < playground.getSize(); ++j) {
				playground.getCell(i,j).setValue(0);
			}
		}
	}

	private void listCells() {
		System.out.print("domain.Kenken\n");
		System.out.print("   ");
		for (int i = 0; i < playground.getSize(); ++i) {
			System.out.print(i + "  ");
		}
		System.out.print("\n");

		for (int i = 0; i < playground.getSize(); ++i) {
			System.out.print(i + " ");
			for (int j = 0; j < playground.getSize(); ++j) {
				System.out.print(" " + playground.getCell(i,j).getValue() + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	private void listCages() {
		ArrayList<KenkenCage> print_cages;
		print_cages = solution.getAllCages();
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

	private void check() {
		int error = 0;
		int correct = 0;
		int undefined = 0;
		for(int i = 0; i < playground.getSize(); ++i) {
			for(int j = 0; j < playground.getSize(); ++j) {
				int v1 = playground.getCell(i, j).getValue();
				if(v1 == 0) ++undefined;
				if(v1 != 0) {
					int v2 = solution.getCell(i, j).getValue();
					if(v1 != v2) {System.out.print("Valor incorrecto en x: " + i + " y: " + j); ++error;}
					else {++correct;}
				}
			}
		}
		if(error == 0 && correct == (playground.getSize()*playground.getSize()) && undefined == 0) {solved = true;}
	}

	private void showSolution() {
		System.out.println("Solucion del domain.Kenken");
		for (int i = 0; i < solution.getSize(); ++i) {
			for (int j = 0; j < solution.getSize(); ++j) {
				System.out.print(solution.getCell(i,j).getValue() + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	private void help() {
		System.out.print("1. casillas -> muestra el tablero con las casillas\n");
		System.out.print("2. regiones -> muestra las regiones del tablero\n");
		System.out.print("3. comprueba -> comprueba los valores añadidos\n");
		System.out.print("4. añadir -> añade un valor a las coordenadas X Y del tablero\n");
		System.out.print("5. borrar -> borra el valor de las coordenadas X Y del tablero\n");
		System.out.print("6. reset -> pone todas las casillas a 0\n");
		System.out.print("7. stop -> abandona la partida y sientete como un perdedor\n");
		System.out.print("8. help -> lista los comandos disponibles\n");	
	}

	private void stop() {
		finish = true;
	}


	public void start() {

		System.out.print("¡Hola!, estos son los comandos disponibles: Puedes insertar el numero de orden o la palabra clave.\n");
		System.out.print("1. casillas -> muestra el tablero con las casillas\n");
		System.out.print("2. regiones -> muestra las regiones del tablero\n");
		System.out.print("3. comprueba -> comprueba los valores añadidos\n");
		System.out.print("4. añadir -> añade un valor a las coordenadas X Y del tablero\n");
		System.out.print("5. borrar -> borra el valor de las coordenadas X Y del tablero\n");
		System.out.print("6. reset -> pone todas las casillas a 0\n");
		System.out.print("7. stop -> abandona la partida y sientete como un perdedor\n");
		System.out.print("8. solucion -> muestra la solucion\n");
		System.out.print("9. help -> lista los comandos disponibles\n");

		while(!solved && !finish) {
			String order = command.nextLine();

			if(order.contains("1") || order.contains("casillas")) {listCells();}
			else if(order.contains("2") || order.contains("regiones")) {listCages();}
			else if(order.contains("3") || order.contains("comprueba")) {check();}

			else if(order.contains("4") || order.contains("añadir")) {
				System.out.println("Inserte los valores de uno en uno tal que: X, Y, valor");
				int a = command.nextInt();
				int b = command.nextInt();
				int v = command.nextInt();
				insertNumber(a,b,v);

			}
			else if(order.contains("5") || order.contains("borrar")) {
				System.out.println("Inserte los valores de uno en uno tal que: X, Y");
				int a = command.nextInt();
				int b = command.nextInt();
				deleteNumber(a,b);
			}

			else if(order.contains("6") || order.contains("reset")) {reset();}
			else if(order.contains("7") || order.contains("stop")) {stop();}
			else if(order.contains("8") || order.contains("solucion")) {showSolution();}
			else if(order.contains("9") || order.contains("help")) {help();}

		}
	}
}