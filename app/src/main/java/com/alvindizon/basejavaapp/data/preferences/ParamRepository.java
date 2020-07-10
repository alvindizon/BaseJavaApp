package com.alvindizon.basejavaapp.data.preferences;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import com.alvindizon.basejavaapp.BuildConfig;
import com.alvindizon.basejavaapp.R;
import com.alvindizon.basejavaapp.data.network.model.LoginToken;
import com.alvindizon.basejavaapp.util.Const;

import javax.inject.Inject;
import javax.inject.Singleton;

// TODO add methods for specific preferences as needed
@Singleton
public class ParamRepository {

    private final PreferenceHelper preferenceHelper;
    private final ConnectivityManager connectivityManager;

    @Inject
    public ParamRepository(PreferenceHelper preferenceHelper,
                           ConnectivityManager connectivityManager) {
        this.preferenceHelper = preferenceHelper;
        this.connectivityManager = connectivityManager;
    }


    private boolean isWiFi() {
        if (Build.VERSION.SDK_INT < 23) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
            }
        } else {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            }
        }
        return false;
    }

    private boolean isMobileData() {
        if (Build.VERSION.SDK_INT < 23) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
            }
        } else {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
            }
        }
        return false;
    }

    public String getBaseUrlWithVersion(boolean isSecondary) {
        StringBuilder sb = new StringBuilder();
        if (isHTTPSEnabled()) {
            sb.append("https://");
        } else {
            sb.append("http://");
        }

        if(isMobileData()) {
            if(isSecondary) {
                sb.append(getSecondaryMobileIp());
                sb.append(":");
                sb.append(getSecondaryMobilePort());
            } else {
                sb.append(getMobileIp());
                sb.append(":");
                sb.append(getMobilePort());
            }
        } else if(isWiFi()) {
            if(isSecondary) {
                sb.append(getSecondaryWiFiIp());
                sb.append(":");
                sb.append(getSecondaryWiFiPort());
            } else {
                sb.append(getWiFiIp());
                sb.append(":");
                sb.append(getWiFiPort());
            }
        } else {
            sb.append(Const.BASE_URL);
        }
        sb.append("/");
        sb.append(BuildConfig.ApiVer);
        sb.append("/api/some/thing");
        return sb.toString();
    }


    public int getConnectionTimeout() {
        return Integer.parseInt(preferenceHelper.get(R.string.key_conn_timeout, R.string.pref_default_conn_timeout));
    }


    public boolean isHTTPSEnabled() {
        return preferenceHelper.get(R.string.key_enable_https, false);
    }

    public String getMobileIp() {
        return preferenceHelper.get(R.string.key_mobile_ip, R.string.pref_default_mobile_ip);
    }

    public String getMobilePort() {
        return preferenceHelper.get(R.string.key_mobile_port, R.string.pref_default_mobile_port);
    }

    public String getWiFiIp() {
        return preferenceHelper.get(R.string.key_wifi_ip, R.string.pref_default_wifi_ip);
    }

    public String getWiFiPort() {
        return preferenceHelper.get(R.string.key_wifi_port, R.string.pref_default_wifi_port);
    }

    public String getSecondaryMobileIp() {
        return preferenceHelper.get(R.string.key_mobile_secondary_ip, R.string.pref_secondary_mobile_ip);
    }

    public String getSecondaryMobilePort() {
        return preferenceHelper.get(R.string.key_mobile_secondary_port, R.string.pref_secondary_mobile_port);
    }

    public String getSecondaryWiFiIp() {
        return preferenceHelper.get(R.string.key_wifi_secondary_ip, R.string.pref_secondary_wifi_ip);
    }

    public String getSecondaryWiFiPort() {
        return preferenceHelper.get(R.string.key_wifi_secondary_port, R.string.pref_secondary_wifi_port);
    }

    public String getRefreshToken() {
        return preferenceHelper.get(R.string.key_refresh_token, null);
    }

    public String getTokenType() {
        return preferenceHelper.get(R.string.key_token_type, null);
    }

    public String getAccessToken() {
        return preferenceHelper.get(R.string.key_access_token, null);
    }

    public void saveToken(LoginToken token) {
        preferenceHelper.set(R.string.key_access_token, token.getAccess_token());
        preferenceHelper.set(R.string.key_token_type, token.getToken_type());
        preferenceHelper.set(R.string.key_expires_in, Integer.toString(token.getExpires_in()));
        preferenceHelper.set(R.string.key_refresh_token, token.getRefresh_token());
    }

    public void clearSession() {
        preferenceHelper.remove(R.string.key_access_token);
        preferenceHelper.remove(R.string.key_token_type);
        preferenceHelper.remove(R.string.key_expires_in);
        preferenceHelper.remove(R.string.key_refresh_token);
    }

    public String getAuthorization() {
        if (getTokenType() != null && getAccessToken() != null) {
            return getTokenType() + " " + getAccessToken();
        }
        return null;
    }
}
