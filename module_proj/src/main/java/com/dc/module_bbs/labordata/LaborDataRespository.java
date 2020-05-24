package com.dc.module_bbs.labordata;

import com.dc.baselib.mvvm.BaseRespository;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class LaborDataRespository extends BaseRespository {
    public List<IAbsLaborData> getData() {
        List<IAbsLaborData> ll = new ArrayList<>();
        RegisteredNumberLaborDataItem registeredNumberLaborDataItem = new RegisteredNumberLaborDataItem();
        registeredNumberLaborDataItem.title = "在册人数";
        registeredNumberLaborDataItem.count = "70人";
        TabLaborDataItem tabLaborDataItem = new TabLaborDataItem();
        tabLaborDataItem.title = "班组出勤信息";
        ll.add(registeredNumberLaborDataItem);
        ll.add(tabLaborDataItem);
        LaborDataItem laborDataItem = new LaborDataItem();
        laborDataItem.allNumber = "50";
        laborDataItem.attendanceNumber = "12";
        laborDataItem.title = "监理单位";
        int xx = (12 / 50) * 100;
        laborDataItem.progress = xx;
        ll.add(laborDataItem);

        LaborDataItem laborDataItem2 = new LaborDataItem();
        laborDataItem2.allNumber = "50";
        laborDataItem2.attendanceNumber = "12";
        laborDataItem2.title = "监理单位";

        int result =(int) ((float) 12 / (float) 50 * 100);
        laborDataItem2.progress = result;
        ll.add(laborDataItem2);


        TabLaborDataItem tabLaborDataItem2 = new TabLaborDataItem();
        tabLaborDataItem2.title = "工种出勤信息";
        ll.add(tabLaborDataItem2);


        LaborDataItem labor1 = new LaborDataItem();
        labor1.allNumber = "50";
        labor1.attendanceNumber = "12";
        labor1.title = "泥瓦工";
        labor1.progress = (12 / 50) * 100;
        ll.add(labor1);
        LaborDataItem labor2 = new LaborDataItem();
        labor2.allNumber = "50";
        labor2.attendanceNumber = "12";
        labor2.title = "木工";
        labor2.progress = (12 / 50) * 100;
        ll.add(labor2);
        return ll;
    }
}
