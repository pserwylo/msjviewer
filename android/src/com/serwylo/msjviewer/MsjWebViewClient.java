package com.serwylo.msjviewer;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import com.serwylo.msjviewer.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MsjWebViewClient extends StylishWebViewClient {

    private static final String TAG = "com.serwylo.msjviewer.MsjWebViewClient";

    protected final MainActivity activity;

    public MsjWebViewClient( MainActivity activity ) {
        super( activity );
        this.activity = activity;
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        super.onPageFinished(view, url);

        // A little weird, but onPageFinished doesn't seem to get invoked if we received a
        // 302 redirect to the /login.jsp page. However, the login page is only navigated to
        // directly after a logout, so far as I can tell. Other times the user sees a login
        // prompt (e.g. in response to a session timeout), the form is shown at the same
        // url they were trying to visit.
        if ( url.equals( MsjConstants.URL_LOGIN ) ) {
            activity.navigateToMainTab();
        }
    }

    @Override
    protected String[] getStylesheetUrls(String webpageUrl) {

        Uri uri = Uri.parse( webpageUrl );
        List<String> urls = new ArrayList<String>();

        Log.d( TAG, "Checking whether we have custom styles for: " + webpageUrl );
        if ( uri.getHost().equals( "ssl.stjohnvic.com.au" ) ) {
            urls.add( RawGitPaths.path( "base" ) );
            urls.add( RawGitPaths.path( "hide-on-android" ) );
        }

        if ( webpageUrl.startsWith("https://ssl.stjohnvic.com.au/msj/event/list.jsp") ) {
            urls.add( RawGitPaths.path( "event-list" ) );
        }

        if ( webpageUrl.startsWith( "https://ssl.stjohnvic.com.au/msj/event/j_security_check" ) ) {
            urls.add( RawGitPaths.path( "login-error" ) );
        }

        if ( webpageUrl.startsWith( "https://ssl.stjohnvic.com.au/msj/event/hours.jsp" ) ) {
            urls.add( RawGitPaths.path( "hours" ) );
        }

        if ( webpageUrl.startsWith( "https://ssl.stjohnvic.com.au/msj/event/upcoming.jsp" ) ) {
            urls.add( RawGitPaths.path( "upcoming" ) );
        }

        String[] urlsArray = new String[ urls.size() ];
        urls.toArray( urlsArray );
        return urlsArray;

    }

}
