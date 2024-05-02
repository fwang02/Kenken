/**
 * @author Romeu Esteve Casanovas
 */

package JUnit;

import Domain.CtrlDomainKenken;
import Domain.Kenken;
import Domain.KenkenCell;
import Domain.KenkenCage;
import Domain.TypeDifficulty;
import Domain.Operation.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class CtrlDomainKenkenTest {

    @Test
    public void testGenerateKenkenByDifficulty() {
        CtrlDomainKenken ctrlDomainKenken = new CtrlDomainKenken();
        int size = 6;
        HashSet<Operation> operations = new HashSet<>();
        operations.add(new ADD());
        operations.add(new SUB());
        operations.add(new MULT());
        operations.add(new DIV());
        operations.add(new POW());
        operations.add(new MOD());
        TypeDifficulty difficulty = TypeDifficulty.MEDIUM;

        ctrlDomainKenken.generateKenkenByDifficulty(size, operations, difficulty);

        Kenken currentGame = ctrlDomainKenken.getCurrentGame();
        assertNotNull("Current game should not be null", currentGame);
        assertEquals("Size should be set correctly", size, currentGame.getSize());
        assertEquals("Operations should be set correctly", operations, currentGame.getOperations());
        assertEquals("Difficulty should be set correctly", difficulty, currentGame.getDificult());
    }

    @Test
    public void testSolveKenkenByUserParameters() {
        CtrlDomainKenken ctrlDomainKenken = new CtrlDomainKenken();
        int size = 6;
        HashSet<Operation> operations = new HashSet<>();
        operations.add(new ADD());
        operations.add(new SUB());
        operations.add(new MULT());
        operations.add(new DIV());
        operations.add(new POW());
        operations.add(new MOD());
        TypeDifficulty difficulty = TypeDifficulty.MEDIUM;

        ctrlDomainKenken.generateKenkenByDifficulty(size, operations, difficulty);
        Kenken currentGame = ctrlDomainKenken.getCurrentGame();
        Kenken kenken = new Kenken(currentGame);

        ArrayList<KenkenCage> cages = kenken.getAllCages();
        KenkenCell[][] cells = kenken.getAllCells();

        boolean solved = ctrlDomainKenken.solveKenkenByUserParameters(size, operations, difficulty, cages, cells);
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
