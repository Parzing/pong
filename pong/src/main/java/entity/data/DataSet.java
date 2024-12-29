package entity.data;

public interface DataSet {
    void setNumberOfGames(int numberOfGames);
    void setNumberOfWins(int numberOfWins);
    void setPlayTime(long playTime);

    int getNumberOfGames();
    int getNumberOfWins();
    int getPlayTime();
    // TODO: more dataset functionality later
}
