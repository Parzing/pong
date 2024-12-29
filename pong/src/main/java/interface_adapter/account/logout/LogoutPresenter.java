package interface_adapter.account.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.account.change_password.LoggedInState;
import interface_adapter.account.change_password.LoggedInViewModel;
import interface_adapter.account.login.LoginState;
import interface_adapter.account.login.LoginViewModel;
import use_case.account.logout.LogoutOutputBoundary;
import use_case.account.logout.LogoutOutputData;

/**
 * The Presenter for the Logout Use Case.
 */
public class LogoutPresenter implements LogoutOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;
    private final LoginViewModel loginViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                           LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData response) {
        // We need to switch to the login view, which should have
        // an empty username and password.

        // We also need to set the username in the LoggedInState to
        // the empty string.

        final LoggedInState newState = loggedInViewModel.getState();
        newState.setUsername("");
        loggedInViewModel.setState(newState);
        loggedInViewModel.firePropertyChanged();
        // 1. get the LoggedInState out of the appropriate View Model,
        // 2. set the username in the state to the empty string
        // 3. set the state in the LoggedInViewModel to the updated state
        // 4. firePropertyChanged so that the View that is listening is updated.

        final LoginState newLoginState = loginViewModel.getState();
        newLoginState.setUsername("");
        newLoginState.setPassword("");
        loginViewModel.setState(newLoginState);
        loginViewModel.firePropertyChanged();
        // 5. get the LoginState out of the appropriate View Model,
        // 6. set the username and password in the state to the empty string
        // 7. set the state in the LoginViewModel to the updated state
        // 8. firePropertyChanged so that the View that is listening is updated.

        // This code tells the View Manager to switch to the LoginView.
        this.viewManagerModel.setState(loginViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // No need to add code here. We'll assume that logout can't fail.
        // Thought question: is this a reasonable assumption?
    }
}
