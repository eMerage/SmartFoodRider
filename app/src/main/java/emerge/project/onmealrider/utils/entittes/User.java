package emerge.project.onmealrider.utils.entittes;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String addressNo; //#112
    String addressRoad;//NarahenpitaNawala Road
    String departmentName;

    String address;//eMerge Solutions (Pvt) Ltd
    String city;//Colombo
    String mobileNo;
    String name;


    public User() {
    }

    public User(String addressNo, String addressRoad, String departmentName, String address, String city, String mobileNo, String name) {
        this.addressNo = addressNo;
        this.addressRoad = addressRoad;
        this.departmentName = departmentName;
        this.address = address;
        this.city = city;
        this.mobileNo = mobileNo;
        this.name = name;
    }


    protected User(Parcel in) {
        addressNo = in.readString();
        addressRoad = in.readString();
        departmentName = in.readString();
        address = in.readString();
        city = in.readString();
        mobileNo = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addressNo);
        dest.writeString(addressRoad);
        dest.writeString(departmentName);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(mobileNo);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public String getAddressRoad() {
        return addressRoad;
    }

    public void setAddressRoad(String addressRoad) {
        this.addressRoad = addressRoad;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
