package emerge.project.onmealrider.ui.activity.login;


import android.content.Context;

import emerge.project.onmealrider.utils.entittes.UpdateToken;

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
    void checkLoginValidation(Context context,String userName, String password, OnLoginValidationFinishedListener onLoginValidationFinishedListener);


    interface OnUpdatePushTokenAndAppVersionFinishedListener {
        void updateStatus(Boolean status, UpdateToken updateToken);
    }
    void updatePushTokenAndAppVersion(Context con, OnUpdatePushTokenAndAppVersionFinishedListener onUpdatePushTokenAndAppVersionFinishedListener);



}
