package entity.data;

public interface DataSet {
    /**
     * Sets the number of games in this dataset.
     * @param numberOfGames The new number of games.
     */
    void setNumberOfGames(int numberOfGames);

    /**
     * Sets the number of wins in this dataset.
     * @param numberOfWins The new number of wins.
     */
    void setNumberOfWins(int numberOfWins);

    /**
     * Sets the new playtime in this dataset.
     * @param playTime The new playtime
     */
    void setPlayTime(long playTime);

    /**
     * Gets the current number of games in this dataset.
     * @return The number of games played so far.
     */
    int getNumberOfGames();

    /**
     * Gets the number of wins in this dataset.
     * @return The number of games won.
     */
    int getNumberOfWins();

    /**
     * Gets the current play time from the dataset.
     * @return The time played by the user.
     */
    long getPlayTime();
    // TODO: more dataset functionality later
}
