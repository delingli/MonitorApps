package com.dc.module_main.ui.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dc.commonlib.common.ConfigUtils;
import com.dc.commonlib.utils.ArounterManager;

public class FtViewPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;
    public static int TYPE_HOME = 1;
    public static int TYPE_BBS = 2;
    public static int TYPE_RETRIEVEPASSWORD = 3;

    private int type = TYPE_HOME;

    public FtViewPagerAdapter(FragmentManager fm, int type, String[] titles) {
        super(fm);
        this.type = type;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = null;

        if (this.type == TYPE_HOME) {
            fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_FOCUSONFRAGMENT_URL).navigation();

  /*          switch (pos) {
                case 0:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_FOCUSONFRAGMENT_URL).navigation();
                    break;
                case 1:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_RECOMMENDEDFRAGMENT_URL).navigation();

                    break;
                case 2:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_HARDWAREDESIGNFRAGMENT_URL).navigation();
                    break;
                case 3:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_PCBDESIGNFRAGMENT_URL).navigation();

                    break;
                case 4:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_ELECTROMAGNETICCOMPATIBILITYFRAGMENT_URL).navigation();

                    break;
                case 5:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_RADIOFREQUENCYDESIGN_URL).navigation();

                    break;
                case 6:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_SPISIMULATIONFRAGENT_URL).navigation();

                    break;
                case 7:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_EMBEDDEDFRAGMENT_URL).navigation();

                    break;
                case 8:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_MEASUREMENTFRAGMENT_URL).navigation();

                    break;
                case 9:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_RELIABILITYFRAGEMNET_URL).navigation();

                    break;

                case 10:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_MANUFACTURINGDESIGN_URL).navigation();

                    break;
                case 11:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_DEVICEENGINEERINGFRAGMENT_URL).navigation();

                    break;
                case 12:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.HOME_DEVELOPMENTMANAGERFRAGMENT_URL).navigation();
                    break;
            }*/

        } else if (this.type == TYPE_BBS) {
            switch (pos) {
                case 0:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.BBS_BBSFOCEONFRAGMENT_URL).withInt(ConfigUtils.KEY_TYPE,ConfigUtils.type_1).navigation();
                    break;
                case 1:
                    fragment = (Fragment) ARouter.getInstance().build(ArounterManager.BBS_BBSFOCEONFRAGMENT_URL).withInt(ConfigUtils.KEY_TYPE,ConfigUtils.type_2).navigation();
                    break;
            }
        } else if (this.type == TYPE_RETRIEVEPASSWORD) {

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.length;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
