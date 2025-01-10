package entity.user;

import entity.data.DataSet;

/**
 * An interface that generates a new User.
 */
public interface UserFactory {

    /**
     * Creates a new User that is completely blank. It defaults to
     * having a null username and password, with a blank DataSet.
     *
     * @return the new user
     */
    User create();

    /**
     * Creates a new User that is blank, except for the name and password fields.
     * Specifically, it should initalize the User with an empty DataSet
     *
     * @param name the name of the new user
     * @param password the password of the new user
     * @return the new user
     */
    User create(String name, String password);

    /**
     * Creates a new User that is defined by the name, password, and dataset given.
     * @param name The User's name
     * @param password The User's password
     * @param dataSet The dataset of the User
     * @return A user that contains the information specified
     */
    User create(String name, String password, DataSet dataSet);
}
