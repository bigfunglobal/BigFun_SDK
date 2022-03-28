package com.bigfun.sdk.model;

public class IPBean {
    //--------------get --------------------------------
    public boolean isIpWhitelist() {
        return ipWhitelist;
    }

    //---------------set----------------------------------
    public void setIpWhitelist(boolean ipWhitelist) {
        this.ipWhitelist = ipWhitelist;
    }

    //--------------get --------------------------------
    public String getArea() {
        return area == null ? "" : area;
    }

    //---------------set----------------------------------
    public void setArea(String area) {
        this.area = area == null ? "" : area;
    }

    //--------------get --------------------------------
    public String getWhiteList() {
        return whiteList == null ? "" : whiteList;
    }

    //---------------set----------------------------------
    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList == null ? "" : whiteList;
    }

    //--------------get --------------------------------
    public String getBlackList() {
        return blackList == null ? "" : blackList;
    }

    //---------------set----------------------------------
    public void setBlackList(String blackList) {
        this.blackList = blackList == null ? "" : blackList;
    }

    private boolean ipWhitelist;
    private String area;
    private String whiteList;
    private String blackList;
}
