package emerge.project.onmealrider.ui.activity.login;


import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.servies.api.ApiClient;
import emerge.project.onmealrider.servies.api.ApiInterface;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Himanshu on 4/5/2017.
 */

public class LoginInteractorImpil implements LoginInteractor {

    Realm realm;


    @Override
    public void checkLoginValidation(String userName, String password, final OnLoginValidationFinishedListener onLoginValidationFinishedListener) {

       if(userName.isEmpty()|| userName.equals("") || userName ==null){
           onLoginValidationFinishedListener.userNameEmpty();
       }else if(password.isEmpty()|| password.equals("") || password ==null){
           onLoginValidationFinishedListener.passwordEmpty();
       }else {
           ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
           Call<Rider> call = apiService.userLoginValidation(userName,password);

           call.enqueue(new Callback<Rider>() {
               @Override
               public void onResponse(Call<Rider> call, Response<Rider> response) {
                   Rider userDetailsArray = response.body();
                   if(userDetailsArray.getRiderId()==0 ){
                       onLoginValidationFinishedListener.loginValidationFail("Invalid login details,Please try again");

                   }else {
                       saveOutlet(userDetailsArray,onLoginValidationFinishedListener);

                   }

               }
               @Override
               public void onFailure(Call<Rider> call, Throwable t) {
                   onLoginValidationFinishedListener.loginError(t.toString());
               }
           });

       }


    }


    private void saveOutlet(final Rider rider, final OnLoginValidationFinishedListener onLoginValidationFinishedListener){

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                final Long userList = bgRealm.where(Rider.class).count();
                Rider riderDb = bgRealm.createObject(Rider.class, (userList.intValue()+1));
                
                
                riderDb.setRiderId(rider.getRiderId());
                riderDb.setRidername(rider.getRidername());

                onLoginValidationFinishedListener.loginValidationSuccessful();

            }
        });


    }

}
