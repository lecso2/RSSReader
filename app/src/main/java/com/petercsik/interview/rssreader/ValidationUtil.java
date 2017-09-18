package com.petercsik.interview.rssreader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Patterns;

import java.net.MalformedURLException;
import java.net.URL;

public class ValidationUtil {

    private static final String PREFIX = "http://";

    public static URL validateURL(String url, Context context) {
        return validateURL(url, context, true);
    }

    public static boolean isValidURL(String url) {
        return validateURL(url, null, false) == null ? false : true;
    }

    private static URL validateURL(String url, Context context, boolean withMessage) {
        URL result = null;
        String prefixedUrl = " ";
        if (url != null && !url.startsWith(PREFIX)) {
            prefixedUrl = PREFIX + url.trim();
        }
        if (Patterns.WEB_URL.matcher(prefixedUrl).matches()) {
            try {
                result = new URL(prefixedUrl);
            } catch (MalformedURLException e) {
                result = null;
                if (withMessage) {
                    DialogUtil.showMalformedUrlAlertDialog(context);
                }
            }
        } else {
            if (withMessage) {
                DialogUtil.showMalformedUrlAlertDialog(context);
            }
        }
        return result;
    }

    public static boolean isConnectionAvailable(Context context) {
        boolean result = true;
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() == null || !conMgr.getActiveNetworkInfo().isConnected()
                || !conMgr.getActiveNetworkInfo().isAvailable()) {
            result = false;
        }
        return result;
    }

}
