package emerge.project.onmealrider.ui.activity.history;


import java.util.ArrayList;

import emerge.project.onmealrider.ui.activity.home.HomeInteractor;
import emerge.project.onmealrider.utils.entittes.Orders;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class HistoryPresenterImpli implements HistoryPresenter,HistoryInteractor.OnGetOrdersFinishedListener {


    private HistorytView historytView;
    HistoryInteractor historyInteractor;


    public HistoryPresenterImpli(HistorytView historytView) {
        this.historytView = historytView;
        this.historyInteractor = new HistoryInteractorImpil();

    }



    @Override
    public void getOrders(String statusCode) {
        historyInteractor.getOrders(statusCode,this);
    }


    @Override
    public void ordersList(ArrayList<Orders> ordersArrayList) {
        historytView.ordersList(ordersArrayList);
    }

    @Override
    public void noOrdersList() {
        historytView.noOrdersList();
    }

    @Override
    public void ordersTimeOut(String statusCode) {
        historytView.ordersTimeOut(statusCode);
    }


}
