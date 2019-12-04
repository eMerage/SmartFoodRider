package emerge.project.onmealrider.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Orders implements Serializable {

    @SerializedName("orderID")
    int orderID;

    @SerializedName("orderDate")
    String orderDate;


    @SerializedName("userID")
    int userID;

    @SerializedName("orderTotal")
    Double orderTotal;

    @SerializedName("orderQty")
    int orderQty;

    @SerializedName("dispatchType")
    String dispatchType;

    @SerializedName("pickUpTime")
    String pickUpTime;

    @SerializedName("promoCode")
    String promoCode;

    @SerializedName("promoTitle")
    String promoTitle;

    @SerializedName("paymentTypeCode")
    String paymentTypeCode;


    @SerializedName("deliveryTime")
    DeliveryTime deliveryTime;

    @SerializedName("mealTimeUser")
    MealTimeUser mealTimeUser;

    @SerializedName("orderOutlet")
    OIrderOutlet oIrderOutlet;


    @SerializedName("statusCode")
    String statusCode;


    public Orders(int orderID, String orderDate, int userID, Double orderTotal, int orderQty, String dispatchType, String pickUpTime, String promoCode, String promoTitle,
                  DeliveryTime deliveryTime, MealTimeUser mealTimeUser, OIrderOutlet oIrderOutlet,String statusCode,String paymentType ) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.userID = userID;
        this.orderTotal = orderTotal;
        this.orderQty = orderQty;
        this.dispatchType = dispatchType;
        this.pickUpTime = pickUpTime;
        this.promoCode = promoCode;
        this.promoTitle = promoTitle;
        this.deliveryTime = deliveryTime;
        this.mealTimeUser = mealTimeUser;
        this.oIrderOutlet = oIrderOutlet;
        this.statusCode = statusCode;
        this.paymentTypeCode = paymentType;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public String getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(String dispatchType) {
        this.dispatchType = dispatchType;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public DeliveryTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(DeliveryTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public MealTimeUser getMealTimeUser() {
        return mealTimeUser;
    }

    public void setMealTimeUser(MealTimeUser mealTimeUser) {
        this.mealTimeUser = mealTimeUser;
    }

    public OIrderOutlet getoIrderOutlet() {
        return oIrderOutlet;
    }

    public void setoIrderOutlet(OIrderOutlet oIrderOutlet) {
        this.oIrderOutlet = oIrderOutlet;
    }


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }
}
