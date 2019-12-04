package emerge.project.onmealrider.ui.activity.home;


import java.util.ArrayList;

import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.utils.entittes.Menus;
import emerge.project.onmealrider.utils.entittes.Orders;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeInteractor {

    interface OnGetOrdersFinishedListener {
        void ordersList(ArrayList<Orders> ordersArrayList);
        void noOrdersList();
        void ordersTimeOut(String statusCode);
    }
    void getOrders(String statusCode,OnGetOrdersFinishedListener onGetOrdersFinishedListener);


    interface OnUpdateOrderStatusFinishedListener {
        void updateOrderStatusStart();
        void updateOrderStatusSuccessful(int orderCurrentStatus);
        void updateOrderStatusFail(int orderId,String orderStatus,String msg);
    }
    void updateOrderStatus(int orderId,String statusCode, OnUpdateOrderStatusFinishedListener onUpdateOrderStatusFinishedListener);



    interface OnGetOrdersFullDetailsFinishedListener {
        void ordersFullDetailsFeedStart();
        void noOrdersFullDetailsAvailable();
        void ordersFullDetailsTimeOut(int orderId);
        void ordersFullDetails(ArrayList<Menus> menusArrayList);
    }
    void getOrdersFullDetails(int orderId, OnGetOrdersFullDetailsFinishedListener onGetOrdersFullDetailsFinishedListener);



    interface OnGetDirectionFinishedListener {
        void direction(Double lan,Double lon);
    }
    void getDirection(Double lan,Double lon,OnGetDirectionFinishedListener onGetDirectionFinishedListener);


    interface OnGetRiderDetailsFinishedListener {
        void getRiderDetails(Rider rider);
    }
    void getRider(OnGetRiderDetailsFinishedListener onGetRiderDetailsFinishedListener);



}
