package emerge.project.onmealrider.ui.activity.login;




/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LoginView {

//
    void userNameEmpty();
    void passwordEmpty();
    void passwordInvalid(String invalidReason);
    void loginValidationSuccessful();
    void loginValidationFail(String msg);
    void loginError(String msg);


}
