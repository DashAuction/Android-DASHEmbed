package io.dashapp.dashembed;

import android.os.Parcel;
import android.os.Parcelable;

public class Config implements Parcelable {
    public String appId;
    public Boolean useDevelopmentServers;

    public Config(String appId) {
        this.appId = appId;
        this.useDevelopmentServers = false;
    }

    public Config(String appId, Boolean useDevelopmentServers) {
        this.appId = appId;
        this.useDevelopmentServers = useDevelopmentServers;
    }

    protected Config(Parcel in) {
        appId = in.readString();
        byte tmpUseDevelopmentServers = in.readByte();
        useDevelopmentServers = tmpUseDevelopmentServers == 0 ? null : tmpUseDevelopmentServers == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appId);
        dest.writeByte((byte) (useDevelopmentServers == null ? 0 : useDevelopmentServers ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
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
}
