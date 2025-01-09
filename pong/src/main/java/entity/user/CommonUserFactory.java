package entity.user;

import entity.data.DataSet;
import entity.data.UserDataSet;

/**
 * A implementation of the UserFactory where you can create different instances of
 * the CommonUser.
 */
public class CommonUserFactory implements UserFactory {
    /**
     * Creates a blank CommonUser with everything blank. It is initialized with a
     * blank UserDataSet.
     * @return A blank user
     */
    public User create() {
        return new CommonUser(null, null, new UserDataSet());
    }

    /**
     * Creates a new CommonUser with an empty UserDataSet.
     * @param name the name of the new user
     * @param password the password of the new user
     * @return A user with a name, password, and no data.
     */
    public User create(String name, String password) {
        return new CommonUser(name, password, new UserDataSet());
    }

    /**
     * Creates a user that has defined data. It gives this user the name, password, and
     * dataset. The dataset does not have to be a UserDataSet.
     * @param name The name of this user.
     * @param password The password of this user.
     * @param dataset The dataset that this user will have
     * @return A user with the data that was passed in.
     */
    public User create(String name, String password, DataSet dataset) {
        return new CommonUser(name, password, dataset);
    }
}
