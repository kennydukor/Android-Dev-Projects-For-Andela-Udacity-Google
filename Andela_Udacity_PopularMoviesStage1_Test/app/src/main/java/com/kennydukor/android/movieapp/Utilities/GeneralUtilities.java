package com.kennydukor.android.movieapp.Utilities;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by kennydukor on 27/09/2018.
 */

public class GeneralUtilities {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
