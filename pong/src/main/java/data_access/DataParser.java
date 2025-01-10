package data_access;

import entity.user.User;

public interface DataParser {
    /**
     * Converts the user to String format to be saved.
     * @param user The user to be saved.
     * @return A string representation of that user.
     */
    String serialize(User user);

    /**
     * Takes a string of data and parses it into a User.
     * @param data The data that contains the user's information.
     * @return A User containing that data.
     */
    User deserialize(String data);
}
