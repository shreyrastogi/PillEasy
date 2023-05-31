package com.mc2022.template.Utilities;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtility {
    static Snackbar snackbar;

    public static void ShowSnackbar(String message, View view) {
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setDuration(10000);
        snackbar.show();
        snackbar.setAction("OK", view1 -> snackbar.dismiss());
    }
}
