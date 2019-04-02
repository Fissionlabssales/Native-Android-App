package com.nytimes.application.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utils {

    public static boolean isNetworkConnected(final Context context) {
        NetworkInfo activeNetwork = Utils.getActiveNetworkInfo(context);
        boolean isNetworkConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        Log.d(Utils.class.getName(), "isNetworkConnected: " + isNetworkConnected);
        return isNetworkConnected;
    }

    public static NetworkInfo getActiveNetworkInfo(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork;
    }
}
