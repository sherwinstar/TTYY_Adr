package com.ushaqi.zhuishushenqi.model.baseweb;

public class SaveImageEntity {

    private String imageValue;
    private boolean isBase64;
    private boolean isJumpToWeChat;

    public String getImageValue() {
        return imageValue;
    }

    public void setImageValue(String imageValue) {
        this.imageValue = imageValue;
    }

    public boolean isBase64() {
        return isBase64;
    }

    public void setBase64(boolean base64) {
        isBase64 = base64;
    }

    public boolean isJumpToWeChat() {
        return isJumpToWeChat;
    }

    public void setJumpToWeChat(boolean jumpToWeChat) {
        isJumpToWeChat = jumpToWeChat;
    }
}
