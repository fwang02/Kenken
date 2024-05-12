/**
 * @author Romeu Esteve Casanovas
 */

package JUnit;

import Domain.Controllers.CtrlDomainUser;
import Domain.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CtrlDomainUserTest {

    static CtrlDomainUser ctrlDomainUser;
    static String username;
    static String password;

    @BeforeClass
    public static void initializeTest() {
        ctrlDomainUser = new CtrlDomainUser();
        username = "testuser";
        password = "password123";

    }

    /*
    @Test
    public void testGetInstance() {
        CtrlDomainUser ctrlDomainUser2 = CtrlDomainUser.getInstance();
        assertSame("Both instances should be the same", ctrlDomainUser, ctrlDomainUser2);
    }
     */

    @Test
    public void testAddUser() {
        boolean added = ctrlDomainUser.addUser(username, password);
        assertTrue("User should be added", added);
    }

    @Test
    public void testIsPasswordCorrect() {
        assertTrue("Password should be correct", ctrlDomainUser.isPasswordCorrect(username, password));
    }

    @Test
    public void testUpdateMaxPoint() {
        ctrlDomainUser.loginUser("testuser");

        int newMaxPoint = 100;
        boolean updated = ctrlDomainUser.updateMaxPointCurrUser(newMaxPoint);
        assertTrue("Max point should be updated", updated);
        User user = ctrlDomainUser.getUser(username);
        assertNotNull("User should not be null", user);
        assertEquals("Max point should be updated correctly", newMaxPoint, user.getMaxPoint());
    }

    @AfterClass
    public static void deleteUserAfterTest() {
        assertTrue("Test user deleted",ctrlDomainUser.deleteUser("testuser"));
    }
}
