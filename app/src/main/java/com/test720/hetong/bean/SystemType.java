package com.test720.hetong.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/29 8:48.
 */

public class SystemType {

    /**
     * code : 1
     * data : {"list":[{"count":0,"logosrc":"Public/Img/zyjapp.jpg","name":"车身系统","tid":"1"},{"count":0,"logosrc":"Public/Img/zyjapp.jpg","name":"底盘系统","tid":"2"},{"count":1,"logosrc":"Public/Img/zyjapp.jpg","name":"动力故障","tid":"3"},{"count":0,"logosrc":"Public/Img/zyjapp.jpg","name":"通信系统故障","tid":"4"}]}
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
             * count : 0
             * logosrc : Public/Img/zyjapp.jpg
             * name : 车身系统
             * tid : 1
             */

            private int count;
            private String logosrc;
            private String name;
            private String tid;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getLogosrc() {
                return logosrc;
            }

            public void setLogosrc(String logosrc) {
                this.logosrc = logosrc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }
        }
    }
}
