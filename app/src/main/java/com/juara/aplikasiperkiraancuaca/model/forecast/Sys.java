
package com.juara.aplikasiperkiraancuaca.model.forecast;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys implements Serializable, Parcelable
{

    @SerializedName("pod")
    @Expose
    private String pod;
    public final static Creator<Sys> CREATOR = new Creator<Sys>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Sys createFromParcel(Parcel in) {
            return new Sys(in);
        }

        public Sys[] newArray(int size) {
            return (new Sys[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4250041598671443376L;

    protected Sys(Parcel in) {
        this.pod = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sys() {
    }

    /**
     * 
     * @param pod
     */
    public Sys(String pod) {
        super();
        this.pod = pod;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pod);
    }

    public int describeContents() {
        return  0;
    }

}
