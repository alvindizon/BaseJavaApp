package com.alvindizon.basejavaapp.data.network.model;

import com.squareup.moshi.Json;

// TODO modify field names as needed
public class LoginToken {

    @Json(name = "access_token")
    private String access_token;
    @Json(name = "token_type")
    private String token_type;
    @Json(name = "expires_in")
    private int expires_in;
    @Json(name = "refresh_token")
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String scope) {
        this.refresh_token = scope;
    }

    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}