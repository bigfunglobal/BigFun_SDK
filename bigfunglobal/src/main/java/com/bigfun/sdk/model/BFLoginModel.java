package com.bigfun.sdk.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.AuthenticationToken;
import com.facebook.login.LoginResult;

import java.util.Set;

public class BFLoginModel {
    private AccessToken accessToken;

    //--------------get --------------------------------
    public AccessToken getAccessToken() {
        return accessToken;
    }

    //--------------get --------------------------------
    public AuthenticationToken getAuthenticationToken() {
        return authenticationToken;
    }

    //--------------get --------------------------------
    public Set<String> getRecentlyGrantedPermissions() {
        return recentlyGrantedPermissions;
    }

    //--------------get --------------------------------
    public Set<String> getRecentlyDeniedPermissions() {
        return recentlyDeniedPermissions;
    }

    @Override
    public String toString() {
        return "BFLoginModel{" +
                "accessToken=" + accessToken +
                ", authenticationToken=" + authenticationToken +
                ", recentlyGrantedPermissions=" + recentlyGrantedPermissions +
                ", recentlyDeniedPermissions=" + recentlyDeniedPermissions +
                '}';
    }

    private AuthenticationToken authenticationToken;
    private Set<String> recentlyGrantedPermissions;
    private Set<String> recentlyDeniedPermissions;

    public BFLoginModel(LoginResult loginResult){
        accessToken=loginResult.getAccessToken();
        authenticationToken=loginResult.getAuthenticationToken();
        recentlyGrantedPermissions=loginResult.getRecentlyGrantedPermissions();
        recentlyDeniedPermissions=loginResult.getRecentlyDeniedPermissions();
    }
}
