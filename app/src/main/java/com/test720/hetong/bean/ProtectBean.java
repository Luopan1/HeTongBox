package com.test720.hetong.bean;

/**
 * @author LuoPan on 2017/11/29 17:45.
 */

public class ProtectBean {
    private int Icon;
    private String title;
    private int notifyNum;

    public ProtectBean(int icon, String title, int notifyNum) {
        Icon = icon;
        this.title = title;
        this.notifyNum = notifyNum;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNotifyNum() {
        return notifyNum;
    }

    public void setNotifyNum(int notifyNum) {
        this.notifyNum = notifyNum;
    }
}
