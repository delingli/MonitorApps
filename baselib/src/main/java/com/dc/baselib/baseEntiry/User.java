package com.dc.baselib.baseEntiry;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public String uid;
    public String username;
    public String email;
    public String regip;
    public String regdate;
    public String nickname;
    public String phone_head;
    public String phone;
    public String source;
    public String token;
    public String other_key;





    public User() {
    }


    protected User(Parcel in) {
        uid = in.readString();
        username = in.readString();
        email = in.readString();
        regip = in.readString();
        regdate = in.readString();
        nickname = in.readString();
        phone_head = in.readString();
        phone = in.readString();
        source = in.readString();
        token = in.readString();
        other_key = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(regip);
        dest.writeString(regdate);
        dest.writeString(nickname);
        dest.writeString(phone_head);
        dest.writeString(phone);
        dest.writeString(source);
        dest.writeString(token);
        dest.writeString(other_key);
    }
}
