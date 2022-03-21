package com.ushaqi.zhuishushenqi.event;


import com.ushaqi.zhuishushenqi.model.Account;

/**
 * 事件-登录成功
 *
 * @author Shaojie
 * @Date 2014-4-17 上午10:30:40
 */
public class LoginEvent {

    private final Account account;

    public LoginEvent(Account account) {
        this.account = account;
    }


    public Account getAccount() {
        return account;
    }

}
