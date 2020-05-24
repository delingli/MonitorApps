package com.dc.module_home.homamain;

import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;

public class HomeMainRespositorys extends BaseRespository {
    public static String EVENT_KEY_RELEASE_FAILURE;

    public HomeMainRespositorys() {
        EVENT_KEY_RELEASE_FAILURE = EventUtils.getEventKey();
    }
}
