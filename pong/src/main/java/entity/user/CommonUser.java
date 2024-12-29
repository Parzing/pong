package entity.user;
import entity.data.DataSet;

public class CommonUser implements User{
    private DataSet dataSet;
    private String username;
    private String password;

    public CommonUser(String username, String password, DataSet dataSet) {
        this.dataSet = dataSet;
        this.username = username;
        this.password = password;
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
     * Sets the new dataset.
     *
     * @param dataSet The new dataset.
     */
    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

}
