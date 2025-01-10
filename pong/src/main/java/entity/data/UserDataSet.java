package entity.data;

public class UserDataSet implements DataSet {

    private int numberOfGames;
    private int numberOfWins;
    private long playTime;

    public UserDataSet() {
        numberOfGames = 0;
        numberOfWins = 0;
        playTime = 0;
    }

    public UserDataSet(int numberOfGames, int numberOfWins, long playTime) {
        this.numberOfGames = numberOfGames;
        this.numberOfWins = numberOfWins;
        this.playTime = playTime;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public long getPlayTime() {
        return playTime;
    }
}
