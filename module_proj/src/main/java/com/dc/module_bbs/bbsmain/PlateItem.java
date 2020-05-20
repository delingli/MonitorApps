package com.dc.module_bbs.bbsmain;

import java.util.List;

//所有
public class PlateItem extends AbsFocusDiscuss {
    public String forumid;
    public String forumname;
    public String posts;
    public String forumupname;
    public String forumupid;
    public String type;
    public String pic;
    public String description;
    public int thread_default_order_by;
    public int focus_num;
    public List<FocusPlateItem.ChildBean> child;
}
