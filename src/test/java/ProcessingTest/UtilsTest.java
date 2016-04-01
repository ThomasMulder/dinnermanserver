package ProcessingTest;

import Processing.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by s124392 on 1-4-2016.
 */
public class UtilsTest {
    private static Utils utils = Utils.getInstance();

    @Test
    public void testGetListIntegerIntersection() throws Exception {
        List<Integer> a = new ArrayList();
        a.add(1);
        a.add(2);
        List<Integer> b = new ArrayList();
        b.add(2);
        List<Integer> intersection = utils.getListIntegerIntersection(a, b);
        assertTrue(intersection.size() == 1);
        assertTrue(intersection.get(0) == 2);
    }

    @Test
    public void testGetListStringIntersection() throws Exception {
        List<String> a = new ArrayList();
        List<String> b = new ArrayList();
        a.add("Hello");
        a.add("World");
        b.add("Hello");
        List<String> intersection = utils.getListStringIntersection(a, b, true);
        assertTrue(intersection.size() == 1);
        assertTrue(intersection.get(0).equals("Hello"));
        intersection = utils.getListStringIntersection(a, b, false);
        assertTrue(intersection.size() == 1);
        assertTrue(intersection.get(0).equals("hello"));
    }

    @Test
    public void testGetArrayAndListIntersection() throws Exception {
        String[] a = {"Hello", "World"};
        List<String> b = new ArrayList();
        b.add("Hello");
        List<String> intersection = utils.getArrayAndListIntersection(a, b, true);
        assertTrue(intersection.size() == 1);
        assertTrue(intersection.get(0).equals("Hello"));
        intersection = utils.getArrayAndListIntersection(a, b, false);
        assertTrue(intersection.size() == 1);
        assertTrue(intersection.get(0).equals("hello"));
    }

    @Test
    public void testGetListStringDifference() throws Exception {
        List<String> a = new ArrayList();
        List<String> b = new ArrayList();
        a.add("a");
        a.add("b");
        b.add("b");
        List<String> diff = utils.getListStringDifference(a, b, true);
        assertTrue(diff.size() == 1);
        assertTrue(diff.get(0).equals("a"));
        diff = utils.getListStringDifference(a, b, false);
        assertTrue(diff.size() == 1);
        assertTrue(diff.get(0).equals("a"));
    }

    @Test
    public void testListContains() throws Exception {
        List<Integer> aux = new ArrayList();
        aux.add(1);
        assertTrue(utils.listContains(aux, 1));
        assertFalse(utils.listContains(aux, 2));
    }

    @Test
    public void testListContains1() throws Exception {
        List<String> aux = new ArrayList();
        aux.add("a");
        assertTrue(utils.listContains(aux, "a", true));
        assertFalse(utils.listContains(aux, "b", true));
        assertTrue(utils.listContains(aux, "a", false));
        assertFalse(utils.listContains(aux, "b", false));
    }

    @Test
    public void testGetRandomIndexFromList() throws Exception {
        List<Integer> aux = new ArrayList();
        aux.add(1);
        aux.add(2);
        aux.add(3);
        for (int i = 0; i < 1000; i++) {
            int index = utils.getRandomIndexFromList(aux);
            assertTrue(0 <= index && index < aux.size());
        }
    }

    @Test
    public void testInsertUniqueInteger() throws Exception {
        List<Integer> aux = new ArrayList();
        utils.insertUniqueInteger(aux, 1);
        assertTrue(aux.size() == 1);
        assertTrue(aux.get(0) == 1);
        utils.insertUniqueInteger(aux, 1);
        assertTrue(aux.size() == 1);
        assertTrue(aux.get(0) == 1);
    }
}