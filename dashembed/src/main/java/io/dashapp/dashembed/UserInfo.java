package io.dashapp.dashembed;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    public String pushTokenString;
    public String userEmail;

    public UserInfo(String pushTokenString, String userEmail) {
        this.pushTokenString = pushTokenString;
        this.userEmail = userEmail;
    }

    protected UserInfo(Parcel in) {
        pushTokenString = in.readString();
        userEmail = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pushTokenString);
        parcel.writeString(userEmail);
    }
}
