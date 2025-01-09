package data_access;

import data_access.encryption.DataEncryption;
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
    final private String fileType = ".txt"; // TODO: this piece of code is smelly
    final private HashMap<String, User> cachedUsers;
    final private DataParser dataParser = new JsonDataParser();
    private User user;


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

        cachedUsers = new HashMap<>();
        load(username);
    }

    /** Loads the user from the username into memory.
     *  It also saves the user that was previously loaded, if there was one.
     *
     * @param username The user that we are going to load.
     */
    public void load(String username) {
        if (user != null) {
            save(user);
        }
        user = get(username);
    }

    /** Loads a user from the username.
     * It also loads the user into a cache for quick access.
     *
     * @param username The username of the user.
     * @return a User object that has the data of the user's file loaded in.
     */
    public User get(String username) {
        if(!cachedUsers.isEmpty() && cachedUsers.containsKey(username)) {
            return cachedUsers.get(username);
        }

        String userFile = getUserPath(username);

        // Use BufferedReader to read the file
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (IOException ex) {
            System.err.println("An error occurred while reading the file: " + ex.getMessage());
            System.out.println("The user's file could not be read.");
            System.exit(1);
        }

        // Decrypt the file content
        String decryptedFileContent = dataEncryption.decrypt(fileContent.toString());

        // Load the file content into the user.
        User user = dataParser.load(decryptedFileContent);

        // Save user to the cache for quick access.
        cachedUsers.put(username, user);
        return user;
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
     * converts it into {@code fileType} format to be saved.
     * @param user the user whose data we are going to save.
     */
    @Override
    public void save(User user) {
        final String encryptedData = dataEncryption.encrypt(dataParser.save(user));
        final File file = new File(getUserDataDirectory(user.getName()));
        try (FileWriter writer = new FileWriter(file)){
            writer.write(encryptedData);
        } catch (IOException ex) {
            System.err.println("An error occurred while writing the file: " + ex.getMessage());
        }
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
