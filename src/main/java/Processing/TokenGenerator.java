package Processing;

import Configuration.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by s124392 on 22-2-2016.
 * A singleton for generating various kinds of random tokens.
 */
public class TokenGenerator {
    /* The singleton instance. */
    private static TokenGenerator instance = null;

    /* The alphabet of characters to build a token from. */
    public static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFCHIJKLMNOPQRSTUVWXYZ";

    /**
     * Constructor.
     */
    private TokenGenerator() {}

    /**
     * Returns the singleton instance of {@code TokenGenerator}.
     * @return {@code TokenGenerator instance}.
     */
    public static TokenGenerator getInstance() {
        if (instance == null) {
            instance = new TokenGenerator();
        }
        return instance;
    }

    /**
     * Generates an authentication token of parameterised {@code length}.
     * @param length the length of the token (number of characters).
     * @return {@code String}.
     */
    public String generateAuthenticationToken(int length) {
        ResultSet results = Database.getInstance().ExecuteQuery("SELECT `authToken` FROM `accounts`;", new ArrayList<String>());
        return generate(length, results);
    }

    /**
     * Generates a unique token of parameterised {@code length} based on existing tokens in query result {@code ResultSet}.
     * @param length the length of the token (number of characters).
     * @param results the query results containing already existing tokens.
     * @return {@code String}.
     */
    private String generate(int length, ResultSet results) {
        List<String> existingTokens = new ArrayList<>();
        try {
            while (results.next()) { // Add query results to array list.
                existingTokens.add(results.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String newToken = getRandomSequence(length); // Generate a new token.
        while (!tokenIsUnique(existingTokens, newToken)) { // While it is not unique, regenerate.
            newToken = getRandomSequence(length);
        }
        return newToken;
    }

    /**
     * Checks whether or not a given {@code newToken} is unique with respect to {@code existingTokens}.
     * @param existingTokens the list of already existing tokens.
     * @param newToken the new token, for which 'uniqueness' has to be determined.
     * @return {@code boolean}.
     */
    private boolean tokenIsUnique(List<String> existingTokens, String newToken) {
        for (String t : existingTokens) {
            if (newToken.equals(t)) { // Token already exists.
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a random sequence of characters of parameterised {@code length}.
     * @param length the length of the sequence to be generated (number of characters).
     * @return {@code String}.
     */
    private String getRandomSequence(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result = result.concat(getRandomLetter());
        }
        return result;
    }

    /**
     * Generates a random character from the constant {@code ALPHABET}.
     * @return {@code String}.
     */
    private String getRandomLetter() {
        int i = (int) (Math.random() * ALPHABET.length());
        return ALPHABET.substring(i, i + 1);
    }
}
