package entity.user;
import entity.data.DataSet;


/**
 * An interface that generates a new User.
 */
public interface UserFactory {
    /**
     * Creates a new User that is blank, except for the name and password fields.
     * Specifically, it should initalize the User with an empty DataSet
     *
     * @param name the name of the new user
     * @param password the password of the new user
     * @return the new user
     */
    User create(String name, String password);


    User create(String name, String password, DataSet dataSet);
}
