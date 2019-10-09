package emerge.project.onmealrider.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MealTimeUser implements Serializable {


    @SerializedName("id")
    String id;

    @SerializedName("name")
    String userName;

    @SerializedName("contactNo")
    String contactNo;

    @SerializedName("imageUrl")
    String imageUrl;


    @SerializedName("address")
    String address;

    @SerializedName("latitude")
    Double latitude;

    @SerializedName("longitude")
    Double longitude;


    @SerializedName("mobileNo")
    String mobileNo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
