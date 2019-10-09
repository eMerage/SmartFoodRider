package emerge.project.onmealrider.servies.api;

import com.google.gson.JsonObject;

import java.util.List;


import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.utils.entittes.Orders;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Himanshu on 8/24/2017.
 */

public interface ApiInterface {


    @GET("MealTimeUser/ValidateUser")
    Call<Rider> userLoginValidation(@Query("eMail") String email, @Query("Password") String password);


    @GET("Order/GetOrdersForRider")
    Call<List<Orders>> getOrdersForRider(@Query("riderID") int riderID, @Query("orderStatusCode") String orderStatusCode);



    @POST("Order/UpdateOrderStatus")
    Call<Boolean> updateOrderStatus(@Query("OrderID") int orderID, @Query("statusCode") String statusCode, @Query("userID") int userID, @Query("userTypeCode") String userTypeCode, @Query("note") String note);


    @GET("Order/GetOrderHistoryByOrder")
    Call<JsonObject> orderHistorDetails(@Query("OrderID") int orderID);



}
