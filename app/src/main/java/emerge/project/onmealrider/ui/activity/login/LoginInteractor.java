package emerge.project.onmealrider.ui.activity.login;




/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LoginInteractor {


    interface OnLoginValidationFinishedListener {
        void userNameEmpty();
        void passwordEmpty();
        void passwordInvalid(String invalidReason);
        void loginValidationSuccessful();
        void loginValidationFail(String msg);
        void loginError(String msg);
    }
    void checkLoginValidation(String userName, String password, OnLoginValidationFinishedListener onLoginValidationFinishedListener);




}
