package emerge.project.onmealrider.ui.activity.history;



import java.util.ArrayList;
import java.util.List;

import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.servies.api.ApiClient;
import emerge.project.onmealrider.servies.api.ApiInterface;
import emerge.project.onmealrider.ui.activity.home.HomeInteractor;
import emerge.project.onmealrider.utils.entittes.Orders;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Himanshu on 4/5/2017.
 */

public class HistoryInteractorImpil implements HistoryInteractor {


    Realm realm = Realm.getDefaultInstance();

    ArrayList<Orders> ordersArrayList = new ArrayList<Orders>();


    @Override
    public void getOrders(final String statusCode, final OnGetOrdersFinishedListener onGetOrdersFinishedListener) {

        Rider rider = realm.where(Rider.class).findFirst();




        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<Orders>> call = apiService.getOrdersForRider(rider.getRiderId(), statusCode);


        call.enqueue(new Callback<ArrayList<Orders>>() {
            @Override
            public void onResponse(Call<ArrayList<Orders>> call, Response<ArrayList<Orders>> response) {
                ordersArrayList = response.body();
                if (ordersArrayList.isEmpty()) {
                    onGetOrdersFinishedListener.noOrdersList();
                } else {
                    onGetOrdersFinishedListener.ordersList(ordersArrayList);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Orders>> call, Throwable t) {
                onGetOrdersFinishedListener.ordersTimeOut(statusCode);
            }
        });
    }
}
