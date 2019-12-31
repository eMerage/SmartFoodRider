package emerge.project.onmealrider.ui.adaptor;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {

    Context mContext;

    ArrayList<Orders> ordersItems;

    HomePresenter homePresenter;

    public OrdersAdapter(Context mContext, ArrayList<Orders> item, HomeView homeView) {
        this.mContext = mContext;
        this.ordersItems = item;

        homePresenter = new HomePresenterImpli(homeView);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_orders, parent, false);

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
        holder.textViewCustomerNumber.setText("07X-XXX-XXXX");


        holder.textViewAmount.setText(String.valueOf(orders.getSubTotal()));
        holder.textViewAmountCustomer.setText(String.valueOf(orders.getOrderTotal()));






        if (orders.getPaymentTypeCode().equals("CH")) {
            holder.textViewPaymentType.setText("Cash");
            holder.textHintAmountOutlet.setVisibility(View.VISIBLE);
            holder.textViewAmount.setVisibility(View.VISIBLE);
        } else {
            holder.textHintAmountOutlet.setVisibility(View.GONE);
            holder.textViewAmount.setVisibility(View.GONE);

            holder.textViewPaymentType.setText("Card");
        }


        if (orders.getStatusCode().equals("ODDS")) {
            holder.buttonCollect.setVisibility(View.VISIBLE);
            holder.buttonDelivered.setVisibility(View.INVISIBLE);
        } else if (orders.getStatusCode().equals("ODCO")) {
            holder.buttonCollect.setVisibility(View.INVISIBLE);
            holder.buttonDelivered.setVisibility(View.VISIBLE);
        } else {
            holder.buttonCollect.setVisibility(View.INVISIBLE);
            holder.buttonDelivered.setVisibility(View.INVISIBLE);
        }

        holder.textViewNote.setText(orders.getOrderNote());





        holder.buttonCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkAvailability.isNetworkAvailable(mContext)) {
                    if (orders.getStatusCode().equals("ODDS")) {
                        homePresenter.updateOrderStatus(orders.getOrderID(), "ODCO");
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                        alertDialogBuilder.setTitle("Warning");
                        alertDialogBuilder.setMessage("Order need to dispatch");
                        alertDialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                });
                        alertDialogBuilder.show();
                    }

                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle("Warning");
                    alertDialogBuilder.setMessage("No Internet Access, Please try again ");
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    alertDialogBuilder.show();
                }
            }
        });


        holder.buttonDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkAvailability.isNetworkAvailable(mContext)) {
                    if (orders.getStatusCode().equals("ODCO")) {
                        homePresenter.updateOrderStatus(orders.getOrderID(), "ODDV");
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                        alertDialogBuilder.setTitle("Warning");
                        alertDialogBuilder.setMessage("Order need to collect");
                        alertDialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                });
                        alertDialogBuilder.show();
                    }

                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle("Warning");
                    alertDialogBuilder.setMessage("No Internet Access, Please try again ");
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    alertDialogBuilder.show();
                }
            }
        });


        holder.relativelayouMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkAvailability.isNetworkAvailable(mContext)) {
                    homePresenter.getOrdersFullDetails(orders.getOrderID());
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle("Warning");
                    alertDialogBuilder.setMessage("No Internet Access, Please try again ");
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    alertDialogBuilder.show();
                }


            }
        });


        holder.buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePresenter.getDirection(orders.getMealTimeUser().getLatitude(), orders.getMealTimeUser().getLongitude());
            }
        });

        holder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:"+orders.getMealTimeUser().getMobileNo()));

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) { Toast.makeText(mContext, "Call permission not granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    mContext.startActivity(call);
                }

                
            }
        });




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

        @BindView(R.id.textView_paymenttype)
        TextView textViewPaymentType;


        @BindView(R.id.button_collect)
        Button buttonCollect;

        @BindView(R.id.button_delivered)
        Button buttonDelivered;

        @BindView(R.id.button_map)
        Button buttonMap;

        @BindView(R.id.button_call)
        Button buttonCall;


        @BindView(R.id.relativelayout_main)
        RelativeLayout relativelayouMain;

        @BindView(R.id.textView_amount_customer)
        TextView textViewAmountCustomer;


        @BindView(R.id.textView_note)
        TextView textViewNote;


        @BindView(R.id.openSansLight4)
        TextView textHintAmountOutlet;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
