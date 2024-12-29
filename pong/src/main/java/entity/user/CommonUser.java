package entity.user;
import entity.data.DataSet;

public class CommonUser implements User{
    private DataSet dataSet;
    private String username;
    private String password;
    private int gamesPlayed;
    private int gamesWon;

    public CommonUser(String username, String password, DataSet dataSet, int gamesPlayed, int gamesWon) {
        this.dataSet = dataSet;
        this.username = username;
        this.password = password;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
    }


    public DataSet getDataSet() {
        return dataSet;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Sets the name of this user.
     *
     * @param name The new name of this user.
     */
    @Override
    public void setName(String name) {
        this.username = name;
    }

    /**
     * Sets the new password of the user.
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the new number of games played.
     *
     * @param gamesPlayed The new number of games played
     */
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Sets the number of games won.
     *
     * @param gamesWon The new number of games won.
     */
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    /**
     * Sets the new dataset.
     *
     * @param dataSet The new dataset.
     */
    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

}
