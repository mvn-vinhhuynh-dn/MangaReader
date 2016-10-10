package coder.victorydst3.mangareader.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by lantm-mac-air on 3/21/16
 */
public final class PermissionUtils {
    private PermissionUtils() {
        // default constructor
    }

    //todo: it will use after
    public static boolean isPermissionCall(Context context) {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED);

    }

    public static boolean checkPermissionLocation(Context context) {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);

    }
}
