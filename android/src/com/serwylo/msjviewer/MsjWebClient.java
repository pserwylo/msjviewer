package com.serwylo.msjviewer;

import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MsjWebClient extends StylishWebClient {

    private static final String TAG = "com.serwylo.msjviewer.MsjWebClient";

    @Override
    protected String[] getStylesheetUrls(String webpageUrl) {

        Uri uri = Uri.parse( webpageUrl );
        List<String> urls = new ArrayList<String>();

        Log.d( TAG, "Checking whether we have custom styles for: " + webpageUrl );
        if ( uri.getHost().equals( "ssl.stjohnvic.com.au" ) ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/base.css" );
        }

        if ( webpageUrl.startsWith("https://ssl.stjohnvic.com.au/msj/event/list.jsp") ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/event-list.css" );
        }

        if ( webpageUrl.startsWith("https://ssl.stjohnvic.com.au/msj/event/hours.jsp") ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/hours.css" );
        }

        if ( webpageUrl.startsWith("https://ssl.stjohnvic.com.au/msj/event/upcoming.jsp") ) {
            urls.add( "https://rawgit.com/pserwylo/msjviewer/master/stylesheets/upcoming.css" );
        }

        String[] urlsArray = new String[ urls.size() ];
        urls.toArray( urlsArray );
        return urlsArray;

    }

}
