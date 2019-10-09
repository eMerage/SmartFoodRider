package emerge.project.onmealrider.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeliveryTime implements Serializable {

    @SerializedName("timeSlotID")
    int timeSlotID;

    @SerializedName("timeSlot")
    String timeSlot;


    public int getTimeSlotID() {
        return timeSlotID;
    }

    public void setTimeSlotID(int timeSlotID) {
        this.timeSlotID = timeSlotID;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}
