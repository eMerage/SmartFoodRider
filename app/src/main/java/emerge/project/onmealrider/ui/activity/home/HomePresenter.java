package emerge.project.onmealrider.ui.activity.home;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomePresenter {






    void getOrders(String statusCode);

    void updateOrderStatus(int orderId,String statusCode);

    void getOrdersFullDetails(int orderId);

    void getDirection(Double lan,Double lon);



}
