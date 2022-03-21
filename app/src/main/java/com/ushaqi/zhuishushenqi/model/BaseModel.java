package com.ushaqi.zhuishushenqi.model;

import java.io.Serializable;


public  class BaseModel implements Serializable {

    /**
     * ok : true
     * msg : string
     */

    private boolean ok;
    private String msg;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
