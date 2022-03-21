package com.ushaqi.zhuishushenqi.module.baseweb.helper;

import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;




/**
 * @author shijingxing
 * @date 2020/7/10
 */
public class WebStyleHelper {

    public static boolean isFullScreenStyle(ZssqWebData webData) {
        if (webData == null) {
            return false;
        }
        int pageStyle = webData.getPageStyle();
        /*if(pageStyle == WebConstans.PAGE_STYLE_WITH_ICON || pageStyle == WebConstans.PAGE_STYLE_DEFAULT){
            return false;
        }*/
        if ((pageStyle & WebConstans.FLAG_PAGE_FULL_SCREEN_PLACEHOLDER) == 0) {
            return false;
        }
        return true;
    }

    public static boolean isFlsFullScreenStyle(ZssqWebData webData) {
        if (webData == null) {
            return false;
        }
        int pageStyle = webData.getPageStyle();
        /*if(pageStyle == WebConstans.PAGE_STYLE_WITH_ICON || pageStyle == WebConstans.PAGE_STYLE_DEFAULT){
            return false;
        }*/

        return WebConstans.PAGE_STYLE_FULLSCREEN_COMMON_FLS == pageStyle;
    }

    public static boolean isImmerseFullScreenStyle(ZssqWebData webData) {
        if (webData == null) {
            return false;
        }
        String pageStyle = webData.getFullScreenType();

        return WebConstans.H5_FULL_SCREEN_TYPE_STATUS_BAR_TRANSPARENT.equals(pageStyle);
    }
}
