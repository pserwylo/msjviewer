package com.serwylo.msjviewer;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class MsjWebClient extends StylishWebClient {

    @Override
    protected String[] getStylesheetUrls(String webpageUrl) {

        Uri uri = Uri.parse( webpageUrl );
        List<String> urls = new ArrayList<String>();

        if ( uri.getHost().equals( "ssl.stjohnvic.com.au" ) ) {
            urls.add( "https://" );
        }


        else if ( webpageUrl.equals( "" ) ) {

        }
        return null;

    }

}
