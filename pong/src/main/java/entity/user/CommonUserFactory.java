package entity.user;
import entity.data.DataSet;
import entity.data.UserDataSet;

public class CommonUserFactory implements UserFactory {
    public User create() {
        return new CommonUser(null, null, new UserDataSet());
    }

    public User create(String name, String password) {
        return new CommonUser(name, password, new UserDataSet());
    }

    public User create(String name, String password, DataSet dataset) {
        return new CommonUser(name, password, dataset);
    }
}
