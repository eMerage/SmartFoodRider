package emerge.project.onmealrider.ui.activity.history;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmealrider.R;
import emerge.project.onmealrider.servies.network.NetworkAvailability;
import emerge.project.onmealrider.ui.activity.home.Home;
import emerge.project.onmealrider.ui.adaptor.HistoryAdapter;
import emerge.project.onmealrider.ui.adaptor.OrdersAdapter;
import emerge.project.onmealrider.utils.entittes.Orders;

public class History extends Activity implements HistorytView{



    @BindView(R.id.recyclerview_main)
    RecyclerView recyclerviewMain;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    HistoryAdapter historyAdapter;
    HistoryPresenter historyPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        historyPresenter = new HistoryPresenterImpli(this);
        setRecycalView();



        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            progressBar.setVisibility(View.VISIBLE);
            historyPresenter.getOrders("ODDV");
        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                historyPresenter.getOrders("ODDV");
            }
        });



    }


    @OnClick(R.id.relativeLayout_slider_menu)
    public void onClickSliderMenue(View view) {
       finish();
    }

    @Override
    public void ordersList(ArrayList<Orders> ordersArrayList) {
        progressBar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        historyAdapter = new HistoryAdapter(this,ordersArrayList);
        recyclerviewMain.setAdapter(historyAdapter);
    }

    @Override
    public void noOrdersList() {
        progressBar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        Toast.makeText(this, "No Orders", Toast.LENGTH_SHORT).show();

        ArrayList<Orders> ordersArrayList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(this,ordersArrayList);
        recyclerviewMain.setAdapter(historyAdapter);
    }

    @Override
    public void ordersTimeOut(final String statusCode) {
        progressBar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("Something went wrong, Please try again");
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                progressBar.setVisibility(View.VISIBLE);
                                historyPresenter.getOrders(statusCode);
                            }else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(History.this);
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
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();

        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }


    private void setRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewMain.setLayoutManager(layoutManager);
        recyclerviewMain.setItemAnimator(new DefaultItemAnimator());
        recyclerviewMain.setNestedScrollingEnabled(false);

    }
}
