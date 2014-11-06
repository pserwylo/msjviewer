package com.serwylo.msjviewer;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

public class MsjWebViewClient extends StylishWebViewClient {

    private static final String TAG = "com.serwylo.msjviewer.MsjWebViewClient";

    protected final WebViewActivity activity;

    public MsjWebViewClient( WebViewActivity activity ) {
        super();
        this.activity = activity;
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        super.onPageFinished(view, url);

        // A little weird, but onPageFinished doesn't seem to get invoked if we received a
        // 302 redirect to the /login.jsp page. However, the login page is only navigated to
        // directly after a logout, so far as I can tell. Other times the user sees a login
        // prompt (e.g. in response to a session timeout), the form is shonw at the same
        // url they were trying to visit.
        if ( url.equals( MsjConstants.URL_LOGIN ) ) {
            activity.finish();
        }
    }

    @Override
    protected String[] getStylesheetUrls(String webpageUrl) {

        Uri uri = Uri.parse( webpageUrl );
        List<String> urls = new ArrayList<String>();

        Log.d( TAG, "Checking whether we have custom styles for: " + webpageUrl );
        if ( uri.getHost().equals( "ssl.stjohnvic.com.au" ) ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/base.css" );
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/hide-on-android.css" );
        }

        if ( webpageUrl.startsWith("https://ssl.stjohnvic.com.au/msj/event/list.jsp") ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/event-list.css" );
        }

        if ( webpageUrl.startsWith( "https://ssl.stjohnvic.com.au/msj/event/j_security_check" ) ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/login-error.css" );
        }

        if ( webpageUrl.startsWith( "https://ssl.stjohnvic.com.au/msj/event/hours.jsp" ) ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/hours.css" );
        }

        if ( webpageUrl.startsWith( "https://ssl.stjohnvic.com.au/msj/event/upcoming.jsp" ) ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/upcoming.css" );
        }

        String[] urlsArray = new String[ urls.size() ];
        urls.toArray( urlsArray );
        return urlsArray;

    }

}
