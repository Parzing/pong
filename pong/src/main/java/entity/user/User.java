package entity.user;
import entity.data.DataSet;
import java.util.UUID;

/**
 * The representation of a user in our program.
 */
public interface User {
    /**
     * Sets the name of this user.
     * @param name The new name of this user.
     */
    void setName(String name);

    /**
     * Sets the new password of the user.
     * @param password The new password.
     */
    void setPassword(String password);

    /**
     * Sets the new dataset.
     * @param dataSet The new dataset.
     */
    void setDataSet(DataSet dataSet);

    /**
     * Gets the current username.
     * @return the username of the user
     */
    String getName();

    /**
     * Gets this user's password
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Gets the dataset corresponding to this user.
     * @return the dataset corresponding to the user.
     */
    DataSet getDataSet();
}
