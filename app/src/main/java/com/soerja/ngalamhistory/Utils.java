package com.soerja.ngalamhistory;

import android.app.Activity;
import android.content.Intent;

public class Utils {

    private static int sTheme;

    public final static int THEME_MATERIAL_GREEN = 0;
    public final static int THEME_MATERIAL_BROWN = 1;

    public static void changeToTheme(Activity activity, int theme){
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void onActivityCreateSetTheme(Activity activity){
        switch (sTheme){
            default:
            case THEME_MATERIAL_GREEN:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_MATERIAL_BROWN:
                activity.setTheme(R.style.AppThemeBrown);
                break;
        }
    }
}
