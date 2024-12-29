package data_access.encryption;

/**
 * A simple interface that allows the encryption and decryption of data.
 */
public interface DataEncryption {
    String encrypt(String data);
    String decrypt(String data);
}
