package use_case.account.change_password;

/**
 * The interface of the DAO for the Change Password Use Case.
 */
public interface ChangePasswordUserDataAccessInterface {

    /**
     * Updates the system to record this user's password.
     * @param newPassword the new password that the user will have.
     */
    void changePassword(String newPassword);
}
