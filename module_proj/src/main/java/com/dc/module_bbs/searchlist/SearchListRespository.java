package com.dc.module_bbs.searchlist;

import com.dc.baselib.mvvm.BaseRespository;
import com.dc.baselib.mvvm.EventUtils;

public class SearchListRespository extends BaseRespository {
    public String EVENT_SEARCH_RESULT;

    public SearchListRespository() {
        this.EVENT_SEARCH_RESULT = EventUtils.getEventKey();
    }
}
