package emerge.project.onmealrider.servies.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


import emerge.project.onmealrider.data.db.Rider;
import emerge.project.onmealrider.utils.entittes.Orders;
import emerge.project.onmealrider.utils.entittes.UpdateToken;
import emerge.project.onmealrider.utils.entittes.User;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Himanshu on 8/24/2017.
 */

public interface ApiInterface {


    @GET("MealTimeUser/ValidateUser")
    Call<Rider> userLoginValidation(@Query("eMail") String email, @Query("Password") String password);


    @GET("MealTimeUser/GetUserByEmail")
    Call<User> getUserByEmail(@Query("email") String email, @Query("pushTokenId") String pushTokenId);




    @GET("Order/GetOrdersForRider")
    Call<ArrayList<Orders>> getOrdersForRider(@Query("userID") int riderID, @Query("orderStatusCode") String orderStatusCode);




    @POST("Order/UpdateOrderStatus")
    Call<Boolean> updateOrderStatus(@Query("OrderID") int orderID, @Query("statusCode") String statusCode, @Query("userID") int userID, @Query("userTypeCode") String userTypeCode, @Query("note") String note);


    @GET("Order/GetOrderHistoryByOrder")
    Call<JsonObject> orderHistorDetails(@Query("OrderID") int orderID);




    @POST("MealTime/SaveMealTimeUserPushToken")
    Call<UpdateToken> saveMealTimeUserPushToken(@Query("UserID") int userID, @Query("pushToken") String pushToken, @Query("appVersion") int appVersion,
                                                      @Query("AppType") String AppType, @Query("AppOs") String AppOs);









}
