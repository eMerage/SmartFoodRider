package emerge.project.onmealrider.ui.activity.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import emerge.project.onmealrider.R;
import emerge.project.onmealrider.servies.network.NetworkAvailability;
import emerge.project.onmealrider.ui.activity.history.History;
import emerge.project.onmealrider.ui.activity.login.Login;
import emerge.project.onmealrider.ui.adaptor.MenuAdapter;
import emerge.project.onmealrider.ui.adaptor.OrdersAdapter;
import emerge.project.onmealrider.utils.entittes.Menus;
import emerge.project.onmealrider.utils.entittes.Orders;

public class Home extends Activity implements HomeView {


    @BindView(R.id.listview_slider)
    ListView listViewSlider;

    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.recyclerview_main)
    RecyclerView recyclerviewMain;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    HomePresenter homePresenter;

    OrdersAdapter ordersAdapter;
    MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        homePresenter = new HomePresenterImpli(this);
        setRecycalView();

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            progressBar.setVisibility(View.VISIBLE);
            homePresenter.getOrders("ODPN");
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
                homePresenter.getOrders("ODPN");
            }
        });

    }


    @OnItemClick(R.id.listview_slider)
    public void onItemClick(int position) {
        dLayout.closeDrawer(Gravity.LEFT);
        Intent intent = new Intent(Home.this, History.class);
        startActivity(intent);
    }

    @OnClick(R.id.relativeLayout_slider_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void ordersList(ArrayList<Orders> ordersArrayList) {
        progressBar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        ordersAdapter = new OrdersAdapter(this,ordersArrayList,this);
        recyclerviewMain.setAdapter(ordersAdapter);


    }

    @Override
    public void noOrdersList() {
        progressBar.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        Toast.makeText(this, "No Orders", Toast.LENGTH_SHORT).show();

        ArrayList<Orders> ordersArrayList = new ArrayList<>();
        ordersAdapter = new OrdersAdapter(this,ordersArrayList,this);
        recyclerviewMain.setAdapter(ordersAdapter);

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
                                homePresenter.getOrders(statusCode);
                            }else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
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

    @Override
    public void updateOrderStatusStart() {
        bloackUserInteraction();
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateOrderStatusSuccessful(int orderCurrentStatus) {
        unBloackUserInteraction();
        homePresenter.getOrders("ODDS");

    }

    @Override
    public void updateOrderStatusFail(final int orderId, final String orderStatus, String msg) {
        unBloackUserInteraction();
        progressBar.setVisibility(View.GONE);


        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("Something went wrong, Please try again");
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                progressBar.setVisibility(View.VISIBLE);
                                bloackUserInteraction();
                                homePresenter.updateOrderStatus(orderId,orderStatus);
                            }else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
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

    @Override
    public void ordersFullDetailsFeedStart() {
        bloackUserInteraction();
        progressBar.setVisibility(View.VISIBLE);



    }

    @Override
    public void noOrdersFullDetailsAvailable() {
        unBloackUserInteraction();
        progressBar.setVisibility(View.GONE);

        Toast.makeText(this, "details not available", Toast.LENGTH_SHORT).show();




    }

    @Override
    public void ordersFullDetailsTimeOut(final int orderId) {

        unBloackUserInteraction();
        progressBar.setVisibility(View.GONE);

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("Something went wrong, Please try again");
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                progressBar.setVisibility(View.VISIBLE);
                                bloackUserInteraction();
                                homePresenter.getOrdersFullDetails(orderId);
                            }else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
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

    @Override
    public void ordersFullDetails(ArrayList<Menus> menusArrayList) {
        progressBar.setVisibility(View.GONE);
        unBloackUserInteraction();



        Dialog dialogBox;
        dialogBox = new Dialog(this);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dialog_order_subitems);
        dialogBox.setCancelable(true);


        RecyclerView recyclerViewOrderSubitems = (RecyclerView) dialogBox.findViewById(R.id.recyclerview_subcart_items);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewOrderSubitems.setLayoutManager(layoutManager);
        recyclerViewOrderSubitems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOrderSubitems.setNestedScrollingEnabled(true);


        menuAdapter = new MenuAdapter(this, menusArrayList);
        recyclerViewOrderSubitems.setAdapter(menuAdapter);


        dialogBox.show();

    }

    @Override
    public void direction(Double lan, Double lon) {


      //  Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        //Uri gmmIntentUri = Uri.parse("google.navigation:q="+lan+","+lon);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+lan+","+lon);

     //   Uri gmmIntentUri = Uri.parse("google.navigation:q=28.5675,77.3260");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }



    }


    private void setRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewMain.setLayoutManager(layoutManager);
        recyclerviewMain.setItemAnimator(new DefaultItemAnimator());
        recyclerviewMain.setNestedScrollingEnabled(false);

    }

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit!");
        alertDialogBuilder.setMessage("Do you really want to exit ?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alertDialogBuilder.show();

    }

}
