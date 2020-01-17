package emerge.project.onmealrider.ui.activity.login;


import android.content.Context;

import emerge.project.onmealrider.utils.entittes.UpdateToken;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class LoginPresenterImpli implements LoginPresenter,
        LoginInteractor.OnLoginValidationFinishedListener,
        LoginInteractor.OnUpdatePushTokenAndAppVersionFinishedListener{


    private LoginView loginView;
    LoginInteractor loginInteractor;


    public LoginPresenterImpli(LoginView loginview) {
        this.loginView = loginview;
        this.loginInteractor = new LoginInteractorImpil();

    }


    @Override
    public void userNameEmpty() {
        loginView.userNameEmpty();
    }

    @Override
    public void passwordEmpty() {
        loginView.passwordEmpty();
    }

    @Override
    public void passwordInvalid(String invalidReason) {
        loginView.passwordInvalid(invalidReason);
    }

    @Override
    public void loginValidationSuccessful() {
        loginView.loginValidationSuccessful();
    }

    @Override
    public void loginValidationFail(String msg) {
        loginView.loginValidationFail(msg);
    }

    @Override
    public void loginError(String msg) {
        loginView.loginError(msg);
    }

    @Override
    public void checkLoginValidation(Context context, String userName, String password) {
        loginInteractor.checkLoginValidation( context,userName,password,this);
    }


    @Override
    public void updatePushTokenAndAppVersion(Context con) {
        loginInteractor.updatePushTokenAndAppVersion( con,this);
    }



    @Override
    public void updateStatus(Boolean status, UpdateToken updateToken) {
        loginView.updateStatus(status,updateToken);
    }
}
