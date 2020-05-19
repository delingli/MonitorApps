package com.dc.module_bbs.bbsdetail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BBsDetails  implements Parcelable {
    public String forumid;
    public String pic;
    public String description;
    public String moderators;
    public String threads;
    public String posts;
    public String focus_num;
    public String forumname;
    public String type;
    public String thread_default_order_by;

    public int is_focus;
    public List<String> moderator_avatar;

    protected BBsDetails(Parcel in) {
        forumid = in.readString();
        pic = in.readString();
        description = in.readString();
        moderators = in.readString();
        threads = in.readString();
        posts = in.readString();
        focus_num = in.readString();
        forumname = in.readString();
        type = in.readString();
        thread_default_order_by = in.readString();
        is_focus = in.readInt();
        moderator_avatar = in.createStringArrayList();
    }

    public static final Creator<BBsDetails> CREATOR = new Creator<BBsDetails>() {
        @Override
        public BBsDetails createFromParcel(Parcel in) {
            return new BBsDetails(in);
        }

        @Override
        public BBsDetails[] newArray(int size) {
            return new BBsDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(forumid);
        dest.writeString(pic);
        dest.writeString(description);
        dest.writeString(moderators);
        dest.writeString(threads);
        dest.writeString(posts);
        dest.writeString(focus_num);
        dest.writeString(forumname);
        dest.writeString(type);
        dest.writeString(thread_default_order_by);
        dest.writeInt(is_focus);
        dest.writeStringList(moderator_avatar);
    }


/*    {
        "forumid": "8",
            "forumname": "\u786c\u4ef6\u6280\u672f\u8ba8\u8bba",
            "pic": "http:\/\/app.eda365.com:8082\/\/upload\/business\/image\/4d\/b69476b4a9adc0ddc20d422357857a.png",
            "description": "\u672c\u7248\u5173\u4e8e\u7535\u5b50\u5de5\u7a0b\u5e08\u5e38\u89c1\u7684\u4ea7\u54c1\u786c\u4ef6\u7814\u53d1\u7535\u5b50\u7535\u8def\u8bbe\u8ba1\u3001\u539f\u7406\u5206\u6790\u786c\u4ef6\u529f\u80fd\u8bbe\u8ba1\u3001\u6d4b\u8bd5\u8c03\u8bd5\u7684\u6280\u672f\u8ba8\u8bba\u3002",
            "moderators": "\u8d85\u7d1a\u72d7,jacklee_47pn,Aubrey,kevin890505,Jujianjun,jacky401,markamp,aarom,canatto,topwon,huo_xing,haoxizi",
            "threads": "8934",
            "posts": "84777",
            "is_focus": 1,
            "focus_num": "1529",
            "moderator_avatar": ["https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=75275", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=129146", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=170042", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=189066", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=194454", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=217324", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=230876", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=303482", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=328737", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=331159", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=342862", "https:\/\/www.eda365.com\/uc_server\/avatar.php?uid=345082"]
    }*/
}
