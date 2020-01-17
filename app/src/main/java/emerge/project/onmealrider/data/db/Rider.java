package emerge.project.onmealrider.data.db;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Rider extends RealmObject implements Serializable {


    @PrimaryKey
    long rowid;

    @SerializedName("id")
    int riderId;

    @SerializedName("name")
    String ridername;





    public Rider() {
    }

    public long getRowid() {
        return rowid;
    }

    public void setRowid(long rowid) {
        this.rowid = rowid;
    }

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public String getRidername() {
        return ridername;
    }

    public void setRidername(String ridername) {
        this.ridername = ridername;
    }
}
