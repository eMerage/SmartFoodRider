package emerge.project.onmealrider.ui.activity.splash;


import android.content.Context;

import emerge.project.onmealrider.utils.entittes.UpdateToken;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SplashInteractor {

    interface OnUpdatePushTokenAndAppVersionFinishedListener {
        void updateStatus(Boolean status, UpdateToken updateToken);
    }
    void updatePushTokenAndAppVersion(Context con, OnUpdatePushTokenAndAppVersionFinishedListener onUpdatePushTokenAndAppVersionFinishedListener);



}
