package com.test720.hetong.base;

/**
 * Created by jie on 2016/12/7.
 */

public class CurrencyEvent<T> {

    private T data;
    private int what;
    public CurrencyEvent(T data, int what) {
        // TODO Auto-generated constructor stub
        this.data = data;
        this.what = what;
    }
    public T getMsg(){
        return data;
    }

    public int getWhat()
    {
        return what;
    }


}
