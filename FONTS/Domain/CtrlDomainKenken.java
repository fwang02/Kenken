package Domain;

import java.util.*;
import java.io.*;

public class CtrlDomainKenken {
	private static final CtrlDomainKenken CTRL_KENKEN = new CtrlDomainKenken();
	private final ArrayList<Kenken> kenkens;
	private static final String filePath  = "./DATA/kenkens.txt";


	private CtrlDomainKenken() {
		kenkens = new ArrayList<Kenken>();
	}

	private void loadKenkenData() {
		try {
            File kenkensFile = new File(filePath);
            Scanner scanner = new Scanner(kenkensFile);
            HashSet<TypeOperation> opSet;
       		ArrayList<KenkenCage> cages;
            while(scanner.hasNextLine()) {
                int size = scanner.nextInt();
                int numCage = scanner.nextInt();
                opSet = new HashSet<>();
                cages = new ArrayList<>(numCage);
                for(int j = 0; j < numCage; ++j) {
                	String line = scanner.nextLine();
		            String[] numStr = line.split("\\s+");

		            TypeOperation op = getOperation(Integer.parseInt(numStr[0]));
		            opSet.add(op);

		            int result = Integer.parseInt(numStr[1]);
		            int numCells = Integer.parseInt(numStr[2]);
		            //Pos[] posCells = new Pos[numCells];
		            KenkenCell[] cells = new KenkenCell[numCells];
		            int offset = 3;
		            for (int i = 0; i < numCells; i++) {
		                int posX = Integer.parseInt(numStr[offset + 2 * i]) - 1;
		                int posY = Integer.parseInt(numStr[offset + 1 + 2 * i]) - 1;
		                // Locked number cell
		                if ((offset + 2 + 2 * i < numStr.length) && numStr[offset + 2 + 2 * i].startsWith("[")) {
		                    String str = numStr[offset + 2 + 2 * i];
		                    int val = Integer.parseInt(str.substring(1, str.length() - 1));

		                    cells[i] = new KenkenCell(posX, posY, val, true);
		                    offset++;
		                } else {
		                    cells[i] = new KenkenCell(posX, posY);
		                }
		            }
		            cages.add(new KenkenCage(op, result, cells));
                }
                Kenken Game = new Kenken(size,opSet,TypeDificult.EXPERT,cages);
                kenkens.add(Game);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no existe：" + filePath+", los usuarios no están cargados en la memoria");
        }
	}

	static TypeOperation getOperation(int num)
    {
        switch (num) {
            case 1:
                return TypeOperation.ADD;
            case 2:
                return TypeOperation.SUB;
            case 3:
                return TypeOperation.MULT;
            case 4:
                return TypeOperation.DIV;
            case 5:
                return TypeOperation.MOD;
            case 6:
                return TypeOperation.POW;
            default:
                throw new IllegalArgumentException("Carácter no válido encontrado en la cadena: " + num);
        }
    }
}