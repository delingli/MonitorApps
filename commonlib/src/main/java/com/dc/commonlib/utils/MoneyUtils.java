package com.dc.commonlib.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyUtils {

    public static float yuanToHundredMillion(String yuan) {
        BigDecimal aa = new BigDecimal(yuan);
        BigDecimal bb = new BigDecimal(100000000 + "");
        return aa.divide(bb).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

    }

    public static String subtract(String a1, String b1) {
        BigDecimal aa = new BigDecimal(a1);
        BigDecimal bb = new BigDecimal(b1);

        return aa.subtract(bb).toString();

    }

    public static double percentage(String base, String pct) {
        BigDecimal aa = new BigDecimal(base);
        BigDecimal bb = new BigDecimal(pct);
        return bb.divide(aa, 2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    public static long percentageInt(String base, String pct) {
        BigDecimal aa = new BigDecimal(base);
        BigDecimal bb = new BigDecimal(pct);
        return bb.divide(aa, 0, BigDecimal.ROUND_HALF_UP).longValue();
    }
}
