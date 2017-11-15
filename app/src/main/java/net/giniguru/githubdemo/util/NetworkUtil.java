package net.giniguru.githubdemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    /**
     * Check Network Availability
     *
     * @param context Context Object Of Activity
     * @return : Return true if Internet is available
     */
    public static boolean isOnline(Context context) {
        if(context !=null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = null;
            if (cm != null) {
                netInfo = cm.getActiveNetworkInfo();
            }
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }else{
            return true;
        }
    }
}
