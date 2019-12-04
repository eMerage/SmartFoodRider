package emerge.project.onmealrider.ui.activity.home;


import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.servies.api.ApiClient;
import emerge.project.onmealrider.servies.api.ApiInterface;
import emerge.project.onmealrider.utils.entittes.Menus;
import emerge.project.onmealrider.utils.entittes.Orders;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class HomeInteractorImpil implements HomeInteractor {


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
                            ordersList.get(i).getDeliveryTime(), ordersList.get(i).getMealTimeUser(),
                            ordersList.get(i).getoIrderOutlet(), ordersList.get(i).getStatusCode(),ordersList.get(i).getPaymentTypeCode()));
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

    @Override
    public void updateOrderStatus(final int orderId, final String statusCode, final OnUpdateOrderStatusFinishedListener onUpdateOrderStatusFinishedListener) {


        onUpdateOrderStatusFinishedListener.updateOrderStatusStart();
        final Rider rider = realm.where(Rider.class).findFirst();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Boolean> call = apiService.updateOrderStatus(orderId, statusCode, rider.getRiderId(), "M", String.valueOf(orderId) + "-" + statusCode);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean status = response.body();


                int orderCurrentStatus = 0;

                if (statusCode.equals("ODPN")) {
                    orderCurrentStatus = 0;
                } else if (statusCode.equals("ODPR")) {
                    orderCurrentStatus = 1;
                } else if (statusCode.equals("ODPK")) {
                    orderCurrentStatus = 2;
                } else if (statusCode.equals("ODDS")) {
                    orderCurrentStatus = 3;
                } else if (statusCode.equals("ODDV")) {
                    orderCurrentStatus = 4;
                }


                if (status) {
                    if(statusCode.equals("ODDV")){
                        updateOrderStatusToFinsh(orderId,rider.getRiderId(),onUpdateOrderStatusFinishedListener);
                    }else {
                        onUpdateOrderStatusFinishedListener.updateOrderStatusSuccessful(orderCurrentStatus);
                    }

                } else {
                    onUpdateOrderStatusFinishedListener.updateOrderStatusFail(orderId,statusCode,"Update Fail");

                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });


    }


    public void updateOrderStatusToFinsh(final int orderId, final int riderID,final OnUpdateOrderStatusFinishedListener onUpdateOrderStatusFinishedListener) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Boolean> call = apiService.updateOrderStatus(orderId, "ODCP",riderID, "M", String.valueOf(orderId) + "-" + "ODCP");
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean status = response.body();

                if (status) {
                    onUpdateOrderStatusFinishedListener.updateOrderStatusSuccessful(4);
                } else {
                    onUpdateOrderStatusFinishedListener.updateOrderStatusFail(orderId,"ODCP","Update Fail");
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });

    }


    @Override
    public void getOrdersFullDetails(final int orderId, final OnGetOrdersFullDetailsFinishedListener onGetOrdersFullDetailsFinishedListener) {
        onGetOrdersFullDetailsFinishedListener.ordersFullDetailsFeedStart();

        final ArrayList<Menus> menusArrayList = new ArrayList<Menus>();//main


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.orderHistorDetails(orderId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                JSONObject historyListist = null;
                try {
                    historyListist = new JSONObject(response.body().toString());
                    JSONArray orderMenusList;

                    orderMenusList = historyListist.getJSONArray("orderMenus");
                    for (int i = 0; i < orderMenusList.length(); i++) {
                        JSONObject jsonData = orderMenusList.getJSONObject(i);

                        menusArrayList.add(new Menus(jsonData.getInt("orderID"), jsonData.getString("id"), jsonData.getString("name"),
                                jsonData.getString("menuSizeCode"), jsonData.getDouble("menuPrice"), jsonData.getInt("menuQty")));

                    }
                    onGetOrdersFullDetailsFinishedListener.ordersFullDetails(menusArrayList);


                } catch (JSONException e) {
                    onGetOrdersFullDetailsFinishedListener.noOrdersFullDetailsAvailable();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                onGetOrdersFullDetailsFinishedListener.ordersFullDetailsTimeOut(orderId);
            }
        });

    }

    @Override
    public void getDirection(Double lan, Double lon, OnGetDirectionFinishedListener onGetDirectionFinishedListener) {
        onGetDirectionFinishedListener.direction(lan,lon);
    }

    @Override
    public void getRider(OnGetRiderDetailsFinishedListener onGetRiderDetailsFinishedListener) {

        final Rider rider = realm.where(Rider.class).findFirst();
        onGetRiderDetailsFinishedListener.getRiderDetails(rider);


    }
}
