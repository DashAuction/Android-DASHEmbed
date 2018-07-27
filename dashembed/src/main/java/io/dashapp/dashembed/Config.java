package io.dashapp.dashembed;

import android.os.Parcel;
import android.os.Parcelable;

public class Config implements Parcelable {
    public String appId;

    public Config(String appId) {
        this.appId = appId;
    }

    protected Config(Parcel in) {
        appId = in.readString();
    }

    public static final Creator<Config> CREATOR = new Creator<Config>() {
        @Override
        public Config createFromParcel(Parcel in) {
            return new Config(in);
        }

        @Override
        public Config[] newArray(int size) {
            return new Config[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(appId);
    }
}
