package com.bigfun.sdk.model;


import androidx.annotation.Keep;

@Keep
public class LoginBean {

    /**
     * code : 0
     * msg : success
     * data : {"headImg":"unknow","nickName":"unknow","accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYXBpdXNlciIsImFjY291bnQiOiI1MjY1NTQiLCJpc3MiOiJqb3ljaGVhcCIsImF1ZCI6IjA5OGY2YmNkNDYyMWQzNzNjYWRlNGU4MzI2MjdiNGY2IiwiZXhwaXJlZFRpbWUiOjE2MDEyNTkxOTg4MzksImV4cCI6MTYwMTI1OTE5OCwibmJmIjoxNTk5NTMxMTk4fQ.zZdHhWgsMZBRGZa685-RRlHelPJvDRK8R840jTZkG44","userId":"526554","expiredTime":1601259198839,"username":"GU1599531198827"}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Keep
    public static class DataBean {
        /**
         * headImg : unknow
         * nickName : unknow
         * accessToken : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYXBpdXNlciIsImFjY291bnQiOiI1MjY1NTQiLCJpc3MiOiJqb3ljaGVhcCIsImF1ZCI6IjA5OGY2YmNkNDYyMWQzNzNjYWRlNGU4MzI2MjdiNGY2IiwiZXhwaXJlZFRpbWUiOjE2MDEyNTkxOTg4MzksImV4cCI6MTYwMTI1OTE5OCwibmJmIjoxNTk5NTMxMTk4fQ.zZdHhWgsMZBRGZa685-RRlHelPJvDRK8R840jTZkG44
         * userId : 526554
         * expiredTime : 1601259198839
         * username : GU1599531198827
         */

        private String headImg;
        private String nickName;
        private String accessToken;
        private String userId;
        private long expiredTime;
        private String username;
        private String kochavaAppguid;

        public String getKochavaAppguid() {
            return kochavaAppguid;
        }

        public void setKochavaAppguid(String kochavaAppguid) {
            this.kochavaAppguid = kochavaAppguid;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public long getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(long expiredTime) {
            this.expiredTime = expiredTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "headImg='" + headImg + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", accessToken='" + accessToken + '\'' +
                    ", userId='" + userId + '\'' +
                    ", expiredTime=" + expiredTime +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
