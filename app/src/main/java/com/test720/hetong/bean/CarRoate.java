package com.test720.hetong.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author LuoPan on 2017/11/29 16:44.
 */

public class CarRoate {

    /**
     * code : 1
     * data : {"list":[{"lat":"30.4418","long":"103.1582"},{"lat":"30.4419","long":"103.1582"},{"lat":"30.4420","long":"103.1582"},{"lat":"30.4421","long":"103.1582"},{"lat":"30.4422","long":"103.1582"},{"lat":"30.4423","long":"103.1582"},{"lat":"30.5418","long":"103.1582"},{"lat":"30.5419","long":"103.1582"},{"lat":"30.5420","long":"103.1582"},{"lat":"30.5421","long":"103.1582"},{"lat":"30.5422","long":"103.1582"},{"lat":"30.5421","long":"103.1382"}]}
     * msg : 成功
     * type : json
     */

    private int code;
    private DataBean data;
    private String msg;
    private String type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * lat : 30.4418
             * long : 103.1582
             */

            private String lat;
            @SerializedName("long")
            private String longX;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLongX() {
                return longX;
            }

            public void setLongX(String longX) {
                this.longX = longX;
            }
        }
    }
}
