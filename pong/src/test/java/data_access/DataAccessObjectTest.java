package data_access;
import data_access.encryption.DataEncryption;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

public class DataAccessObjectTest {

    final static private int maxAttempts = 10;

    private static Stream<Arguments> provideAllTestCases() throws IOException {
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
        File mockUserDataOrigin = new File("src/test/java/data_access/MockUserDataOrigin.json");
        File mockUserDataPath = new File("src/test/java/data_access/MockUserData.json");
        int attemptNumber = 0;
        do{
            try {
                Files.copy(mockUserDataOrigin.toPath(), mockUserDataPath.toPath());
            } catch (Exception ex) {

            }
            attemptNumber++;
            if(attemptNumber >= maxAttempts) {
                System.out.println("Test setup failed");
                throw new IOException("test setup failed");
            }

        } while (!mockUserDataOrigin.equals(mockUserDataPath));

        return Stream.of(Arguments.of(
                new FileUserDataAccessObject(mockUserDataPath.getPath(), mockDataEncryption))
        );
    }

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    public void testSave(FileUserDataAccessObject dataAccessObject) {

    }

}
