package com.dc.baselib.baseEntiry;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    public String user_id;
    public String realname;
    public boolean auth_pass;//true:已实名认证
    public boolean captcha;
    public String sid;//tolen
    public String face_image;
    public String company_name;//公司名
    public String company_role;
    public String company_manager;
    public String company_id;
    public int worker_id;
    public List<String> groups_list;
    public ArrayList<ChildrenBean> children;
    public boolean is_owner;


    public class ChildrenBean implements Serializable {
        public String id;
        public String employee__company__name;
        public boolean is_owner;
    }


    public User() {
    }

}
