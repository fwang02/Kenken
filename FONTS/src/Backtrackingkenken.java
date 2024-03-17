import java.util.*;
import java.io.*;

public class RandomMatrix {

	int[] matrix[];
	int size;

	RandomMatrix(int size) {
		this.size = size;
		matrix = new int[size][size];
	}

	boolean check(int num, int row, int col) {
		for (int i = 0; i < size; ++i) {
			if (num == matrix[i][col]) return false;
		}
		for (int j = 0; j < size; ++j) {
			if (num == matrix[row][j]) return false;
		}
		return true;
	}

	/*Backtracking hecho por javi. Si podeis probarlo para ver si funciona
	Lo adaptare luego para que utlize las clases y no una matriz por defecto*/

	void fillMatrix(int i, int j) {
		if (i == size) {end = true;}
		else if (j == size) {fillMatrix(i+1, 0);}
		else if (matrix[i][j] != 0) {fillMatrix(i, j+1);}
		else {
			Boolean[] tried = new Boolean[size+1];
			for(int t = 0; t <= size; ++t) tried[t] = false;

			int tmp = new Random().nextInt(size)+1;

			for(int u = 0;u < size && !end; ++u) {
				while(tried[tmp]) {tmp = new Random().nextInt(size)+1;}
				tried[tmp] = true;
				if(check(tmp, i, j)) {
					matrix[i][j] = tmp;
					fillMatrix(i, j+1);
				}
			}
			if (!end) matrix[i][j] = 0;  //vaciamos la casilla si no sirve y se ha llenado
		}
	}

	void printMatrix () {
		System.out.println("Random Matix:");
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
	}

  public static void main(String[] args) {
		int size = Integer.valueOf(args[0]);
		RandomMatrix rm = new RandomMatrix(size);
		rm.fillMatrix();
		rm.printMatrix();
  }
}
