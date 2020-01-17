package emerge.project.onmealrider.ui.activity.login;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.servies.api.ApiClient;
import emerge.project.onmealrider.servies.api.ApiInterface;
import emerge.project.onmealrider.utils.entittes.ErrorObject;
import emerge.project.onmealrider.utils.entittes.UpdateToken;
import emerge.project.onmealrider.utils.entittes.User;
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

public class LoginInteractorImpil implements LoginInteractor {

    Realm realm;

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    EncryptedPreferences encryptedPreferences;
    private static final String PUSH_TOKEN = "pushToken";


    UpdateToken updateToken = new UpdateToken();


    String pushToken;

    @Override
    public void checkLoginValidation(final Context context, final String userName, String password, final OnLoginValidationFinishedListener onLoginValidationFinishedListener) {

        if (userName.isEmpty() || userName.equals("") || userName == null) {
            onLoginValidationFinishedListener.userNameEmpty();
        } else if (password.isEmpty() || password.equals("") || password == null) {
            onLoginValidationFinishedListener.passwordEmpty();
        } else {

            Call<Rider> call = apiService.userLoginValidation(userName, password);

            call.enqueue(new Callback<Rider>() {
                @Override
                public void onResponse(Call<Rider> call, Response<Rider> response) {
                    Rider userDetailsArray = response.body();
                    if (userDetailsArray.getRiderId() == 0) {
                        onLoginValidationFinishedListener.loginValidationFail("Invalid login details,Please try again");

                    } else {
                        saveOutlet(context, userDetailsArray, userName, onLoginValidationFinishedListener);

                    }

                }

                @Override
                public void onFailure(Call<Rider> call, Throwable t) {
                    onLoginValidationFinishedListener.loginError(t.toString());
                }
            });

        }


    }


    private void saveOutlet(final Context context, final Rider rider, final String userName, final OnLoginValidationFinishedListener onLoginValidationFinishedListener) {

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                final Long userList = bgRealm.where(Rider.class).count();
                Rider riderDb = bgRealm.createObject(Rider.class, (userList.intValue() + 1));


                riderDb.setRiderId(rider.getRiderId());
                riderDb.setRidername(rider.getRidername());

                updatePushTokan(context, userName, onLoginValidationFinishedListener);

            }
        });


    }

    private void updatePushTokan(Context context, String userName, final OnLoginValidationFinishedListener onLoginValidationFinishedListener) {
        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
        String userPushTokenId = encryptedPreferences.getString(PUSH_TOKEN, "");


        try {

            Call<User> call = apiService.getUserByEmail(userName, userPushTokenId);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User userDetailsArray = response.body();
                    if (userDetailsArray == null) {
                        onLoginValidationFinishedListener.loginError("");
                    } else {
                        onLoginValidationFinishedListener.loginValidationSuccessful();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    onLoginValidationFinishedListener.loginError(t.toString());
                }
            });


        } catch (Exception ex) {
            onLoginValidationFinishedListener.loginError("");
        }


    }

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

            Call<UpdateToken> call = apiService.saveMealTimeUserPushToken(userID, token, versionCode, "R", "A");

            call.enqueue(new Callback<UpdateToken>() {
                @Override
                public void onResponse(Call<UpdateToken> call, Response<UpdateToken> response) {
                    updateToken = response.body();
                    onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(updateToken.isStatus(), updateToken);
                }

                @Override
                public void onFailure(Call<UpdateToken> call, Throwable t) {
                    updateToken.setError(errorObject);
                    onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(false, updateToken);
                }
            });

        } catch (Exception ex) {
            updateToken.setError(errorObject);
            onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(false, updateToken);
        }

    }

}
