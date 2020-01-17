package emerge.project.onmealrider.ui.activity.splash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import emerge.project.onmealrider.R;
import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.servies.network.NetworkAvailability;
import emerge.project.onmealrider.ui.activity.home.Home;
import emerge.project.onmealrider.ui.activity.login.Login;
import emerge.project.onmealrider.utils.entittes.UpdateToken;
import io.realm.Realm;
import io.realm.RealmResults;

public class Splash extends Activity implements SplashView {


    Realm realm;

    SplashPresenter splashPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


        splashPresenter = new SplashPresenterImpli(this);

        realm = Realm.getDefaultInstance();


        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            splashPresenter.updatePushTokenAndAppVersion(this);
        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("No Internet Access, Please try again ");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();
        }



    }

    @Override
    public void updateStatus(Boolean status, final UpdateToken updateToken) {
        if(status){

            realm.beginTransaction();

            final Intent intent;
            RealmResults<Rider> allTransactions = realm.where(Rider.class).findAll();
            realm.commitTransaction();

            if(allTransactions.size()==0){
                intent = new Intent(Splash.this, Login.class);
            }else {
                intent = new Intent(Splash.this, Home.class);
            }

            new Handler().postDelayed(new Runnable() {
                public void run()  {
                    startActivity(intent);
                    finish();
                }
            }, 2000);


        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("App Update");
            alertDialogBuilder.setMessage(updateToken.getError().getErrDescription());

            if((updateToken.getError().getErrCode().equals("CE")) || (updateToken.getError().getErrCode().equals("SYSE")) ){
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Splash.this, "You can not processed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });


            }else {
                alertDialogBuilder.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateToken.getAppUrl()));
                                startActivity(browserIntent);

                                return;
                            }
                        });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Splash.this, "You can not processed", Toast.LENGTH_SHORT).show();

                        return;
                    }
                });

            }

            alertDialogBuilder.show();


        }




    }
}
