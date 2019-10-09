package emerge.project.onmealrider.ui.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import emerge.project.onmealrider.R;
import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.ui.activity.home.Home;
import emerge.project.onmealrider.ui.activity.login.Login;
import io.realm.Realm;
import io.realm.RealmResults;

public class Splash extends Activity {


    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        realm = Realm.getDefaultInstance();
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



    }
}
