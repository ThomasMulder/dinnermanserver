package ModelTest;

import Configuration.Database;
import Model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 28-3-2016.
 */
public class UserTest {

    @Test
    /**
     * Tests the {@code computeSimilarity()} method of the {@code User} class.
     */
    public void testComputeSimilarity() throws Exception {
        List<Integer> uFavorites = new ArrayList();
        List<Integer> vFavorites = new ArrayList();
        uFavorites.add(1);
        uFavorites.add(2);
        vFavorites.add(1);
        vFavorites.add(3);
        User u = new User("u", uFavorites, new ArrayList<String>());
        User v = new User("v", vFavorites, new ArrayList<String>());
        assertTrue(u.computeSimilarity(v) == 5);
    }
}