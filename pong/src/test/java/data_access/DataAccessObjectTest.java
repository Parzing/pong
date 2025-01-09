package data_access;
import data_access.encryption.DataEncryption;
import entity.data.DataSet;
import entity.data.UserDataSet;
import entity.user.CommonUserFactory;
import entity.user.User;

import entity.user.UserFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class DataAccessObjectTest {

    final static private int maxAttempts = 10;
    final private UserFactory userFactory = new CommonUserFactory();

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

        // Copy the mockUserData to the one that will be manipulated by the DAO
        resetMockUserFile(mockUserDataOrigin, mockUserDataPath);

        return Stream.of(Arguments.of(
            new FileUserDataAccessObject("pong\\src\\test\\java\\data_access", mockDataEncryption, "MockUserData"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    public void testSaveAndLoad(FileUserDataAccessObject dataAccessObject) {
        DataSet testDataSet = new UserDataSet(4, 1, 50000);
        User testUser = userFactory.create("username", "password", testDataSet);
        User DAOUser = dataAccessObject.get("MockUserData");
        // test load
        assert usersEqual(testUser, DAOUser);




    }

    private boolean usersEqual(User user1, User user2) {
        return user1.getName().equals(user2.getName()) &&
                user1.getPassword().equals(user2.getPassword()) &&
                user1.getDataSet().getPlayTime() == user2.getDataSet().getPlayTime() &&
                user1.getDataSet().getNumberOfGames() == user2.getDataSet().getNumberOfGames() &&
                user1.getDataSet().getNumberOfWins() == user2.getDataSet().getNumberOfWins();
    }

    private static void resetMockUserFile(File mockUserDataOrigin, File mockUserDataPath) throws IOException {
        int attemptNumber = 0;
        do{
            try {
                Files.copy(mockUserDataOrigin.toPath(), mockUserDataPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {
                attemptNumber++;
                if(attemptNumber >= maxAttempts) {
                    System.out.println("Test setup failed");
                    throw new IOException("test setup failed");
                }
            }
        } while (Files.mismatch(mockUserDataOrigin.toPath(), mockUserDataPath.toPath()) != -1);

    }

}
