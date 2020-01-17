package emerge.project.onmealrider.ui.activity.splash;


import android.content.Context;


import emerge.project.onmealrider.utils.entittes.UpdateToken;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class SplashPresenterImpli implements SplashPresenter,SplashInteractor.OnUpdatePushTokenAndAppVersionFinishedListener {


    private SplashView splashView;
    SplashInteractor landingInteractor;


    public SplashPresenterImpli(SplashView splashView) {
        this.splashView = splashView;
        this.landingInteractor = new SplashInteractorImpil();

    }





    @Override
    public void updatePushTokenAndAppVersion(Context con) {
        landingInteractor.updatePushTokenAndAppVersion( con,this);
    }



    @Override
    public void updateStatus(Boolean status, UpdateToken updateToken) {
        splashView.updateStatus(status,updateToken);
    }


}
