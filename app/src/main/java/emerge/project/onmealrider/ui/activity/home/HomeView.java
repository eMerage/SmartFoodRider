package emerge.project.onmealrider.ui.activity.home;


import java.util.ArrayList;

import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.utils.entittes.Menus;
import emerge.project.onmealrider.utils.entittes.Orders;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeView {





    void ordersList(ArrayList<Orders> ordersArrayList);

    void noOrdersList();

    void ordersTimeOut(String statusCode);


    void updateOrderStatusStart();

    void updateOrderStatusSuccessful(int orderCurrentStatus);

    void updateOrderStatusFail(int orderId, String orderStatus, String msg);


    void ordersFullDetailsFeedStart();

    void noOrdersFullDetailsAvailable();

    void ordersFullDetailsTimeOut(int orderId);

    void ordersFullDetails(ArrayList<Menus> menusArrayList);

    void direction(Double lan,Double lon);

    void getRiderDetails(Rider rider);


}
