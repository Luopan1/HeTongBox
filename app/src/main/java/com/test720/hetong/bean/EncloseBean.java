package com.test720.hetong.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/12/13 10:53.
 */

public class EncloseBean {

    /**
     * code : 1
     * data : {"list":[{"data":"2020-09-13 20:00:44","day":"2020年09月13日","did":"8","time":"1599998444"}],"total":2}
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
         * list : [{"data":"2020-09-13 20:00:44","day":"2020年09月13日","did":"8","time":"1599998444"}]
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
             * data : 2020-09-13 20:00:44
             * day : 2020年09月13日
             * did : 8
             * time : 1599998444
             */

            private String data;
            private String day;
            private String did;
            private String time;

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
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
