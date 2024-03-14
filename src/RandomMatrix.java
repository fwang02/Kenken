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

	void fillMatrix () {
		Vector<Boolean> used = new Vector<>();
		for(int k = 0; k <= size; ++k) used.add(k, false);
		int tmp = 0;
		for (int i=0; i<size; i++) {
			for(int k = 0; k <= size; ++k) used.set(k, false);
			for (int j = 0; j < size; j++) {
				tmp = new Random().nextInt(size)+1;
				while(used.get(tmp)) {tmp = new Random().nextInt(size)+1;}
				used.set(tmp, true);
				while(!check(tmp, i, j)) {
					tmp = new Random().nextInt(size)+1;
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
