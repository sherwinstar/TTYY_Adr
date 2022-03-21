package com.ushaqi.zhuishushenqi.model;

import java.io.Serializable;

/**
 * 账户
 *
 * @author Shaojie
 * @Date 2013-10-18 下午7:00:55
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 7268031855566891017L;

    private User data;// 用户
    private boolean ok; // 状态，成功或者失败
    private String msg; //

    public User getUser() {
        return data;
    }

    public void setUser(User user) {
        this.data = user;
    }

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
