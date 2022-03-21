package com.ushaqi.zhuishushenqi.model;
/**
 * <p>
 * @ClassName: ChannelInfoResult
 * @Date: 2019-11-07 19:33
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 渠道归因的返回值
 * </p>
 */
public class ChannelInfoResult {
    private int            code;
    private String message;
    private CpsChannelInfo result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CpsChannelInfo getResult() {
        return result;
    }

    public void setResult(CpsChannelInfo result) {
        this.result = result;
    }
}
