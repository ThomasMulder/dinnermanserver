package Processing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 1-4-2016.
 * A singleton containing various methods for common operations.
 */
public class Utils {
    /* Singleton instance. */
    private static Utils instance = null;

    /**
     * Constructor.
     */
    private Utils() {}

    /**
     * Returns the static instance of this singleton.
     * @return {@code Utils}
     */
    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    /**
     * Returns the intersection between {@code List<Integer> a} and {@code List<Integer b}.
     * That is, it returns a new {@code List<Integer> result} such that for all {@code int x} in {@code result}
     * {@code x} in {@code a} and {@code x} in {@code b} holds.
     * @param a The first list to find intersection for.
     * @param b The second list to find intersection for.
     * @return {@code List<Integer>}.
     */
    public List<Integer> getListIntegerIntersection(List<Integer> a, List<Integer> b) {
        List<Integer> result = new ArrayList();
        for (int x : a) { // For all x in a.
            for (int y : b) { // For all y in b.
                if (x == y) { // x in a and b.
                    result.add(x);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns the intersection between {@code List<String> a} and {@code List<String b}.
     * That is, it returns a new {@code List<String> result} such that for all {@code String x} in {@code result}
     * {@code x} in {@code a} and {@code x} in {@code b} holds.
     * @param a The first list to find intersection for.
     * @param b The second list to find intersection for.
     * @param strict Indicates whether or not strings must match exactly (e.g. differentiating between capitals).
     * @return {@code List<String>}.
     */
    public List<String> getListStringIntersection(List<String> a, List<String> b, boolean strict) {
        List<String> result = new ArrayList();
        for (String x : a) { // For all x in a.
            if (!strict) {
                x = x.toLowerCase().trim(); // Make the string more uniform.
            }
            for (String y : b) { // For all y in b.
                if (!strict) {
                    y = y.toLowerCase().trim(); // Make the string more uniform.
                }
                if (x.equals(y)) { // x in a and b.
                    result.add(x);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Returns the intersection between {@code String[] a} and {@code List<String b}.
     * That is, it returns a new {@code List<String> result} such that for all {@code String x} in {@code result}
     * {@code x} in {@code a} and {@code x} in {@code b} holds.
     * @param a The array to find intersection for.
     * @param b The list to find intersection for.
     * @param strict Indicates whether or not strings must match exactly (e.g. differentiating between capitals).
     * @return {@code List<String>}.
     */
    public List<String> getArrayAndListIntersection(String[] a, List<String> b, boolean strict) {
        List<String> aux = new ArrayList();
        for (String s : a) { // Add strings in a to auxiliary list.
            aux.add(s);
        }
        return getListStringIntersection(aux, b, strict);
    }

    /**
     * Returns the difference {@code (a - b)} of two parameterised {@code List<String> a} and {@code b}.
     * @param a the list to subtract from.
     * @param b the list to subtract.
     * @param strict Indicates whether or not strings must match exactly (e.g. differentiating between capitals).
     * @return the difference (a - b).
     */
    public List<String> getListStringDifference(List<String> a, List<String> b, boolean strict) {
        List<String> result = new ArrayList();
        for (String s : a) { // Iterate over a.
            if (!strict) {
                s = s.toLowerCase().trim(); // Make String uniform.
            }
            boolean isAllowed = true; // Assumption.
            for (String t : b) { // Iterate over b.
                if (!strict) {
                    t = t.toLowerCase().trim(); // Make String uniform.
                }
                if (t.equals(s)) { // s is present in b.
                    isAllowed = false;
                    break;
                }
            }
            if (isAllowed) { // s is not present in b and is allowed.
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Checks whether or not {@code List<String> list} contains {@code String string}.
     * @param list the list to check in.
     * @param string the string to check for.
     * @param strict Indicates whether or not strings must match exactly (e.g. differentiating between capitals).
     * @return {@code boolean}.
     */
    public boolean listContains(List<String> list, String string, boolean strict) {
        string = string.toLowerCase().trim();
        for (String s : list) { // For all s in list.
            if (!strict) {
                s = s.toLowerCase().trim(); // Make string more uniform.
            }
            if (s.equals(string)) { // string in list.
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether or not {@code List<String> list} contains {@code String string}.
     * @param list the list to check in.
     * @param x the string to check for.
     * @return {@code boolean}.
     */
    public boolean listContains(List<Integer> list, int x) {
        for (int i : list) { // For all i in list.
            if (i == x) { // x in list.
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a random index for parameterised {@code List list}.
     * @param list the list to pick a random index for.
     * @return {@code int}.
     */
    public int getRandomIndexFromList(List list) {
        return (int) Math.round(Math.random() * (list.size() - 1));
    }

    /**
     * Returns parameterised {@code List<Integer> l} with one instance of {@code int x} present.
     * That is, {@code int x} is added to {@code List<Integer> l} if and only if {@code int x} is not yet
     * present in {@code List<Integer> l}.
     * @param l the list to add to.
     * @param x the integer to add.
     * @return {@code List<Integer>}.
     */
    public List<Integer> insertUniqueInteger(List<Integer> l, int x) {
        boolean contains = false; // Initial assumption: x not yet in l.
        for (int i : l) { // For all i in l.
            if (i == x) { // x already in l.
                contains = true; // Discard the assumption.
                break;
            }
        }
        if (!contains) { // x not yet in l.
            l.add(x);
        }
        return l;
    }
}
