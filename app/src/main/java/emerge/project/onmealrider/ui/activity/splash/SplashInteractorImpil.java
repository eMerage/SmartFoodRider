package emerge.project.onmealrider.ui.activity.splash;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.servies.api.ApiClient;
import emerge.project.onmealrider.servies.api.ApiInterface;
import emerge.project.onmealrider.utils.entittes.ErrorObject;
import emerge.project.onmealrider.utils.entittes.Menus;
import emerge.project.onmealrider.utils.entittes.UpdateToken;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class SplashInteractorImpil implements SplashInteractor {

    Realm realm;

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    UpdateToken updateToken = new UpdateToken();


    String pushToken;

    @Override
    public void updatePushTokenAndAppVersion(Context con, final OnUpdatePushTokenAndAppVersionFinishedListener onUpdatePushTokenAndAppVersionFinishedListener) {
        realm = Realm.getDefaultInstance();
        int versionCode = 1;

        try {
            PackageInfo pInfo = con.getPackageManager().getPackageInfo(con.getPackageName(), 0);
            versionCode = pInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }

        int userID = 0;
        try {
            Rider rider = realm.where(Rider.class).findFirst();
            userID = rider.getRiderId();
        } catch (Exception ex) {

        }


        final int finalVersionCode = versionCode;
        final int finalUserID = userID;
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        } else {
                            pushToken = task.getResult().getToken();
                        }
                        updateTokenToServer(finalUserID, finalVersionCode, pushToken, onUpdatePushTokenAndAppVersionFinishedListener);

                    }
                });
    }


    private void updateTokenToServer(int userID, int versionCode, String token, final OnUpdatePushTokenAndAppVersionFinishedListener onUpdatePushTokenAndAppVersionFinishedListener) {
        final ErrorObject errorObject = new ErrorObject();
        errorObject.setErrCode("CE");
        errorObject.setErrDescription("Communication error, Please try again");

        try {
            try {

                Call<UpdateToken> call = apiService.saveMealTimeUserPushToken(userID,token,versionCode,"R","A");

                call.enqueue(new Callback<UpdateToken>() {
                    @Override
                    public void onResponse(Call<UpdateToken> call, Response<UpdateToken> response) {
                        updateToken= response.body();
                        onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(updateToken.isStatus(),updateToken);



                    }
                    @Override
                    public void onFailure(Call<UpdateToken> call, Throwable t) {
                        updateToken.setError(errorObject);
                        onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(false,updateToken);
                    }
                });

            } catch (Exception ex) {
                updateToken.setError(errorObject);
                onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(false,updateToken);
            }

        } catch (Exception ex) {
            updateToken.setError(errorObject);
            onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(false, updateToken);
        }


    }
}


