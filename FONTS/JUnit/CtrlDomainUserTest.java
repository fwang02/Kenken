package JUnit;

import Domain.CtrlDomainUser;
import Domain.User;
import org.junit.Test;
import java.io.File;
import java.util.HashMap;
import static org.junit.Assert.*;

public class CtrlDomainUserTest {

    @Test
    public void testGetInstance() {
        CtrlDomainUser ctrlDomainUser1 = CtrlDomainUser.getInstance();
        CtrlDomainUser ctrlDomainUser2 = CtrlDomainUser.getInstance();
        assertSame("Both instances should be the same", ctrlDomainUser1, ctrlDomainUser2);
    }

    @Test
    public void testAddUser() {
        CtrlDomainUser ctrlDomainUser = CtrlDomainUser.getInstance();
        String username = "testuser";
        String password = "password123";
        if (ctrlDomainUser.isUserExist(username)) {
            System.out.println("testuser already exists, test aborted");
            return;
        }
        ctrlDomainUser.addUser(username, password);

        HashMap<String, User> users = ctrlDomainUser.getAllUsers();
        assertTrue("User should be added", users.containsKey(username));

        // Clean up by removing the added user from file
        ctrlDomainUser.deleteUser("testuser");
    }

    @Test
    public void testIsPasswordCorrect() {
        CtrlDomainUser ctrlDomainUser = CtrlDomainUser.getInstance();
        String username = "testuser";
        String password = "password123";
        ctrlDomainUser.addUser(username, password);

        assertTrue("Password should be correct", ctrlDomainUser.isPasswordCorrect(username, password));

        // Clean up by removing the added user from file
        ctrlDomainUser.deleteUser("testuser");
    }

    @Test
    public void testUpdateMaxPoint() {
        CtrlDomainUser ctrlDomainUser = CtrlDomainUser.getInstance();
        String username = "testuser";
        String password = "password123";
        ctrlDomainUser.addUser(username, password);

        ctrlDomainUser.loginUser("testuser");

        int newMaxPoint = 100;
        boolean updated = ctrlDomainUser.updateMaxPointCurrUser(newMaxPoint);
        assertTrue("Max point should be updated", updated);

        User user = ctrlDomainUser.getUser(username);
        assertNotNull("User should not be null", user);
        assertEquals("Max point should be updated correctly", newMaxPoint, user.getMaxPoint());

        // Clean up by removing the added user from file
        ctrlDomainUser.deleteUser("testuser");
    }
}
