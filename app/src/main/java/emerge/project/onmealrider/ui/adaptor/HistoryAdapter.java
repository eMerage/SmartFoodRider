package emerge.project.onmealrider.ui.adaptor;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.onmealrider.R;
import emerge.project.onmealrider.servies.network.NetworkAvailability;
import emerge.project.onmealrider.ui.activity.home.HomePresenter;
import emerge.project.onmealrider.ui.activity.home.HomePresenterImpli;
import emerge.project.onmealrider.ui.activity.home.HomeView;
import emerge.project.onmealrider.utils.entittes.Orders;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context mContext;

    ArrayList<Orders> ordersItems;



    public HistoryAdapter(Context mContext, ArrayList<Orders> item) {
        this.mContext = mContext;
        this.ordersItems = item;



    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_orders_history, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Orders orders = ordersItems.get(position);

        holder.textViewOrderNumber.setText(String.valueOf(orders.getOrderID()));
        holder.textViewTime.setText(orders.getDeliveryTime().getTimeSlot());
        holder.textViewOutlet.setText(orders.getoIrderOutlet().getName());
        holder.textViewAmount.setText(String.valueOf(orders.getOrderTotal()));
        holder.textViewQty.setText(String.valueOf(orders.getOrderQty()));
        holder.textViewCustomer.setText(orders.getMealTimeUser().getUserName());

        holder.textViewAddress.setText(orders.getMealTimeUser().getAddress());

        holder.textViewCustomerNumber.setText(orders.getMealTimeUser().getMobileNo());

        holder.textViewDate.setText(orders.getOrderDate().substring(0,10));



    }

    @Override
    public int getItemCount() {
        return ordersItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_ordernumber)
        TextView textViewOrderNumber;

        @BindView(R.id.textView_time)
        TextView textViewTime;

        @BindView(R.id.textView_outlet)
        TextView textViewOutlet;

        @BindView(R.id.textView_amount)
        TextView textViewAmount;

        @BindView(R.id.textView_qty)
        TextView textViewQty;

        @BindView(R.id.textView_customer)
        TextView textViewCustomer;

        @BindView(R.id.textView_address)
        TextView textViewAddress;

        @BindView(R.id.textView_customer_number)
        TextView textViewCustomerNumber;

        @BindView(R.id.textView_date)
        TextView textViewDate;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }



}
