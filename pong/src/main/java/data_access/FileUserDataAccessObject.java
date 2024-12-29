package data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data_access.encryption.DataEncryption;
import entity.data.DataSet;
import entity.user.CommonUserFactory;
import entity.user.User;
import use_case.account.change_password.ChangePasswordUserDataAccessInterface;
import use_case.account.login.LoginUserDataAccessInterface;
import use_case.account.logout.LogoutUserDataAccessInterface;
import use_case.account.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.util.HashMap;


/** This is a class that lets you access the data of one user.
 * It contains various methods to change password, login, logout, signup users.
 */

public class FileUserDataAccessObject implements ChangePasswordUserDataAccessInterface,
        LoginUserDataAccessInterface, LogoutUserDataAccessInterface, SignupUserDataAccessInterface {

    /**
     * Most of these instance variables are self-explanatory.
     * The userDataDirectory is the base folder that the user data is stored in.
     * The dataEncryption is a service that we use to encrypt data before it is saved.
     * The user is the user that is currently stored into memory.
     * The cachedUsers is a set of users that we store, temporarily, into memory,
     * so that we don't have to lookup and decrypt users we have already looked up.
     */

    final private String userDataDirectory;
    final private DataEncryption dataEncryption;
    final private String fileType = ".json";
    final private User user;
    final private HashMap<String, User> cachedUsers;

    /**
     * This takes in a userDataDirectory. It is typically the directory pong/userdata
     * and the data is stored in {@code fileType} files.
     * @param userDataDirectory The folder that holds all the user data.
     * @param dataEncryption The data encryption service that encrypts the user data so it is unreadable
     * @param username The username of the user. It will be loaded into memory.
     */
    public FileUserDataAccessObject(String userDataDirectory, DataEncryption dataEncryption, String username) {
        this.userDataDirectory = userDataDirectory;
        this.dataEncryption = dataEncryption;
        user = get(username);
        cachedUsers = new HashMap<>();
        cachedUsers.put(username, user);
    }

    /** Loads a user from the username
     *
     * @param username The username of the user.
     * @return a User object that has the data of the user's file loaded in.
     */
    public User get(String username) {
        User user = new CommonUserFactory().create();
        String userFile = getUserPath(username);

        // Use BufferedReader to read the file
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException ex) {
            System.err.println("An error occurred while reading the file: " + ex.getMessage());
            System.out.println("The user's file could not be read.");
            System.exit(1);
        }

        String decryptedJsonContent = dataEncryption.decrypt(jsonContent.toString());

        // Parse the JSON content
        JsonObject jsonObject = JsonParser.parseString(decryptedJsonContent).getAsJsonObject();

        // Add all the various attributes.
        loadUsername(user, jsonObject);
        loadPassword(user, jsonObject);
        loadTimePlayed(user, jsonObject);
        loadNumGames(user, jsonObject);
        loadNumWins(user, jsonObject);
        return user;
    }

    // This is a list of attribute adders that add various things to the user.

    private void loadUsername(User user, JsonObject jsonObject) {
        if (jsonObject.has("username")) {
            user.setName(jsonObject.get("username").getAsString());
        }
    }
    private void loadPassword(User user, JsonObject jsonObject) {
        if (jsonObject.has("password")) {
            user.setPassword(jsonObject.get("password").getAsString());
        }
    }
    private void loadTimePlayed(User user, JsonObject jsonObject) {
        if (jsonObject.has("timePlayed")) {
            user.getDataSet().setPlayTime(jsonObject.get("timePlayed").getAsLong());
        }
    }
    private void loadNumGames(User user, JsonObject jsonObject) {
        if (jsonObject.has("numGames")) {
            user.getDataSet().setNumberOfGames(jsonObject.get("numGames").getAsInt());
        }
    }
    private void loadNumWins(User user, JsonObject jsonObject) {
        if (jsonObject.has("numWins")) {
            user.getDataSet().setNumberOfWins(jsonObject.get("numWins").getAsInt());
        }
    }


    /**
     * Updates the system to record this user's password.
     *
     * @param newPassword the new password for the user currently loaded.
     */
    public void changePassword(String newPassword) {
        user.setPassword(newPassword);
    }

    /**
     * Checks if the given username exists.
     * More specifically, it checks the directory for a file that matches {@code username.fileType}
     * @param username the name of the file we are looking for
     * @return {@code true} if a file is found with the user data; {@code false} otherwise
     */
    @Override
    public boolean existsByName(String username) {
        String path = getUserDataDirectory(username);
        File file = new File(path);
        return file.exists();
    }

    /**
     * Saves the user. It takes the DataSet from the User and
     * converts it into {@code .json} format to be saved.
     * @param user the user whose data we are going to save.
     */
    @Override
    public void save(User user) {

        JsonObject jsonObject = new JsonObject();

        saveUsername(jsonObject, user);
        savePassword(jsonObject, user);
        saveTimePlayed(jsonObject, user);
        saveNumGames(jsonObject, user);
        saveNumWins(jsonObject, user);

        final String encryptedData = dataEncryption.encrypt(jsonObject.toString());
        final File file = new File(getUserDataDirectory(user.getName()));
        // TODO: unfinished busines
    }

    private void saveUsername(JsonObject jsonObject, User user) {
        jsonObject.addProperty("username", user.getName());
    }
    private void savePassword(JsonObject jsonObject, User user) {
        jsonObject.addProperty("password", user.getPassword());
    }
    private void saveTimePlayed(JsonObject jsonObject, User user) {
        jsonObject.addProperty("timePlayed", user.getDataSet().getPlayTime());
    }
    private void saveNumGames(JsonObject jsonObject, User user) {
        jsonObject.addProperty("numGames", user.getDataSet().getNumberOfGames());
    }
    private void saveNumWins(JsonObject jsonObject, User user) {
        jsonObject.addProperty("numWins", user.getDataSet().getNumberOfWins());
    }

    /** Gets a file corresponding to a user.
     *
     * @param username The name of the User's file.
     * @return A String whose path is for the User's file.
     */
    private String getUserPath(String username) {
        // TODO: File names should be hashed eventually
        return userDataDirectory + File.separator + username + fileType;
    }

    /**
     * Returns the username of the curren user of the application.
     *
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    @Override
    public String getCurrentUsername() {
        return user.getName();
    }

    /**
     * Sets the username indicating who is the current user of the application.
     *
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    @Override
    public void setCurrentUsername(String username) {
        user.setName(username);
    }


    private String getUserDataDirectory(String username) {
        return userDataDirectory + File.separator + username + fileType;
    }
}
