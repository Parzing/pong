package data_access;
import data_access.encryption.DataEncryption;
import entity.data.UserDataSet;
import entity.user.CommonUser;
import entity.user.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.util.stream.Stream;

public class DataAccessObjectTest {

    final static private int maxAttempts = 10;

    private static Stream<Arguments> provideAllTestCases() throws IOException {
        // A mock data encryption.
        DataEncryption mockDataEncryption = new DataEncryption() {
            @Override
            public String encrypt(String data) {
                return data;
            }

            @Override
            public String decrypt(String data) {
                return data;
            }
        };
        File mockUserDataOrigin = new File("pong\\src\\test\\java\\data_access\\MockUserDataOrigin.txt");
        File mockUserDataPath = new File("pong\\src\\test\\java\\data_access\\MockUserData.txt");
        int attemptNumber = 0;
        do{
            try {
                Files.copy(mockUserDataOrigin.toPath(), mockUserDataPath.toPath());
            } catch (Exception ex) {
                attemptNumber++;
                if(attemptNumber >= maxAttempts) {
                    System.out.println("Test setup failed");
                    throw new IOException("test setup failed");
                }
            }
        } while (Files.mismatch(mockUserDataOrigin.toPath(), mockUserDataPath.toPath()) != -1);

        return Stream.of(Arguments.of(
                new FileUserDataAccessObject("pong\\src\\test\\java\\data_access", mockDataEncryption, "MockUserData"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    public void testSave(FileUserDataAccessObject dataAccessObject) {

        User testUser = new CommonUser("username", "password", new UserDataSet(4, 1, 50000));
        User DAOUser = dataAccessObject.get("MockUserData");

        assert usersEqual(testUser, DAOUser);
    }

    private boolean usersEqual(User u1, User u2) {
        return u1.getName().equals(u2.getName()) &&
                u1.getPassword().equals(u2.getPassword()) &&
                u1.getDataSet().getPlayTime() == u2.getDataSet().getPlayTime() &&
                u1.getDataSet().getNumberOfGames() == u2.getDataSet().getNumberOfGames() &&
                u1.getDataSet().getNumberOfWins() == u2.getDataSet().getNumberOfWins();
    }

}
