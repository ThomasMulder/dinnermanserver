package ResourceTest;

import ApiServer.Resource.IngredientResource;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 22-3-2016.
 */
public class IngredientResourceTest {

    @Test
    public void testGetIntersection() throws Exception {
        IngredientResource res = new IngredientResource();
        String[] array = {"apples", "pears"};
        List<String> list = new ArrayList();
        list.add("apples");
        List<String> intersection = res.getIntersection(array, list);
        assertTrue(intersection.get(0).equals("apples"));
    }
}