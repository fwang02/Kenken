package JUnit;

import Domain.Kenken;
import Domain.KenkenCage;
import Domain.KenkenCell;
import Domain.TypeDifficulty;
import Domain.TypeOperation;
import org.junit.Test;
import java.util.HashSet;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class KenkenTest {
    @Test
    public void kenkenDefaultConstruct() {
        Kenken k = new Kenken();
        assertEquals("Empty kenken", 0, k.getSize());
    }

    @Test
    public void kenkenDuplicateConstruct() {
        Kenken k = new Kenken();
        Kenken k2 = new Kenken(k);
        assertEquals("Equal cages", k.getAllCages(), k2.getAllCages());
    }

    @Test
    public void kenkenGenConstruct() {
        HashSet<TypeOperation> operations = new HashSet<>();
        operations.add(TypeOperation.ADD);
        operations.add(TypeOperation.SUB);
        operations.add(TypeOperation.MULT);
        operations.add(TypeOperation.DIV);
        operations.add(TypeOperation.POW);
        operations.add(TypeOperation.MOD);

        Kenken k = new Kenken(6, operations, TypeDifficulty.MEDIUM);
        assertEquals("Kenken size", 6, k.getSize());
        assertEquals("Kenken difficulty", TypeDifficulty.MEDIUM, k.getDificult());
        assertEquals("Kenken number of cages", 0, k.getNumberCages());
        assertEquals("Kenken number of individual cells", 0, k.getNumberIndCells());
        assertEquals("Kenken operations", operations, k.getOperations());
    }

    @Test
    public void kenkenFileConstruct() {
        HashSet<TypeOperation> operations = new HashSet<>();
        operations.add(TypeOperation.ADD);
        operations.add(TypeOperation.SUB);
        operations.add(TypeOperation.MULT);
        operations.add(TypeOperation.DIV);
        operations.add(TypeOperation.POW);
        operations.add(TypeOperation.MOD);

        ArrayList<KenkenCage> cages = new ArrayList<>();

        KenkenCell[][] cells = new KenkenCell[6][6];

        Kenken k = new Kenken(6, operations, TypeDifficulty.MEDIUM, cages, cells);
        assertEquals("Kenken size", 6, k.getSize());
        assertEquals("Kenken difficulty", TypeDifficulty.MEDIUM, k.getDificult());
        assertEquals("Kenken number of cages", cages.size(), k.getNumberCages());
        assertEquals("Kenken number of individual cells", 0, k.getNumberIndCells());
        assertEquals("Kenken operations", operations, k.getOperations());
        assertEquals("Kenken cages", cages, k.getAllCages());
        assertEquals("Kenken cells", cells, k.getAllCells());
    }
}