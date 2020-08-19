package com.example.mashu.walkinggroup.proxy;

import android.app.Activity;

import com.example.mashu.walkinggroup.R;

/**
 * The ProxyManager class deals with providing the
 * application with the proxy to generate HTTP Requests.
 */

public class ProxyManager {

    private static WGServerProxy proxy;
    private static String token;


    public static WGServerProxy getProxy(Activity activity) {

        generateProxy(activity);
        return proxy;
    }

    private static void generateProxy(Activity activity) {

        if(token == null) {
            proxy = ProxyBuilder.getProxy(activity.getString(R.string.api_key));
        } else {
            proxy = ProxyBuilder.getProxy(activity.getString(R.string.api_key), token);
        }
    }

    public static String setToken(String token) {
        ProxyManager.token = token;
        return token;
    }

    public static String getToken() {
        return token;
    }

}