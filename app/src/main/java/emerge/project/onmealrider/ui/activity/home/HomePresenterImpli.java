package emerge.project.onmealrider.ui.activity.home;


import java.util.ArrayList;

import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.ui.activity.history.HistoryInteractor;
import emerge.project.onmealrider.utils.entittes.Menus;
import emerge.project.onmealrider.utils.entittes.Orders;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class HomePresenterImpli implements HomePresenter,
        HomeInteractor.OnGetOrdersFinishedListener,
        HomeInteractor.OnUpdateOrderStatusFinishedListener,
HomeInteractor.OnGetOrdersFullDetailsFinishedListener
        ,HomeInteractor.OnGetDirectionFinishedListener,
HomeInteractor.OnGetRiderDetailsFinishedListener{


    private HomeView homeView;

    HomeInteractor homeInteractor;


    public HomePresenterImpli(HomeView homeViewByFood) {
        this.homeView = homeViewByFood;
        this.homeInteractor = new HomeInteractorImpil();

    }





    @Override
    public void getOrders(String statusCode) {
        homeInteractor.getOrders(statusCode,this);
    }



    @Override
    public void ordersList(ArrayList<Orders> ordersArrayList) {
        homeView.ordersList(ordersArrayList);
    }

    @Override
    public void noOrdersList() {
        homeView.noOrdersList();
    }

    @Override
    public void ordersTimeOut(String statusCode) {
        homeView.ordersTimeOut(statusCode);
    }










    @Override
    public void updateOrderStatus(int orderId, String statusCode) {
        homeInteractor.updateOrderStatus(orderId,statusCode,this);
    }




    @Override
    public void updateOrderStatusStart() {
        homeView.updateOrderStatusStart();
    }

    @Override
    public void updateOrderStatusSuccessful(int orderCurrentStatus) {
        homeView.updateOrderStatusSuccessful(orderCurrentStatus);
    }

    @Override
    public void updateOrderStatusFail(int orderId, String orderStatus,String msg) {
        homeView.updateOrderStatusFail(orderId,orderStatus,msg);
    }










    @Override
    public void getOrdersFullDetails(int orderId) {
        homeInteractor.getOrdersFullDetails(orderId,this);
    }




    @Override
    public void ordersFullDetailsFeedStart() {
        homeView.ordersFullDetailsFeedStart();
    }

    @Override
    public void noOrdersFullDetailsAvailable() {
        homeView.noOrdersFullDetailsAvailable();
    }

    @Override
    public void ordersFullDetailsTimeOut(int orderId) {
        homeView.ordersFullDetailsTimeOut(orderId);
    }

    @Override
    public void ordersFullDetails(ArrayList<Menus> menusArrayList) {
        homeView.ordersFullDetails(menusArrayList);
    }




    @Override
    public void getDirection(Double lan, Double lon) {
        homeInteractor.getDirection(lan,lon,this);
    }



    @Override
    public void direction(Double lan, Double lon) {
        homeView.direction(lan,lon);
    }



    @Override
    public void getRider() {
        homeInteractor.getRider(this);
    }

    @Override
    public void getRiderDetails(Rider rider) {
        homeView.getRiderDetails(rider);
    }
}
