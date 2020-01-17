package emerge.project.onmealrider.ui.activity.login;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LoginPresenter {

    void checkLoginValidation(Context context,String userName, String password);

    void updatePushTokenAndAppVersion(Context con);

}
