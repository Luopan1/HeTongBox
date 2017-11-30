package com.test720.hetong.bean;

import java.util.List;

/**
 * @author LuoPan on 2017/11/29 9:23.
 */

public class SystemError {

    /**
     * code : 1
     * data : {"describe":"故障异常时，实时报警，提醒到维修店维修","imgsrc":"Public/Img/zyjapp.jpg","list":[{"gzm_data":"P0003","gzm_describe":"燃油量调节器控制电路低","gzm_detail":" (在喷油嘴被广泛使用的新型汽车中，燃油量调节器已经很少被使用),燃油调节器出现故障","gzm_solve":"请通过专业的维修机构对相关部件做进一步检测","type_id":"3"},{"gzm_data":"P0002","gzm_describe":"燃油量调节器控制电路范围/性能","gzm_detail":" (在喷油嘴被广泛使用的新型汽车中，燃油量调节器已经很少被使用),燃油调节器出现故障","gzm_solve":"请通过专业的维修机构对相关部件做进一步检测","type_id":"3"},{"gzm_data":"P0001","gzm_describe":"燃油量调节器控制电路/开路","gzm_detail":" (在喷油嘴被广泛使用的新型汽车中，燃油量调节器已经很少被使用),燃油调节器出现故障","gzm_solve":"请通过专业的维修机构对相关部件做进一步检测","type_id":"3"}],"name":"动力故障"}
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
         * describe : 故障异常时，实时报警，提醒到维修店维修
         * imgsrc : Public/Img/zyjapp.jpg
         * list : [{"gzm_data":"P0003","gzm_describe":"燃油量调节器控制电路低","gzm_detail":" (在喷油嘴被广泛使用的新型汽车中，燃油量调节器已经很少被使用),燃油调节器出现故障","gzm_solve":"请通过专业的维修机构对相关部件做进一步检测","type_id":"3"},{"gzm_data":"P0002","gzm_describe":"燃油量调节器控制电路范围/性能","gzm_detail":" (在喷油嘴被广泛使用的新型汽车中，燃油量调节器已经很少被使用),燃油调节器出现故障","gzm_solve":"请通过专业的维修机构对相关部件做进一步检测","type_id":"3"},{"gzm_data":"P0001","gzm_describe":"燃油量调节器控制电路/开路","gzm_detail":" (在喷油嘴被广泛使用的新型汽车中，燃油量调节器已经很少被使用),燃油调节器出现故障","gzm_solve":"请通过专业的维修机构对相关部件做进一步检测","type_id":"3"}]
         * name : 动力故障
         */

        private String describe;
        private String imgsrc;
        private String name;
        private List<ListBean> list;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * gzm_data : P0003
             * gzm_describe : 燃油量调节器控制电路低
             * gzm_detail :  (在喷油嘴被广泛使用的新型汽车中，燃油量调节器已经很少被使用),燃油调节器出现故障
             * gzm_solve : 请通过专业的维修机构对相关部件做进一步检测
             * type_id : 3
             */

            private String gzm_data;
            private String gzm_describe;
            private String gzm_detail;
            private String gzm_solve;
            private String type_id;

            public String getGzm_data() {
                return gzm_data;
            }

            public void setGzm_data(String gzm_data) {
                this.gzm_data = gzm_data;
            }

            public String getGzm_describe() {
                return gzm_describe;
            }

            public void setGzm_describe(String gzm_describe) {
                this.gzm_describe = gzm_describe;
            }

            public String getGzm_detail() {
                return gzm_detail;
            }

            public void setGzm_detail(String gzm_detail) {
                this.gzm_detail = gzm_detail;
            }

            public String getGzm_solve() {
                return gzm_solve;
            }

            public void setGzm_solve(String gzm_solve) {
                this.gzm_solve = gzm_solve;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }
        }
    }
}
