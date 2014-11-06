package com.serwylo.msjviewer;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String FIRST_RUN = "firstRun";
    private static Preferences instance = null;

    public static Preferences get( Context context ) {
        if ( instance == null ) {
            instance = new Preferences( context );
        }
        return instance;
    }

    private final SharedPreferences prefs;

    private Preferences( Context context ) {
        prefs = context.getApplicationContext().getSharedPreferences( "preferences", Context.MODE_PRIVATE );
    }

    public boolean isFirstRun() {
        return prefs.getBoolean( FIRST_RUN, true );
    }

    public void setIsFirstRun( boolean value ) {
        prefs.edit().putBoolean( FIRST_RUN, value ).commit();
    }

}
