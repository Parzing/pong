package data_access.encryption;


/**
 * This is a default implementation of data encryption. For now, it does not do anything for
 * encryption and decryption. TODO: make the class encrypt and decrypt properly
 */
public class DefaultDataEncryption implements DataEncryption {
    @Override
    public String encrypt(String data) {
        return data;
    }

    @Override
    public String decrypt(String data) {
        return data;
    }
}
