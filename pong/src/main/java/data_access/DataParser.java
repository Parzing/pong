package data_access;

import entity.user.User;
public interface DataParser {
    String save(User user);
    User load(String data);

}
