package Processing;


import Configuration.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by s124392 on 23-2-2016.
 */
public class Maintenance extends Thread {
    /* The date at which authentication expiration was last checked. */
    private Date authenticationExpirationTime;
    /* The interval during which an authentication token is valid, in milliseconds. */
    private static final long AUTHENTICATION_EXPIRATION_TIME_INTERVAL = 1800000;//1800000;
    /* The interval between authentication expiration checks, in milliseconds. */
    private static final long AUTHENTICATION_CLEAN_TIME_INTERVAL = 2000;

    /* The date at which the `favorites` table was last cleaned. */
    private Date favoritesCleanTime;
    /* The interval between cleaning the `favorites` table, in milliseconds. */
    private static final long FAVORITES_CLEAN_TIME_INTERVAL = 180000;

    /**
     * Constructor.
     */
    public Maintenance() {
        this.authenticationExpirationTime = new Date();
        this.favoritesCleanTime = new Date();
    }

    /**
     * Runs the thread and performs checks at certain intervals.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date now = new Date();
            if (authenticationIntervalExceeded()) {
                this.authenticationExpirationTime = new Date();
                updateAuthenticationExpiration();
            }
            if (favoritesIntervalExceeded()) {
                this.favoritesCleanTime = new Date();
                cleanFavorites();
            }
        }
    }

    /**
     * Stars the service.
     * @return {@code true iff} the service started correctly.
     */
    public boolean startService() {
        if (this.isAlive()) {
            System.err.println("[WARNING] Maintenance: Something attempted to start Maintenance a second time.");
            return false;
        }
        System.out.println("[INFO] Maintenance: Maintenance has been started.");
        this.start();
        return true;
    }

    /**
     * Attempts to stop the service gracefully.
     * @return {@code true iff} the service stopped correctly.
     */
    public boolean stopService() {
        System.out.println("[INFO] Maintenance: Attempting to stop Maintenance...");
        try {
            this.interrupt();
            this.join(1000);

            if (this.isAlive()) {
                System.err.println("[WARN] Maintenance: Maintenance did not stop within one second.");
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Updates the `accounts` table, nullifying those `authToken` values for which the corresponding `authTokenCreated`
     * date is at least {@code AUTHENTICATION_EXPIRATION_TIME_INTERVAL} milliseconds ago, from this point in time.
     */
    private void updateAuthenticationExpiration() {
        List<Integer> idsToRemove = new ArrayList();
        ResultSet result = Database.getInstance().ExecuteQuery("SELECT `id`, `authTokenCreated` FROM `accounts`;", new ArrayList<String>());
        try {
            while (result.next()) { // For each result.
                int id = result.getInt(1);
                Date date = result.getTimestamp(2);
                if (date != null) {
                    if (timeIntervalExceeded(date, AUTHENTICATION_EXPIRATION_TIME_INTERVAL)) { // Token no longer valid.
                        idsToRemove.add(id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int id : idsToRemove) { // Nullify tokens.
            Database.getInstance().ExecuteUpdate("UPDATE `accounts` SET `authToken` = NULL , `authTokenCreated` = NULL WHERE `id` = '" + id + "';", new ArrayList<String>());
        }
    }

    /**
     * Cleans the `favorites` table of possible duplicate entries.
     */
    private void cleanFavorites() {
        System.out.println("[INFO] Maintenance: Cleaning duplicates from `favorites` table.");
        ResultSet result = Database.getInstance().ExecuteQuery("SELECT `account_id`, `recipe_id` FROM `favorites` LIMIT 0,1000;", new ArrayList<String>());
        List<FavoriteEntry> entries = new ArrayList();
        FavoriteEntry currentEntry = null;
        try {
            while(result.next()) {
                int account_id = result.getInt(1);
                int recipe_id = result.getInt(2);
                if (currentEntry == null || currentEntry.getAccount_id() != account_id) {
                    currentEntry = new FavoriteEntry(account_id);
                    entries.add(currentEntry);
                }
                currentEntry.getRecipe_ids().add(recipe_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (FavoriteEntry entry : entries) {
            HashSet<Integer> uniqueRecipeIds = new HashSet();
            uniqueRecipeIds.addAll(entry.getRecipe_ids());
            List<Integer> toRemove = new ArrayList();
            toRemove.addAll(entry.getRecipe_ids());
            List<Integer> aux = new ArrayList();
            aux.addAll(uniqueRecipeIds);
            for (Integer i : aux) {
                toRemove.remove(i);
            }
            for (int i : toRemove) {
                Database.getInstance().ExecuteUpdate("DELETE FROM `favorites` WHERE `account_id` = '" + entry.getAccount_id() + "' AND `recipe_id` = '" + i + "' LIMIT 1;", new ArrayList<String>());
            }
        }
    }

    /**
     * Checks whether or not the {@code authenticationExpirationTime} is exceeded by at least
     * {@code AUTHENTICATION_CLEAN_TIME_INTERVAL} milliseconds.
     * @return {@code true iff} the interval has been exceeded.
     */
    private boolean authenticationIntervalExceeded() {
        return timeIntervalExceeded(this.authenticationExpirationTime, AUTHENTICATION_CLEAN_TIME_INTERVAL);
    }

    /**
     * Checks whether or not the {@code favoritesCleanTime} is exceeded by at least
     * {@code FAVORITES_CLEAN_TIME_INTERVAL} milliseconds.
     * @return {@code true iff} the interval has been exceeded.
     */
    private boolean favoritesIntervalExceeded() {
        return timeIntervalExceeded(this.favoritesCleanTime, FAVORITES_CLEAN_TIME_INTERVAL);
    }

    /**
     * Checks whether or the the {@code date} is exceeded by at least {@code interval} milliseconds.
     * @param date
     * @param interval
     * @return
     */
    private boolean timeIntervalExceeded(Date date, long interval) {
        Date now = new Date();
        return (now.getTime() - date.getTime() >= interval);
    }

    /**
     * Auxiliary enumeration that describes an entry in the `favorites` table.
     */
    private class FavoriteEntry {
        private int entry_id;
        private int account_id;
        private List<Integer> recipe_ids;

        public FavoriteEntry(int account_id) {
            this.setEntry_id(entry_id);
            this.setAccount_id(account_id);
            this.setRecipe_ids(new ArrayList());
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public List<Integer> getRecipe_ids() {
            return recipe_ids;
        }

        public void setRecipe_ids(List<Integer> recipe_ids) {
            this.recipe_ids = recipe_ids;
        }

        public int getEntry_id() {
            return entry_id;
        }

        public void setEntry_id(int entry_id) {
            this.entry_id = entry_id;
        }
    }
}
