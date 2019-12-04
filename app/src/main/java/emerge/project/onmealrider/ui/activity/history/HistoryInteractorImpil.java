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




    @Override
    public void getOrders(final String statusCode, final OnGetOrdersFinishedListener onGetOrdersFinishedListener) {

        Rider rider = realm.where(Rider.class).findFirst();

        final ArrayList<Orders> ordersArrayList = new ArrayList<Orders>();


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Orders>> call = apiService.getOrdersForRider(rider.getRiderId(), statusCode);


        call.enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                List<Orders> ordersList = response.body();
                for (int i = 0; i < ordersList.size(); i++) {
                    ordersArrayList.add(new Orders(ordersList.get(i).getOrderID(), ordersList.get(i).getOrderDate(), ordersList.get(i).getUserID(),
                            ordersList.get(i).getOrderTotal(), ordersList.get(i).getOrderQty(),
                            ordersList.get(i).getDispatchType(), ordersList.get(i).getPickUpTime(), ordersList.get(i).getPromoCode(), ordersList.get(i).getPromoTitle(),
                            ordersList.get(i).getDeliveryTime(), ordersList.get(i).getMealTimeUser(),ordersList.get(i).getoIrderOutlet(),ordersList.get(i).getStatusCode(),ordersList.get(i).getPaymentTypeCode()));
                }

                if (ordersArrayList.isEmpty()) {
                    onGetOrdersFinishedListener.noOrdersList();
                } else {
                    onGetOrdersFinishedListener.ordersList(ordersArrayList);
                }


            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                onGetOrdersFinishedListener.ordersTimeOut(statusCode);
            }
        });
    }
}
