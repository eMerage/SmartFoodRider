package emerge.project.onmealrider.ui.activity.home;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class PushService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Home activity = Home.instance;
        if (activity != null) {
            activity.refrashOrdersWhenOrderProsess();
        }

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}

