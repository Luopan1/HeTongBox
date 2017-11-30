package com.test720.hetong.bean;

/**
 * @author LuoPan on 2017/11/28 11:48.
 */

public class Car {
    private String icon;
    private String carLicense;
    private String carName;

    public Car(String icon, String carLicense, String carName) {
        this.icon = icon;
        this.carLicense = carLicense;
        this.carName = carName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}
