package com.ushaqi.zhuishushenqi.sensors;

/**
 * @author shijingxing
 * @date 2020/7/6
 */
public class SensorsPageEventHelper {
    /**
     * 事件：页面浏览
     */
    public static final String YY_ZS_PAGE_SHOW = "yy_ZSPageShow";



    /**
     * 事件：页面item曝光
     */
    public static final String YY_ZS_KEYITEM_EXPOUSURE = "yy_ZSKeyItemExpousure";


    /**
     * 事件：按钮点击
     */
    public static final String YY_ZS_BTN_CLICK = "yy_ZSBtnClick";


    public static void addZSPageShowEvent(SensorsParamBuilder paramBuilder){
        SensorsUploadHelper.addTrackEvent("ZSPageShow", paramBuilder);
    }

    /**
     * 追书页面曝光事件
     * @param paramBuilder
     * @param pageCategorys
     */
    public static void addZSPageShowEvent(SensorsParamBuilder paramBuilder, String... pageCategorys){
        if(pageCategorys == null || pageCategorys.length == 0){
            return;
        }
        if(paramBuilder == null){
            paramBuilder = SensorsParamBuilder.create();
        }
        switch (pageCategorys.length){
            case 5:
                paramBuilder.put("page_category5", pageCategorys[4]);
            case 4:
                paramBuilder.put("page_category4", pageCategorys[3]);
            case 3:
                paramBuilder.put("page_category3", pageCategorys[2]);
            case 2:
                paramBuilder.put("page_category2", pageCategorys[1]);
            case 1:
                paramBuilder.put("page_category1", pageCategorys[0]);
            default:
                break;
        }
        SensorsUploadHelper.addTrackEvent(YY_ZS_PAGE_SHOW, paramBuilder);

    }


    public static void addZSBtnClickEvent(SensorsParamBuilder paramBuilder){
        SensorsUploadHelper.addTrackEvent(YY_ZS_BTN_CLICK,paramBuilder);
    }

    /**
     * 追书按钮点击事件
     * @param paramBuilder
     * @param btnClickCategorys
     */
    public static void addZSBtnClickEvent(SensorsParamBuilder paramBuilder, String... btnClickCategorys) {
        if (btnClickCategorys == null || btnClickCategorys.length == 0) {
            return;
        }
        if(paramBuilder == null){
            paramBuilder = SensorsParamBuilder.create();
        }
        switch (btnClickCategorys.length) {
            case 5:
                paramBuilder.put("btn_click_category5", btnClickCategorys[4]);
            case 4:
                paramBuilder.put("btn_click_category4", btnClickCategorys[3]);
            case 3:
                paramBuilder.put("btn_click_category3", btnClickCategorys[2]);
            case 2:
                paramBuilder.put("btn_click_category2", btnClickCategorys[1]);
            case 1:
                paramBuilder.put("btn_click_category1", btnClickCategorys[0]);
            default:
                break;
        }
        SensorsUploadHelper.addTrackEvent(YY_ZS_BTN_CLICK, paramBuilder);
    }

    /**
     * 页面item曝光事件
     * @param paramBuilder
     * @param pageCategorys
     */
    public static void addZSKeyItemExposureEvent(SensorsParamBuilder paramBuilder, String... pageCategorys){
        if(pageCategorys == null || pageCategorys.length == 0){
            return;
        }
        if(paramBuilder == null){
            paramBuilder = SensorsParamBuilder.create();
        }
        switch (pageCategorys.length){
            case 5:
                paramBuilder.put("page_item_category5", pageCategorys[4]);
            case 4:
                paramBuilder.put("page_item_category4", pageCategorys[3]);
            case 3:
                paramBuilder.put("page_item_category3", pageCategorys[2]);
            case 2:
                paramBuilder.put("page_item_category2", pageCategorys[1]);
            case 1:
                paramBuilder.put("page_item_category1", pageCategorys[0]);
            default:
                break;
        }
        SensorsUploadHelper.addTrackEvent(YY_ZS_KEYITEM_EXPOUSURE, paramBuilder);

    }


}
