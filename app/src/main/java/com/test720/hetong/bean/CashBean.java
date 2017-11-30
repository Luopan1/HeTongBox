package com.test720.hetong.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author LuoPan on 2017/11/29 18:22.
 */

public class CashBean {


    /**
     * code : 1
     * data : {"list":[{"collision_type":"0","did":"6","lat":"30.4418","long":"103.1582","time":"2020-09-13 20:00:44"}],"total":2}
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
        /**
         * list : [{"collision_type":"0","did":"6","lat":"30.4418","long":"103.1582","time":"2020-09-13 20:00:44"}]
         * total : 2
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * collision_type : 0
             * did : 6
             * lat : 30.4418
             * long : 103.1582
             * time : 2020-09-13 20:00:44
             */

            private String collision_type;
            private String did;
            private String lat;
            @SerializedName("long")
            private String longX;
            private String time;

            public String getCollision_type() {
                return collision_type;
            }

            public void setCollision_type(String collision_type) {
                this.collision_type = collision_type;
            }

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
            }

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
