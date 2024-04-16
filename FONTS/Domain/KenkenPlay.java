package Domain;

import java.util.*;

public class KenkenPlay {

	private final Scanner command = new Scanner(System.in);
	private final Kenken solution;
	private int[][] board;
	private final int MaxValue;
	private final int MinValue;
	private final int MaxCoord;
	private final int MinCoord;
	private int hints;
	private boolean solved;
	private boolean finish;

	KenkenPlay(Kenken solution) {
		this.solution = solution;
		this.board = new int[solution.getSize()][solution.getSize()];
		this.MaxValue = solution.getSize();
		this.MinValue = 1;
		this.MaxCoord = solution.getSize();
		this.MinCoord = 1;
		this.hints = 0;
		this.solved = false;
		this.finish = false;
	}


	private void insertNumber(int x, int y, int v) {
		if (x > MaxCoord || y > MaxCoord || x < MinCoord || y < MinCoord) System.out.print("Porfavor, inserte coordenadas validas");
		else if (v > MaxValue || v < MinValue) System.out.print("Porfavor, inserte valores entre 1 i " + MaxValue);
		else {
			board[x-1][y-1] = v;
		}
	}

	private void deleteNumber(int x, int y) {
		if (x > MaxCoord || y > MaxCoord || x < MinCoord || y < MinCoord) System.out.print("Porfavor, inserte coordenadas validas");
		else {
			board[x-1][y-1] = 0;
		}
	}

	private void reset() {
		for(int i = 0; i < MaxValue; ++i)  {
			for(int j = 0; j < MaxValue; ++j) {
				board[i][j] = 0;
			}
		}
	}

	private void listCells() {
		System.out.print("domain.Kenken\n");
		System.out.print("   ");
		for (int i = 0; i < MaxValue; ++i) {
			System.out.print(i+1 + "  ");
		}
		System.out.print("\n");

		for (int i = 0; i < MaxValue; ++i) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < MaxValue; ++j) {
				System.out.print(" " + board[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	private void listCages() {
		ArrayList<KenkenCage> solutionCages;
		solutionCages = solution.getAllCages();
		for(int i = 0; i < solutionCages.size(); ++i) {
			System.out.print("Cage " + i + "-> ");
			System.out.print("Size: " + solutionCages.get(i).getCageSize() + " ");
			System.out.print("Cells: ");
			for(int j = 0; j < solutionCages.get(i).getCageSize(); ++j) {
				System.out.print(solutionCages.get(i).getPos(j).posX + " ");
				System.out.print(solutionCages.get(i).getPos(j).posY + " || ");
			}
			System.out.print(solutionCages.get(i).getOperation() + " = ");
			System.out.print(solutionCages.get(i).getResult() + "\n");
		}
	}

	private void hint() {
		for(int i = 0; i < MaxValue; ++i) {
			for(int j = 0; j < MaxValue; ++j) {
				if(board[i][j] == 0) {
					int v = solution.getCell(i,j).getValue();
					++hints;
					int x = i + 1;
					int y = j + 1;
					System.out.println("Prueba con poner " + v + " en la casilla x: " + x + " y: " + y + " Puede que funcione");
				}
			}
		}
	}

	private void check() {
		int incorrect = 0;
		int correct = 0;
		int undefined = 0;
		for(int i = 0; i < MaxValue; ++i) {
			for(int j = 0; j < MaxValue; ++j) {
				if(board[i][j] == 0) ++undefined;
				if(board[i][j] != 0) {
					int correctValue = solution.getCell(i, j).getValue();
					int x = i + 1;
					int y = j + 1;
					if(board[i][j] != correctValue) {System.out.print("Valor incorrecto en x: " + x + " y: " + y +"\n"); ++incorrect;}
					else {++correct;}
				}
			}
		}
		if(incorrect == 0 && correct == (MaxValue*MaxValue) && undefined == 0) {solved = true;}
	}

	private void showSolution() {
		System.out.println("Solucion del Kenken");
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
		System.out.print("7. pista -> pide una pista\n");
		System.out.print("8. salir -> abandona la partida y sientete como un perdedor\n");
		System.out.print("9. solucion -> muestra la solucion\n");
		System.out.print("10. help -> lista los comandos disponibles\n");	
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
		System.out.print("7. pista -> pide una pista\n");
		System.out.print("8. salir -> abandona la partida y sientete como un perdedor\n");
		System.out.print("9. solucion -> muestra la solucion\n");
		System.out.print("10. help -> lista los comandos disponibles\n");

		String order;
		int a, b, v;
		while(!solved && !finish) {
			order = command.nextLine();
			switch(order) {
				case "1": case "casillas":
					listCells();
					break;
				case "2": case "regiones":
					listCages();
					break;
				case "3": case "comprueba":
					check();
					break;
				case "4": case "añadir":
					System.out.println("Inserte los valores de uno en uno tal que: X, Y, valor");
					a = command.nextInt();
					b = command.nextInt();
					v = command.nextInt();
					insertNumber(a,b,v);
					break;
				case "5": case "borrar":
					System.out.println("Inserte los valores de uno en uno tal que: X, Y");
					a = command.nextInt();
					b = command.nextInt();
					deleteNumber(a,b);
				case "6": case "reset":
					reset();
					break;
				case "7": case "pista":
					hint();
					break;
				case "8": case "salir":
					stop();
					break;
				case "9": case "solucion":
					showSolution();
					break;
				case "10": case "help":
					help();
					break;

			}	
		}
	}
}