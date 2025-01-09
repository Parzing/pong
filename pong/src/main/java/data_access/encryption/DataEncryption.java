package data_access.encryption;

/**
 * A simple interface that allows the encryption and decryption of data.
 */
public interface DataEncryption {
    /**
     * Encrypts some data.
     * @param data The data to be encrypted
     * @return The encrypted data.
     */
    String encrypt(String data);

    /**
     * Decrypts some data.
     * @param data The data to be decrypted.
     * @return The decrypted data, ready to be used.
     */
    String decrypt(String data);
}
