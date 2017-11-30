package com.test720.hetong.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author LuoPan on 2017/11/27 15:14.
 */

public class notify {


    /**
     * code : 1
     * data : {"list":[{"collision_type":"0","did":"10","lat":"","long":"","meun_type":"4","pjss":"0","pz_time":"1970年01月01日","time":"2020-09-13 20:00:44","xslc":"0","xssj":"0"},{"collision_type":"0","did":"9","lat":"","long":"","meun_type":"3","pjss":"0","pz_time":"1970年01月01日","time":"2020-09-13 20:00:44","xslc":"0","xssj":"0"},{"collision_type":"0","did":"8","lat":"","long":"","meun_type":"3","pjss":"0","pz_time":"1970年01月01日","time":"2020-09-13 20:00:44","xslc":"0","xssj":"0"},{"collision_type":"1","did":"7","lat":"30.4418","long":"103.1582","meun_type":"2","pjss":"0","pz_time":"1970年01月01日","time":"2020-09-13 20:00:44","xslc":"0","xssj":"0"},{"collision_type":"0","did":"6","lat":"30.4418","long":"103.1582","meun_type":"2","pjss":"0","pz_time":"1970年01月01日","time":"2020-09-13 20:00:44","xslc":"0","xssj":"0"},{"collision_type":"0","did":"2","lat":"","long":"","meun_type":"1","pjss":"90","pz_time":"1970年01月01日","time":"2020-09-13 20:00:44","xslc":"80","xssj":"20"},{"collision_type":"0","did":"1","lat":"","long":"","meun_type":"1","pjss":"90","pz_time":"1970年01月01日","time":"2020-09-13 20:00:44","xslc":"80","xssj":"20"}]}
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
             * collision_type : 0
             * did : 10
             * lat :
             * long :
             * meun_type : 4
             * pjss : 0
             * pz_time : 1970年01月01日
             * time : 2020-09-13 20:00:44
             * xslc : 0
             * xssj : 0
             */

            private String collision_type;
            private String did;
            private String lat;
            @SerializedName("long")
            private String longX;
            private String meun_type;
            private String pjss;
            private String pz_time;
            private String time;
            private String xslc;
            private String xssj;

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

            public String getMeun_type() {
                return meun_type;
            }

            public void setMeun_type(String meun_type) {
                this.meun_type = meun_type;
            }

            public String getPjss() {
                return pjss;
            }

            public void setPjss(String pjss) {
                this.pjss = pjss;
            }

            public String getPz_time() {
                return pz_time;
            }

            public void setPz_time(String pz_time) {
                this.pz_time = pz_time;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getXslc() {
                return xslc;
            }

            public void setXslc(String xslc) {
                this.xslc = xslc;
            }

            public String getXssj() {
                return xssj;
            }

            public void setXssj(String xssj) {
                this.xssj = xssj;
            }
        }
    }
}
