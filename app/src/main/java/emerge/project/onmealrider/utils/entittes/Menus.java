package emerge.project.onmealrider.utils.entittes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Menus implements Parcelable {

    int orderId;
    String cartID;
    String outletMenuName;
    String size;
    Double price;
    int qty;



    public Menus(int orderId, String cartID, String outletMenuName, String size, Double price, int qty) {
        this.orderId = orderId;
        this.cartID = cartID;
        this.outletMenuName = outletMenuName;
        this.size = size;
        this.price = price;
        this.qty = qty;

    }


    protected Menus(Parcel in) {
        orderId = in.readInt();
        cartID = in.readString();
        outletMenuName = in.readString();
        size = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        qty = in.readInt();
    }

    public static final Creator<Menus> CREATOR = new Creator<Menus>() {
        @Override
        public Menus createFromParcel(Parcel in) {
            return new Menus(in);
        }

        @Override
        public Menus[] newArray(int size) {
            return new Menus[size];
        }
    };

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getOutletMenuName() {
        return outletMenuName;
    }

    public void setOutletMenuName(String outletMenuName) {
        this.outletMenuName = outletMenuName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderId);
        dest.writeString(cartID);
        dest.writeString(outletMenuName);
        dest.writeString(size);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        dest.writeInt(qty);
    }
}
