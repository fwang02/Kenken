package JUnit;

import Domain.CtrlDomainKenken;
import Domain.Kenken;
import Domain.KenkenCell;
import Domain.KenkenCage;
import Domain.TypeDifficulty;
import Domain.TypeOperation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class CtrlDomainKenkenTest {

    @Test
    public void testGenerateKenkenByDifficulty() {
        CtrlDomainKenken ctrlDomainKenken = new CtrlDomainKenken();
        int size = 6;
        HashSet<TypeOperation> ops = new HashSet<>();
        ops.add(TypeOperation.ADD);
        ops.add(TypeOperation.SUB);
        ops.add(TypeOperation.MULT);
        ops.add(TypeOperation.DIV);
        ops.add(TypeOperation.POW);
        ops.add(TypeOperation.MOD);
        TypeDifficulty difficulty = TypeDifficulty.MEDIUM;

        ctrlDomainKenken.generateKenkenByDifficulty(size, ops, difficulty);

        Kenken currentGame = ctrlDomainKenken.getCurrentGame();
        assertNotNull("Current game should not be null", currentGame);
        assertEquals("Size should be set correctly", size, currentGame.getSize());
        assertEquals("Operations should be set correctly", ops, currentGame.getOperations());
        assertEquals("Difficulty should be set correctly", difficulty, currentGame.getDificult());
    }

    @Test
    public void testSolveKenkenByUserParameters() {
        CtrlDomainKenken ctrlDomainKenken = new CtrlDomainKenken();
        int size = 6;
        HashSet<TypeOperation> ops = new HashSet<>();
        ops.add(TypeOperation.ADD);
        ops.add(TypeOperation.SUB);
        ops.add(TypeOperation.MULT);
        ops.add(TypeOperation.DIV);
        ops.add(TypeOperation.POW);
        ops.add(TypeOperation.MOD);
        TypeDifficulty difficulty = TypeDifficulty.MEDIUM;

        ctrlDomainKenken.generateKenkenByDifficulty(size, ops, difficulty);
        Kenken currentGame = ctrlDomainKenken.getCurrentGame();
        Kenken kenken = new Kenken(currentGame);

        ArrayList<KenkenCage> cages = kenken.getAllCages();
        KenkenCell[][] cells = kenken.getAllCells();

        boolean solved = ctrlDomainKenken.solveKenkenByUserParameters(size, ops, difficulty, cages, cells);
        assertTrue("Should return true since it can be solved", solved);
        assertNotNull("Current game should not be null", ctrlDomainKenken.getCurrentGame());
    }

    @Test
    public void testSolveKenkenByFile() {
        CtrlDomainKenken ctrlDomainKenken = new CtrlDomainKenken();
        String fileName = "input";
        boolean solved = ctrlDomainKenken.solveKenkenByFile(fileName);
        assertTrue("Should return false since it can be solved", solved);
        assertNotNull("Current game should not be null", ctrlDomainKenken.getCurrentGame());
    }
}
