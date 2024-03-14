import java.util.*;
import java.io.*;
import java.lang.Math;

public class RandomMatrix {

	int[] matrix[];
	int size;

	RandomMatrix(int size) {
		this.size = size;
		matrix = new int[size][size];
	}

	boolean check(int num, int row, int col) {
		if (num == 0) return false;

		for (int i = 0; i < size; ++i) {
			if (num == matrix[i][col]) return false;
		}
		for (int j = 0; j < size; ++j) {
			if (num == matrix[row][j]) return false;
		}

		return true;
	}

	void initializeMatrix() {
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				matrix[i][j] = 0;
			}
		}
	}

	void fillMatrix () {
		int tmp = 0;
		initializeMatrix();
		for (int i=0; i<size; i++) {
			for (int j = 0; j < size; j++) {
				while(!check(tmp, i, j)) {
					tmp = (int)(Math.random()*(size+1));
				}
				matrix[i][j] = tmp;
			}
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
