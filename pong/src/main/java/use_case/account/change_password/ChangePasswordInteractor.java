package use_case.account.change_password;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordOutputBoundary) {
        this.userDataAccessObject = changePasswordDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        userDataAccessObject.changePassword(changePasswordInputData.getPassword());
        final ChangePasswordOutputData changePasswordOutputData =
                new ChangePasswordOutputData(changePasswordInputData.getUsername(), false);
        userPresenter.prepareSuccessView(changePasswordOutputData);
    }
}
