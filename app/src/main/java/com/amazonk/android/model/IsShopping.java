package com.amazonk.android.model;

public class IsShopping {
    private static boolean isShopping = false;

    // Called by ScanActivity
    public static void startShopping() {
        isShopping = true;
    }

    // Called by LocationService to empty the cart and start paying
    public static void stopShopping() {
        isShopping = false;
        // TODO: empty cart
    }

    public static boolean status() {
        return isShopping;
    }
}
