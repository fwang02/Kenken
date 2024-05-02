/**
 * @author Romeu Esteve Casanovas
 */

package JUnit;

import Domain.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserTest {
    @Test
    public void userDefaultConstruct() {
        User user = new User();
        assertNull("Username should be null", user.getUsername());
        assertNull("Password should be null", user.getPassword());
        assertEquals("Max point should be 0", 0, user.getMaxPoint());
        //assertNull("Created games should be null", user.getCreatedGames());
    }

    @Test
    public void userConstructWithUsernameAndPassword() {
        String username = "testuser";
        String password = "password123";
        User user = new User(username, password);
        assertEquals("Username should be set correctly", username, user.getUsername());
        assertEquals("Password should be set correctly", password, user.getPassword());
        assertEquals("Max point should be 0", 0, user.getMaxPoint());
        //assertNotNull("Created games should be initialized", user.getCreatedGames());
        //assertTrue("Created games should be empty", user.getCreatedGames().isEmpty());
    }

    @Test
    public void userConstructWithUsernamePasswordAndMaxPoint() {
        String username = "testuser";
        String password = "password123";
        int maxPoint = 100;
        User user = new User(username, password, maxPoint);
        assertEquals("Username should be set correctly", username, user.getUsername());
        assertEquals("Password should be set correctly", password, user.getPassword());
        assertEquals("Max point should be set correctly", maxPoint, user.getMaxPoint());
        //assertNotNull("Created games should be initialized", user.getCreatedGames());
        //assertTrue("Created games should be empty", user.getCreatedGames().isEmpty());
    }

    @Test
    public void setUserPassword() {
        String username = "testuser";
        String password = "password123";
        User user = new User(username, password);
        String newPassword = "newpassword123";
        user.setPassword(newPassword);
        assertEquals("Password should be updated", newPassword, user.getPassword());
    }

    @Test
    public void setUserUsername() {
        String username = "testuser";
        String password = "password123";
        User user = new User(username, password);
        String newUsername = "newtestuser";
        user.setUsername(newUsername);
        assertEquals("Username should be updated", newUsername, user.getUsername());
    }

    @Test
    public void setUserMaxPoint() {
        String username = "testuser";
        String password = "password123";
        int maxPoint = 100;
        User user = new User(username, password);
        user.setMaxPoint(maxPoint);
        assertEquals("Max point should be updated", maxPoint, user.getMaxPoint());
    }
}
