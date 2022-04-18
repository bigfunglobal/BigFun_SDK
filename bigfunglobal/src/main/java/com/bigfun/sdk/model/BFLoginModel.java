//package com.bigfun.sdk.model;
//
//import com.facebook.AccessToken;
//import com.facebook.login.LoginResult;
//
//import java.util.Set;
//
//public class BFLoginModel {
//    private AccessToken accessToken;
//
//    //--------------get --------------------------------
//    public AccessToken getAccessToken() {
//        return accessToken;
//    }
//
//    //--------------get --------------------------------
//    public Set<String> getRecentlyGrantedPermissions() {
//        return recentlyGrantedPermissions;
//    }
//
//    //--------------get --------------------------------
//    public Set<String> getRecentlyDeniedPermissions() {
//        return recentlyDeniedPermissions;
//    }
//
//    @Override
//    public String toString() {
//        return "BFLoginModel{" +
//                "accessToken=" + accessToken +
//                ", recentlyGrantedPermissions=" + recentlyGrantedPermissions +
//                ", recentlyDeniedPermissions=" + recentlyDeniedPermissions +
//                '}';
//    }
//
//    private Set<String> recentlyGrantedPermissions;
//    private Set<String> recentlyDeniedPermissions;
//
//    public BFLoginModel(LoginResult loginResult){
//        accessToken=loginResult.getAccessToken();
//        recentlyGrantedPermissions=loginResult.getRecentlyGrantedPermissions();
//        recentlyDeniedPermissions=loginResult.getRecentlyDeniedPermissions();
//    }
//}
