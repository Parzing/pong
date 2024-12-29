package data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import data_access.encryption.DataEncryption;
import entity.data.DataSet;
import entity.user.CommonUser;
import entity.user.CommonUserFactory;
import entity.user.User;
import use_case.account.change_password.ChangePasswordUserDataAccessInterface;
import use_case.account.login.LoginUserDataAccessInterface;
import use_case.account.logout.LogoutUserDataAccessInterface;
import use_case.account.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.util.HashMap;


public class FileUserDataAccessObject implements ChangePasswordUserDataAccessInterface,
        LoginUserDataAccessInterface, LogoutUserDataAccessInterface, SignupUserDataAccessInterface {

    final private String userDataDirectory;
    final private DataEncryption dataEncryption;
    final private String fileType = ".json";
    final private HashMap<String, User> userList;

    /**
     * This takes in a userDataDirectory. It is typically the directory pong/userdata
     * and the data is stored in .txt files.
     *
     */
    public FileUserDataAccessObject(String userDataDirectory, DataEncryption dataEncryption) {
        this.userDataDirectory = userDataDirectory;
        this.dataEncryption = dataEncryption;
        userList = new HashMap<>();
    }

    private User getCommonUser() throws IOException {
        User commonUser = new CommonUserFactory().create();
        // Use BufferedReader to read the file
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(userDataDirectory))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException ex) {
            System.err.println("An error occurred while reading the file: " + ex.getMessage());
            throw ex;
        }

        String decryptedJsonContent = dataEncryption.decrypt(jsonContent.toString());

        // Parse the JSON content
        JsonObject jsonObject = JsonParser.parseString(decryptedJsonContent).getAsJsonObject();

        addUsername(commonUser, jsonObject);
        addPassword(commonUser, jsonObject);
        addTimePlayed(commonUser, jsonObject);
        addNumGames(commonUser, jsonObject);
        addNumWins(commonUser, jsonObject);
        return commonUser;
    }

    // This is a list of attribute adders that add various things to the user.

    private void addUsername(User user, JsonObject jsonObject) {
        if (jsonObject.has("username")) {
            user.setName(jsonObject.get("username").getAsString());
        }
    }
    private void addPassword(User user, JsonObject jsonObject) {
        if (jsonObject.has("password")) {
            user.setPassword(jsonObject.get("password").getAsString());
        }
    }
    private void addTimePlayed(User user, JsonObject jsonObject) {
        if (jsonObject.has("timePlayed")) {
            user.getDataSet().setPlayTime(jsonObject.get("timePlayed").getAsLong());
        }
    }
    private void addNumGames(User user, JsonObject jsonObject) {
        if (jsonObject.has("numGames")) {
            user.getDataSet().setNumberOfGames(jsonObject.get("numGames").getAsInt());
        }
    }
    private void addNumWins(User user, JsonObject jsonObject) {
        if (jsonObject.has("numWins")) {
            user.getDataSet().setNumberOfWins(jsonObject.get("numWins").getAsInt());
        }
    }


    /**
     * Updates the system to record this user's password.
     *
     * @param user the user whose password is to be updated
     */
    public void changePassword(String username, String newPassword) {

    }

    /**
     * Checks if the given username exists.
     * More specifically, it checks the directory for a file that matches {@code username.txt}
     * @param username the name of the file we are looking for
     * @return {@code true} if a file is found with the user data; {@code false} otherwise
     */
    @Override
    public boolean existsByName(String username) {
        String path = getUserDataDirectory(username);
        File file = new File(path);
        if(file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * Saves the user. It takes the DataSet from the User and
     * converts it into {@code .json} format to be saved.
     * @param user the user whose data we are going to save.
     */
    @Override
    public void save(User user) {
        final DataSet userData = user.getDataSet();
        final String encryptedData = dataEncryption.encrypt(userData.toString());
        final File file = new File(getUserDataDirectory(user.getName()));
        // TODO: unfinished
    }

    public void load(String name) {

    }

    /**
     * Returns the user with the given username.
     *
     * @param username the username to look up
     * @return the user with the given username
     */
    @Override
    public User get(String username) {

        // Use BufferedReader to read the file
        StringBuilder jsonContent = new StringBuilder();
        String file = getUserDataDirectory(username);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            // TODO: transform jsonContent into
        } catch (IOException ex) {
            // Hopefully, this should never happen. DAO.get(name) should never be called
            // if DAO.existsByName(name) is false.
            System.err.println("An error occurred while reading the file: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }


        return null;
    }



    private File getFile(String path) {
        return new File(userDataDirectory + File.separator + path + fileType);
    }

    /**
     * Returns the username of the curren user of the application.
     *
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    @Override
    public String getCurrentUsername() {
        return "";
    }

    /**
     * Sets the username indicating who is the current user of the application.
     *
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    @Override
    public void setCurrentUsername(String username) {

    }

    private String getUserDataDirectory(String username) {
        return userDataDirectory + File.separator + username + fileType;
    }

    /**
     * Updates the system to record this user's password.
     *
     * @param user the user whose password is to be updated
     */
    @Override
    public void changePassword(User user) {

    }
}
