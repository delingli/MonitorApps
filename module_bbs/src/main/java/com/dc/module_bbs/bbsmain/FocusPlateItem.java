package com.dc.module_bbs.bbsmain;

import java.util.List;

//关注的板块
public class FocusPlateItem extends AbsFocusDiscuss {
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
    public List<ChildBean> child;

    public class ChildBean extends AbsFocusDiscuss {
        public String forumid;
        public String forumname;
        public String type;
        public String threads;
        public String posts;
        public String pic;
        public String description;
        public int focus_num;
        public int thread_default_order_by;

        public List<ChildBean> child;

  /*      {
            "forumid": "48",
                "forumname": "电子硬件教程资料区",
                "type": "sub",
                "threads": "1667",
                "posts": "41321",
                "pic": "http:\/\/app.eda365.com:8082\/\/pic\/forum_forum_cover\/48_5cc036b3e78d6.jpg",
                "description": "",
                "focus_num": 370,
                "thread_default_order_by": 0
        }*/


    }

/*    {
        "forumid": "271",
            "forumname": "电磁兼容&安规论坛",
            "threads": "2844",
            "posts": "19469",
            "forumupname": "研发技术版块",
            "forumupid": "99",
            "type": "forum",
            "pic": "http:\/\/app.eda365.com:8082\/\/upload\/business\/image\/ab\/535f444a973fe489e6055f9e9b9d88.png",
            "description": "EMC电磁兼容近场远场、EMI、ESD、RE\/CE等辐射传导干扰、屏蔽接地、滤波防护等电磁兼容技术网站。",
            "thread_default_order_by": 1,
            "focus_num": 692,
            "child": [{
        "forumid": "304",
                "forumname": "电磁兼容教程资料区",
                "type": "sub",
                "threads": "1280",
                "posts": "6397",
                "pic": "http:\/\/app.eda365.com:8082\/\/pic\/forum_forum_cover\/304_5cc034491a43a.jpg",
                "description": "",
                "focus_num": 121,
                "thread_default_order_by": 0
    }]
    }*/
}
