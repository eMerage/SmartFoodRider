package emerge.project.onmealrider.ui.activity.history;


import java.util.ArrayList;

import emerge.project.onmealrider.ui.activity.home.HomeInteractor;
import emerge.project.onmealrider.utils.entittes.Orders;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HistoryInteractor {


    interface OnGetOrdersFinishedListener {
        void ordersList(ArrayList<Orders> ordersArrayList);
        void noOrdersList();
        void ordersTimeOut(String statusCode);
    }
    void getOrders(String statusCode,OnGetOrdersFinishedListener onGetOrdersFinishedListener);





}
