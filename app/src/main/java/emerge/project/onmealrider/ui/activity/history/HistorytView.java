package emerge.project.onmealrider.ui.activity.history;


import java.util.ArrayList;

import emerge.project.onmealrider.utils.entittes.Orders;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HistorytView {




        void ordersList(ArrayList<Orders> ordersArrayList);
        void noOrdersList();
        void ordersTimeOut(String statusCode);



}
