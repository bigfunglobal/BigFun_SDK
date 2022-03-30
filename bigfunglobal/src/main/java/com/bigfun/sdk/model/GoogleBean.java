package com.bigfun.sdk.model;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class GoogleBean {

    //--------------get --------------------------------
    public String getId() {
        return Id == null ? "" : Id;
    }

    //--------------get --------------------------------
    public String getIdToken() {
        return IdToken == null ? "" : IdToken;
    }

    //--------------get --------------------------------
    public String getEmail() {
        return Email == null ? "" : Email;
    }


    //--------------get --------------------------------
    public String getDisplayName() {
        return DisplayName == null ? "" : DisplayName;
    }

    //--------------get --------------------------------
    public String getGivenName() {
        return GivenName == null ? "" : GivenName;
    }

    //--------------get --------------------------------
    public String getFamilyName() {
        return FamilyName == null ? "" : FamilyName;
    }

    //--------------get --------------------------------
    public Uri getPhotoUrl() {
        return PhotoUrl;
    }

    //--------------get --------------------------------
    public String getServerAuthCode() {
        return ServerAuthCode == null ? "" : ServerAuthCode;
    }
    private String Id;
    private String IdToken;
    private String Email;

    private String DisplayName;
    private String GivenName;
    private String FamilyName;


    private Uri PhotoUrl;
    private String ServerAuthCode;

    public GoogleBean(GoogleSignInAccount account){
        this.Id=account.getId();
        this.IdToken=account.getIdToken();
        this.Email=account.getEmail();
        this.DisplayName=account.getDisplayName();
        this.GivenName=account.getGivenName();
        this.FamilyName=account.getFamilyName();
        this.PhotoUrl=account.getPhotoUrl();
        this.ServerAuthCode=account.getServerAuthCode();
    }

    @Override
    public String toString() {
        return "{" +"code:" + "0"  +
                ", Id:" + Id  +
                ", IdToken:" + IdToken  +
                ", Email:" + Email +
                ", DisplayName:" + DisplayName  +
                ", GivenName:" + GivenName  +
                ", FamilyName:" + FamilyName  +
                ", PhotoUrl:" + PhotoUrl +
                ", ServerAuthCode:" + ServerAuthCode  +
                "}";
    }
}
