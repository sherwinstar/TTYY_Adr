package com.ushaqi.zhuishushenqi.model;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by jared on 2017/11/1.
 */

public class HomeTapEntity implements CustomTabEntity {

    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public HomeTapEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
