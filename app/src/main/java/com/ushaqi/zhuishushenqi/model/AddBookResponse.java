package com.ushaqi.zhuishushenqi.model;

/**
 * @Author chengwencan
 * @Date 2017/3/8.
 */

public class AddBookResponse {

    /**
     * code : 10000
     * describe : success
     * msg : success
     */

    private String code;
    private String describe;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AddBookResponse{" +
                "code='" + code + '\'' +
                ", describe='" + describe + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
