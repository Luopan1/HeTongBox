package com.test720.hetong.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/28 9:38.
 */

public class Classification {

    /**
     * code : 1
     * data : {"list":[{"count":"1","lid":"1","logosrc":"Public/Img/zyjapp.jpg","name":"汽车系统安全监控"},{"count":"0","lid":"2","logosrc":"Public/Img/zyjapp.jpg","name":"汽车动态监控"},{"count":"0","lid":"3","logosrc":"Public/Img/zyjapp.jpg","name":"汽车保护监控"},{"count":"0","lid":"4","logosrc":"Public/Img/zyjapp.jpg","name":"出行导航预设"},{"count":"4","lid":"5","logosrc":"Public/Img/zyjapp.jpg","name":"通知"},{"count":"0","lid":"6","logosrc":"Public/Img/zyjapp.jpg","name":"我的"}]}
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
             * count : 1
             * lid : 1
             * logosrc : Public/Img/zyjapp.jpg
             * name : 汽车系统安全监控
             */

            private String count;
            private String lid;
            private String logosrc;
            private String name;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getLid() {
                return lid;
            }

            public void setLid(String lid) {
                this.lid = lid;
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
        }
    }
}
